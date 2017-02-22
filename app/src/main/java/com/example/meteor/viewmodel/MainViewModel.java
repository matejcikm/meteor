package com.example.meteor.viewmodel;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.meteor.network.model.Meteor;
import com.example.meteor.ui.MainView;

import io.realm.RealmResults;
import io.realm.Sort;

/**
 * @author martin
 * @since 21/02/2017.
 */

public class MainViewModel extends BaseViewModel<MainView> {

    private RealmResults<Meteor> mMeteors;

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        mMeteors = mRealm.where(Meteor.class).findAllSorted("yearInternal", Sort.DESCENDING);
    }

    public RealmResults<Meteor> getMeteors() {
        return mMeteors;
    }
}
