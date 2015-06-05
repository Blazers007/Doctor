package com.blazers.app.doctor.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.blazers.app.doctor.R;

/**
 * Created by liang on 2015/6/5.
 */
public class StateProgressView extends View {

    private Paint linePaint, textPaint, donePaint, doneTextPaint;

    public StateProgressView(Context context) {
        super(context);
        init(context, null);
    }

    public StateProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StateProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    void init(Context ctx, AttributeSet attrs) {
        /* Line Paint */
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(14);
        linePaint.setColor(Color.rgb(193, 193, 193));

        /* Text Paint */
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(30);

        /* Done Paint */

        donePaint = new Paint();
        donePaint.setAntiAlias(true);
        donePaint.setStrokeWidth(12);
        donePaint.setColor(getResources().getColor(R.color.blue500));

        /* Done Text */
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();

        int seg = width / 8;

        /* 画线 */
        canvas.drawLine(seg, 60, seg*7, 60, linePaint);

        /* 画圆圈 文字 */
        for (int i = 1 ; i < 8 ; i+=2 ){
            canvas.drawCircle(seg*i, 60, 30, linePaint);
            canvas.drawText("阶段一", seg*i, 130, textPaint);
        }

        /* 画出进度 */
        canvas.drawCircle(seg, 60, 26, donePaint);
        canvas.drawLine(seg, 60, seg*2, 60, donePaint);
    }
}
