package com.example.meteor.util;

import android.databinding.BindingAdapter;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author martin
 * @since 21/02/2017.
 */

public class BindingUtility {

    private static SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    private BindingUtility() {

    }

    @BindingAdapter({"date"})
    public static void setDate(TextView textView, @Nullable Date date) {
        if (date == null) {
            textView.setText("");
        } else {
            textView.setText(sSimpleDateFormat.format(date));
        }
    }
}
