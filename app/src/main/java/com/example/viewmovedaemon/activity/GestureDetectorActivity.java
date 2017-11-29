package com.example.viewmovedaemon.activity;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.viewmovedaemon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 手势检测
 */

public class GestureDetectorActivity extends AppCompatActivity {
    private static final String TAG = "GestureDetectorActivity";
    @BindView(R.id.button1)
    Button button1;

    private GestureDetectorCompat gestureDetectorCompat;

    //private GestureDetector gestureDetectorCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_detector);
        ButterKnife.bind(this);


        gestureDetectorCompat = new GestureDetectorCompat(GestureDetectorActivity.this, new MyGestureListener());

        //gestureDetectorCompat = new GestureDetector(GestureDetectorActivity.this, new MyGestureListener());
        //gestureDetectorCompat.setIsLongpressEnabled(false);


        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean consume = gestureDetectorCompat.onTouchEvent(event);
                Log.i(TAG, "onTouch: consume=" + consume);
                return false;
            }
        });

    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.i(TAG, "onScroll: 拖动");
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.i(TAG, "onDown: 按下一瞬间，就会触发");
            return super.onDown(e);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.i(TAG, "onSingleTapUp: 单击");
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            Log.i(TAG, "onLongPress: 长按");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.i(TAG, "onFling: 快速滑动");
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.i(TAG, "onDoubleTap: 双击");
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }
    }
}
