package com.blazers.app.doctor.library.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

/**
 * Created by Blazers on 2015/7/7.
 */
public class LockedViewPager extends ViewPager {

    public LockedViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
       return false;
    }
}
