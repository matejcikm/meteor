package com.example.meteor.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.meteor.inject.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author martin
 * @since 22/02/2017.
 */

@Singleton
public class PreferencesHelper {

    private static final String PREF_FILE_NAME = "meteor_pref_file";
    public static final String KEY_ALARM_SET = "key_alarm_set";

    private final SharedPreferences mPreferences;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void setBoolean(String key, boolean value) {
        mPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBooLean(String key) {
        return mPreferences.getBoolean(key, false);
    }
}
