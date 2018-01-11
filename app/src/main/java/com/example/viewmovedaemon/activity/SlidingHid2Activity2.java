package com.example.viewmovedaemon.activity;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.viewmovedaemon.R;
import com.example.viewmovedaemon.view.SmartScrollView;

/**
 * 滑动隐藏
 */
public class SlidingHid2Activity2 extends AppCompatActivity {
    private static final String TAG = "SlidingHid2Activity2";
    private GestureDetectorCompat mDetectorCompat;
    private ScrollView mScrollView;

    private LinearLayout mLinearLayout;
    private int mOriginLinearTop;
    private int mOriginLinearBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_hid22);
        mLinearLayout = (LinearLayout) findViewById(R.id.linear);


        mLinearLayout.post(new Runnable() {
            @Override
            public void run() {
                mOriginLinearTop = mLinearLayout.getTop();
                mOriginLinearBottom = mLinearLayout.getBottom();
                Log.i(TAG, "run: mOriginLinearTop=" + mOriginLinearTop);

            }
        });


        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());
        mDetectorCompat.setIsLongpressEnabled(false);

        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetectorCompat.onTouchEvent(event);
                return false;
            }
        });


    }


    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if (Math.abs(distanceY) > Math.abs(distanceX)) {//判断是否竖直滑动
                int linearTop = mLinearLayout.getTop();
                int linearBottom = mLinearLayout.getBottom();
                Log.i(TAG, "onScroll: linearTop=" + linearTop);


                //是否向下滑动
                boolean isScrollDown = e1.getRawY() < e2.getRawY() ? true : false;

                //根据滑动方向和linearLayout当前的位置判断是否需要移动LinearLayout的位置
                if (!ifNeedScroll(isScrollDown)) {

                    return false;
                }


                if (isScrollDown) {

                    Log.i(TAG, "onScroll: 下移");

                    //下滑下移mLinearLayout
                    int top = linearTop + (int) Math.abs(distanceY);
                    int bottom = linearBottom + (int) Math.abs(distanceY);
                    if (top >= mOriginLinearTop) {
                        top = mOriginLinearTop;
                        bottom = mOriginLinearBottom;

                        //此时，mLinearLayout 下移至初始状态啦

                    }
                    mLinearLayout.setTop(top);
                    mLinearLayout.setBottom(bottom);

                    // TODO: 2017/11/30 mLinearLayout 下移 至 初始状态期间，滚动视图不应该发生滚动（父控件把事件拦截掉）
                    //换句话说，当 mLinearLayout 下移初始状态之后，滚动视图才可以滚动


                } else if (!isScrollDown) {
                    Log.i(TAG, "onScroll: 上移");


                    //上滑上移mLinearLayout
                    mLinearLayout.setTop(linearTop - (int) Math.abs(distanceY));
                    mLinearLayout.setBottom(linearBottom - (int) Math.abs(distanceY));

                    // TODO: 2017/11/30  当mLinearLayout上移至完全隐藏期间，滚动视图不应该发生滚动（父控件把事件拦截掉）
                    //换句话说，当 mLinearLayout 上移至完全隐藏之后，滚动视图才可以滚动
                }
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }


        //写一个方法，根据滑动方向和linearBottom当前的位置，判断按钮是否应该继续滑动
        private boolean ifNeedScroll(boolean isScrollDown) {
            int nowLinearTop = mLinearLayout.getTop();
            Log.i(TAG, "ifNeedScroll: nowLinearTop=" + nowLinearTop + "  mOriginLinearTop=" + mOriginLinearTop);

            //linearLayout不能超出原来的上边界
            if (isScrollDown && nowLinearTop >= mOriginLinearTop) return false;

            //判断按钮是否在屏幕范围内，如果不在，则不需要再移动位置
            if (!isScrollDown) {
                return isInScreen(mLinearLayout);
            }

            return true;
        }

        //判断一个控件是否在屏幕范围内
        private boolean isInScreen(View view) {
            int width, height;
            Point p = new Point();
            getWindowManager().getDefaultDisplay().getSize(p);
            width = p.x;
            height = p.y;
            Rect rect = new Rect(0, 0, width, height);
            if (!view.getLocalVisibleRect(rect)) {
                // TODO: 2017/11/30  如果不在屏幕内，滚动视图，才可以开始滑动
                return false;
            }


            return true;
        }


    }


}
