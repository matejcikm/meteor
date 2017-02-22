package com.example.meteor.viewmodel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.meteor.activity.DetailActivity;
import com.example.meteor.network.model.Meteor;
import com.example.meteor.ui.DetailView;

/**
 * @author martin
 * @since 21/02/2017.
 */
public class DetailViewModel extends BaseViewModel<DetailView> {

    private Meteor mMeteor;

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);

        if (arguments != null) {
            int id = arguments.getInt(DetailActivity.ARG_ID);
            mMeteor = mRealm.where(Meteor.class).equalTo("id", id).findFirstAsync();
        }
    }

    @Override
    public void onBindView(@NonNull DetailView view) {
        super.onBindView(view);

        if (mMeteor.isLoaded()) {
            view.showMeteor(mMeteor);
        } else {
            mMeteor.addChangeListener(element -> view.showMeteor(mMeteor));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mMeteor != null && mMeteor.isManaged()) {
            mMeteor.removeChangeListeners();
        }
    }

    public Meteor getMeteor() {
        return mMeteor;
    }
}
