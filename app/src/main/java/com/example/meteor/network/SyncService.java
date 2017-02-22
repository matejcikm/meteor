package com.example.meteor.network;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.meteor.MeteorApplication;
import com.example.meteor.util.NetworkUtil;

import javax.inject.Inject;

import rx.Subscription;
import timber.log.Timber;

/**
 * @author martin
 * @since 21/02/2017.
 */

public class SyncService extends Service {

    private Subscription mSubscription;

    @Inject
    DataManager mDataManager;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MeteorApplication.get(this).getComponent().inject(this);
        Timber.d("Sync Service onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.d("Service started...");

        if (NetworkUtil.isOnline(this)) {

            mSubscription = mDataManager.getallMeteors()
                    .subscribe(meteors -> {
                                Timber.d("Sync success");
                            }, throwable -> {
                                Timber.e(throwable, "sync failed");
                            }
                    );
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
