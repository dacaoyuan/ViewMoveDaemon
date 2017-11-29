package com.example.viewmovedaemon.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by yuanpk on 2017/11/29.
 */

public class CustomLinearLayout extends LinearLayout {

    private Scroller scroller;

    public CustomLinearLayout(Context context) {
        super(context);

    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);



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
