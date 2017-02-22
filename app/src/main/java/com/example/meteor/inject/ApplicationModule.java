package com.example.meteor.inject;

import android.app.Application;
import android.content.Context;

import com.example.meteor.network.NasaService;
import com.example.meteor.network.model.Meteor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * @author martin
 * @since 21/02/2017.
 */

@Module
public class ApplicationModule {

    private Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context providesContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    public NasaService providesNasaService() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Meteor.class, new NasaService.TypeAdapter())
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(NasaService.BASE_URL)
                .build();

        return retrofit.create(NasaService.class);
    }

}
