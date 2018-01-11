package com.example.viewmovedaemon.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.viewmovedaemon.R;
import com.example.viewmovedaemon.view.CustomScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ElseActivity extends AppCompatActivity {
    private static final String TAG = "ElseActivity";
    @BindView(R.id.scrollView)
    CustomScrollView scrollView;
    @BindView(R.id.edit)
    EditText edit;
    private int editHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_else);
        ButterKnife.bind(this);

        edit.post(new Runnable() {
            @Override
            public void run() {
                editHeight = edit.getHeight();
            }
        });


        final int[] firstY = {0};
        final int[] currentY = {0};


        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        firstY[0] = (int) event.getY();


                        break;
                    case MotionEvent.ACTION_MOVE:

                        currentY[0] = (int) event.getY();
                        int i = currentY[0] - firstY[0];
                        if (i >=editHeight) {
                            return false;
                        }

                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }


                return false;
            }
        });


    }


}
