package com.example.meteor.ui;

import android.support.annotation.StringRes;

import eu.inloop.viewmodel.IView;

/**
 * @author martin
 * @since 21/02/2017.
 */

public interface BaseView extends IView {

    void showToast(String message);

    void showToast(@StringRes int message);
}
