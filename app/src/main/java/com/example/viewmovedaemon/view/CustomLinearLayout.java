package com.example.viewmovedaemon.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by yuanpk on 2017/11/29.
 */

public class CustomLinearLayout extends LinearLayout {
    private static final String TAG = "CustomLinearLayout";
    private Scroller scroller;

    public static final float DEFAULT_RADIUS = 150;

    private Canvas canvas;
    private Paint mPaint;
    private int alpha = 100;
    private float radius = 0;
    private float x = 0;
    private float y = 0;

    public CustomLinearLayout(Context context) {
        super(context);

    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);

        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(8);

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        /**
         * 点击水波纹效果
         */
        ObjectAnimator rippleAnimator = ObjectAnimator.ofFloat(this, "radius", 0f, 1f).setDuration(1000);
        rippleAnimator.start();

        return super.onTouchEvent(event);
    }

    /**
     * ObjectAnimators自动执行
     *
     * @param currentValue
     */
    public void setRadius(float currentValue) {
        alpha = (int) (100 * (1 - currentValue) / 2);
        radius = DEFAULT_RADIUS * currentValue;
        invalidate();

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.canvas == null) {
            this.canvas = canvas;
        }
        //方便刷新透明度
       mPaint.setARGB(alpha, 0, 125, 251);



        canvas.drawCircle(x, y, radius, mPaint);
    }


    //缓慢滚动到指定位置
    public void smoothScrollTo(int destX, int destY, int duration) {


        int scrollX = getScrollX();
        int deltaX = destX - scrollX;

        int scrollY = getScrollY();
        int deltaY = destY - scrollY;

        scroller.startScroll(scrollX, 0, deltaX, deltaY, duration * 1000);
        invalidate();


    }


    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }


}
