package com.example.viewmovedaemon;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by yuanpk on 2017/11/29.
 */

public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final String TAG = "MyGestureListener";
    public Button mButton;
    private Activity activity;
    private int mOriginButtonTop;
    private int mOriginButtonBottom;

    public MyGestureListener(Button mButton, Activity activity, int mOriginButtonTop, int mOriginButtonBottom) {
        Log.i(TAG, "MyGestureListener: mOriginButtonTop=" + mOriginButtonTop);
        this.mButton = mButton;
        this.activity = activity;
        this.mOriginButtonTop = mOriginButtonTop;
        this.mOriginButtonBottom = mOriginButtonBottom;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        if (Math.abs(distanceY) > Math.abs(distanceX)) {//判断是否竖直滑动

            int buttonTop = mButton.getTop();
            int buttonBottom = mButton.getBottom();
            Log.i(TAG, "onScroll: buttonTop=" + buttonTop);

            //是否向下滑动
            boolean isScrollDown = e1.getRawY() < e2.getRawY() ? true : false;

            //根据滑动方向和mButton当前的位置判断是否需要移动Button的位置
            if (!ifNeedScroll(isScrollDown)) {
                return false;
            }

            if (isScrollDown) {
                Log.i(TAG, "onScroll: 上移");
                //下滑上移Button
                int top = buttonTop - (int) Math.abs(distanceY);
                int bottom = buttonBottom - (int) Math.abs(distanceY);
                if (top <= mOriginButtonTop) {
                    top = mOriginButtonTop;
                    bottom = mOriginButtonBottom;
                }
                mButton.setTop(top);
                mButton.setBottom(bottom);
                //mButton.setTop(buttonTop - (int) Math.abs(distanceY));
                //mButton.setBottom(buttonBottom - (int) Math.abs(distanceY));
            } else if (!isScrollDown) {
                Log.i(TAG, "onScroll: 下移");
                //上滑下移Button
                mButton.setTop(buttonTop + (int) Math.abs(distanceY));
                mButton.setBottom(buttonBottom + (int) Math.abs(distanceY));
            }

        }

        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    //写一个方法，根据滑动方向和mButton当前的位置，判断按钮是否应该继续滑动
    private boolean ifNeedScroll(boolean isScrollDown) {
        int nowButtonTop = mButton.getTop();
        Log.i(TAG, "ifNeedScroll: nowButtonTop=" + nowButtonTop + "  mOriginButtonTop=" + mOriginButtonTop);

        //button不能超出原来的上边界
        if (isScrollDown && nowButtonTop <= mOriginButtonTop) return false;

        //判断按钮是否在屏幕范围内，如果不在，则不需要再移动位置
        if (!isScrollDown) {
            return isInScreen(mButton);
        }

        return true;
    }

    //判断一个控件是否在屏幕范围内
    private boolean isInScreen(View view) {
        int width, height;
        Point p = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(p);
        width = p.x;
        height = p.y;
        Rect rect = new Rect(0, 0, width, height);
        if (!view.getLocalVisibleRect(rect)) return false;

        return true;
    }


}
