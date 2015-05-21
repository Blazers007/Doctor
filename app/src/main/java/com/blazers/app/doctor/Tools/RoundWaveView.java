package com.blazers.app.doctor.Tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import com.blazers.app.doctor.R;

/**
 * Created by liang on 2015/5/21.
 */
public class RoundWaveView extends View {

    private Paint circlePaint;
    private Paint roundPaint;
    private Paint wavePaint;

    public RoundWaveView(Context context) {
        super(context);
        init();
    }

    public RoundWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measure(widthMeasureSpec, true);
        int height = measure(heightMeasureSpec, false);
        if (width < height) {
            setMeasuredDimension(width, width);
        } else {
            setMeasuredDimension(height, height);
        }
    }

    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight()
                : getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth()
                    : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
            sendEmptyMessageDelayed(0, 16);
        }
    };

    void init() {

        /*
        http://blog.csdn.net/kezhongke/article/details/42642673

        http://blog.csdn.net/wangjinyu501/article/details/39527021

        https://github.com/gabrielemariotti/cardslib/blob/master/doc/OVERVIEW.md
        * */

        roundPaint = new Paint();
        roundPaint.setAntiAlias(true);
        roundPaint.setColor(getResources().getColor(R.color.red500));

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setAlpha(64);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(10);
        circlePaint.setColor(getResources().getColor(R.color.white));

        wavePaint = new Paint();
        wavePaint.setAntiAlias(true);
        wavePaint.setAlpha(192);
        wavePaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centerX = getMeasuredWidth() / 2;
        int centerY = getMeasuredHeight() / 2;
        int radius = Math.min(centerX, centerY);

        canvas.drawCircle(centerX, centerY, radius, roundPaint);

        RectF rectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(rectF, 0, 359, false, circlePaint);

        /* Wave */
    }
}
