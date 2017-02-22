package com.example.meteor.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;

import com.example.meteor.MeteorApplication;
import com.example.meteor.R;
import com.example.meteor.adapter.MainAdapter;
import com.example.meteor.databinding.ActivityMainBinding;
import com.example.meteor.network.SyncService;
import com.example.meteor.ui.MainView;
import com.example.meteor.util.PreferencesHelper;
import com.example.meteor.viewmodel.MainViewModel;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<MainView, MainViewModel, ActivityMainBinding> implements MainView {

    @Inject
    PreferencesHelper mPreferencesHelper;

    @Override
    public ActivityMainBinding setBindingContentView() {
        return DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Nullable
    @Override
    public Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MeteorApplication.get(this).getComponent().inject(this);
        setModelView(this);

        MainAdapter adapter = new MainAdapter(this, this, getViewModel().getMeteors());
        getBinding().mainRecyclerview.setAdapter(adapter);

        setActionBar(IndicatorType.NONE);

        if (!mPreferencesHelper.getBooLean(PreferencesHelper.KEY_ALARM_SET)) {
            setAlarmManager();
            mPreferencesHelper.setBoolean(PreferencesHelper.KEY_ALARM_SET, true);
        }
    }

    @Override
    public void onItemClick(int id) {
        Intent intent = DetailActivity.newIntent(this, id);
        startActivity(intent);
    }

    private void setAlarmManager() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long interval = DateUtils.DAY_IN_MILLIS;
        Intent serviceIntent = SyncService.getStartIntent(this);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, serviceIntent, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
    }
}
