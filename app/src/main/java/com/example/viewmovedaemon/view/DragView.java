package com.example.viewmovedaemon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yuanpk on 2017/11/29.
 */
public class DragView extends View {
    private static final String TAG = "DragView";
    private int lastX;
    private int lastY;

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onTouchEvent(MotionEvent event) {
        //获取到手指处的横坐标和纵坐标
        int x = (int) event.getX();
        int y = (int) event.getY();
        //Log.i(TAG, "onTouchEvent: x= " + x + " y= " + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算移动的距离
                int offX = x - lastX;
                int offY = y - lastY;
                //调用layout方法来重新放置它的位置
                //layout(getLeft() + offX, getTop() + offY, getRight() + offX, getBottom() + offY);
                offsetLeftAndRight(offX);
                offsetTopAndBottom(offY);
                break;
        }
        return true;
    }
}