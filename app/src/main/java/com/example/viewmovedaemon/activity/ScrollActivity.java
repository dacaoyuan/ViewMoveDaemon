package com.example.viewmovedaemon.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.viewmovedaemon.R;
import com.example.viewmovedaemon.view.SmartScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScrollActivity extends AppCompatActivity {
    private static final String TAG = "ScrollActivity";
    @BindView(R.id.scrollView)
    SmartScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        ButterKnife.bind(this);

        scrollView.setScanScrollChangedListener(new SmartScrollView.ISmartScrollChangedListener() {
            @Override
            public void onScrolledToBottom() {
                Log.i(TAG, "onCreate: isScrolledToBottom 底部");
            }

            @Override
            public void onScrolledToTop() {
                if (scrollView.isScrolledToTop()) {
                    Log.i(TAG, "onCreate: isScrolledToTop 顶部");
                }
            }
        });





      /*  if (scrollView.isScrolledToTop()) {
            Log.i(TAG, "onCreate: isScrolledToTop 顶部");
        }
        if (scrollView.isScrolledToBottom()) {
            Log.i(TAG, "onCreate: isScrolledToBottom 底部");
        }*/

    }
}
