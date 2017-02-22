package com.example.meteor.inject;

import android.content.Context;

import com.example.meteor.activity.MainActivity;
import com.example.meteor.network.DataManager;
import com.example.meteor.network.SyncService;
import com.example.meteor.util.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author martin
 * @since 21/02/2017.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    void inject(MainActivity mainActivity);

    @ApplicationContext
    Context context();

    DataManager dataManager();

    PreferencesHelper preferencesHelper();
}
