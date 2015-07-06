package com.blazers.app.doctor.util;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Blazers on 2015/7/6.
 */
public class InputCheck {

    public static boolean CheckPhoneNumber(View v, String phone) {
        if (phone == null) {
            Snackbar.make(v, "输入的电话号码为空，请您检查", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (phone.length() < 11) {
            return false;
        }

        return true;
    }
}
