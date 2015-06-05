package com.blazers.app.doctor.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.blazers.app.doctor.R;

/**
 * Created by liang on 2015/5/21.
 */
public class RippleView extends View {

    private Paint ripplePaint;
    private Bitmap rippleBitmap;
    private int rippleTag = -1;
    private int max = 1;
    private static final int[] alphaList = new int[]{64, 128, 192};

    public RippleView(Context context) {
        super(context);
        init();
    }

    public RippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        ripplePaint = new Paint();
        ripplePaint.setAntiAlias(true);
        ripplePaint.setStyle(Paint.Style.FILL);
        ripplePaint.setColor(getResources().getColor(R.color.red500));

        handler.sendEmptyMessage(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            rippleTag = (rippleTag + 1) % (max * 4);
            invalidate();
            sendEmptyMessageDelayed(0, 16);
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getMeasuredWidth() / 2;
        int centerY = getMeasuredHeight() / 2;
        int maxRadiusOnePiece = max = Math.min(centerX, centerY) / 4;
        if (rippleTag == -1)
            rippleTag = maxRadiusOnePiece;
        /* 分层绘制 从大到小*/
        for(int i = 0 ; i < 3; i ++) {
            int size = (rippleTag + (i * maxRadiusOnePiece)) % (maxRadiusOnePiece * 4);
            int alpha = (int) (255*(size - maxRadiusOnePiece) *1.0d / (maxRadiusOnePiece * 3.0d));
            Log.e("State[" + i + "]", " Size : " + size + "   Alpha : " + alpha);
            ripplePaint.setAlpha(255 - alpha);
            canvas.drawCircle(centerX, centerY, size, ripplePaint);
        }
        /* 绘制中心圆 */
        ripplePaint.setAlpha(255);
        canvas.drawCircle(centerX, centerY, maxRadiusOnePiece, ripplePaint);
    }
}
