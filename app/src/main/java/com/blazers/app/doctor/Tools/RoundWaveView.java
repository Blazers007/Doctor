package com.blazers.app.doctor.Tools;

import android.content.Context;
import android.graphics.*;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import com.blazers.app.doctor.R;

import java.util.Random;

/**
 * Created by liang on 2015/5/21.
 */
public class RoundWaveView extends View {

    private Paint circlePaint;
    private Paint roundPaint;
    private Paint wavePaint;
    /* Wave Path */
    private Path wavePath;
    private Path wavePath2;

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
            if(getMeasuredWidth() != 0)
                fai = (fai + 5) % getMeasuredWidth();
            else
                fai += 5;
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
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(10);
        circlePaint.setColor(getResources().getColor(R.color.white));
        circlePaint.setAlpha(64);

        wavePaint = new Paint();
        wavePaint.setAntiAlias(true);
        wavePaint.setColor(Color.WHITE);
        wavePaint.setAlpha(4);

        /* Init Path */
        wavePath = new Path();

        handler.sendEmptyMessage(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centerX = getMeasuredWidth() / 2;
        int centerY = getMeasuredHeight() / 2;
        int radius = Math.min(centerX, centerY)- 20; /* 缩小一个padding */

        canvas.drawCircle(centerX, centerY, radius, roundPaint);
        RectF rectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(rectF, 0, 360, true, roundPaint);
        /* Wave */
        setPath();
        canvas.save();
        Path round = new Path();
        round.addCircle(centerX, centerY, radius, Path.Direction.CCW);
        canvas.clipPath(round, Region.Op.REPLACE);
        canvas.drawPath(wavePath, wavePaint);
        canvas.restore();

    }

    // 左右偏移 φ
    private int fai = 0;
    // 上下偏移
    private float k = 200;
    // 角速度
    private float w = 0.7f;
    // 振幅
    private int a = 20;

    void setPath() {
        int x = 0;
        int y = 0;
        wavePath.reset();
        for(int i = 0 ; i < getMeasuredWidth() ; i ++) {
            x = i;
            y = (int) (a * Math.sin((i * w + fai) * Math.PI / 180) + k);
            if (i == 0) {
                wavePath.moveTo(x, y);
            }
            wavePath.quadTo(x, y, x + 1, y);
        }
        wavePath.lineTo(getMeasuredWidth(), getMeasuredHeight());
        wavePath.lineTo(0, getMeasuredHeight());
        wavePath.close();
    }
}
