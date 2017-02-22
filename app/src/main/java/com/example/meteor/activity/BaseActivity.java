package com.example.meteor.activity;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.meteor.BR;
import com.example.meteor.R;
import com.example.meteor.ui.BaseView;
import com.example.meteor.viewmodel.BaseViewModel;

import eu.inloop.viewmodel.base.ViewModelBaseActivity;

/**
 * @author martin
 * @since 21/02/2017.
 */

public abstract class BaseActivity<T extends BaseView, VM extends BaseViewModel<T>, B extends ViewDataBinding> extends ViewModelBaseActivity<T,VM> implements BaseView {

    private B mBinding;

    @IntDef({IndicatorType.BACK, IndicatorType.NONE})
    public @interface IndicatorType {
        int NONE = 0;
        int BACK = 1;
    }

    public abstract B setBindingContentView();

    private B setupBinding() {
        B binding = setBindingContentView();
        binding.setVariable(BR.view, this);
        binding.setVariable(BR.viewModel, getViewModel());

        return binding;
    }

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = setupBinding();
    }

    public void setActionBar(String title, @IndicatorType int type) {
        ActionBar actionBar;
        if ((actionBar = setActionBar(type)) != null) {
            actionBar.setTitle(title);
        }
    }

    public ActionBar setActionBar(@IndicatorType int type) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            if (type == IndicatorType.BACK) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            return actionBar;
        }
        return null;
    }

    public B getBinding() {
        return mBinding;
    }

    @Override
    public void showToast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
