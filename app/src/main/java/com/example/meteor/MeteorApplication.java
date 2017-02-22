package com.example.meteor;

import android.app.Application;
import android.content.Context;

import com.example.meteor.inject.ApplicationComponent;
import com.example.meteor.inject.ApplicationModule;
import com.example.meteor.inject.DaggerApplicationComponent;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * @author martin
 * @since 21/02/2017.
 */

public class MeteorApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(configuration);
    }

    public static MeteorApplication get(Context context) {
        return (MeteorApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }

        return mApplicationComponent;
    }

}
