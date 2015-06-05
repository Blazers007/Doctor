package com.blazers.app.doctor.Util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by liang on 2015/6/5.
 */
public class PhoneUtils {

    public static int getScreenWidth(Activity act) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
