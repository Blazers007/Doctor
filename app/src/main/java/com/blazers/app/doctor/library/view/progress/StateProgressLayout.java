package com.blazers.app.doctor.library.view.progress;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.blazers.app.doctor.R;

/**
 * Created by liang on 2015/6/5.
 */
public class StateProgressLayout extends LinearLayout {

    private View root;

    public StateProgressLayout(Context context) {
        super(context);
        init(context, null);
    }

    public StateProgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StateProgressLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    void init(Context ctx, AttributeSet attrs) {
        root = LayoutInflater.from(ctx).inflate(R.layout.view_state_progress, this, true);
    }

}
