package com.example.meteor.viewmodel;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.example.meteor.ui.BaseView;

import eu.inloop.viewmodel.AbstractViewModel;
import io.realm.Realm;
import rx.subscriptions.CompositeSubscription;

/**
 * @author martin
 * @since 21/02/2017.
 */

public abstract class BaseViewModel<T extends BaseView> extends AbstractViewModel<T> {

    protected Realm mRealm;
    protected CompositeSubscription mSubscription = new CompositeSubscription();

    @CallSuper
    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void onBindView(@NonNull T view) {
        super.onBindView(view);
    }

    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mRealm.isInTransaction()) {
            mRealm.cancelTransaction();
        }

        mRealm.close();

        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    public void handleError(String message) {

        if (getView() != null) {
            getView().showToast(message);
        }
    }

    public void handleError(@StringRes int message) {

        if (getView() != null) {
            getView().showToast(message);
        }
    }
}
