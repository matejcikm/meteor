package com.example.meteor.network;


import com.example.meteor.network.model.Meteor;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import retrofit2.Response;
import rx.Observable;

/**
 * @author martin
 * @since 21/02/2017.
 */

@Singleton
public class DataManager {


    private NasaService mService;

    @Inject
    public DataManager(NasaService service) {
        mService = service;
    }

    public Observable<List<Meteor>> getallMeteors() {
        return mService.getAllMeteors()
                .concatMap(this::checkIfResponseSuccessful)
                .flatMap(meteors -> {
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(realm1 -> realm1.insertOrUpdate(meteors));

                    return Observable.just(meteors);
                });
    }


    private <T> Observable<T> checkIfResponseSuccessful(Response<T> response) {

        if (response.isSuccessful()) {
            return Observable.just(response.body());
        } else {
            return Observable.error(new Throwable(response.raw().toString()));
        }
    }
}
