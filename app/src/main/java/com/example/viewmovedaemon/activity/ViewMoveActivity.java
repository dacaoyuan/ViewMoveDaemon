package com.example.viewmovedaemon.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.example.viewmovedaemon.R;
import com.example.viewmovedaemon.view.CustomLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewMoveActivity extends AppCompatActivity {
    private static final String TAG = "ViewMoveActivity";
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.linear)
    CustomLinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_move);
        ButterKnife.bind(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ViewMoveActivity.onClick");
            }
        });
        //linear.smoothScrollTo(-500, -500, 9);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        moveMethod3();

        button.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "moveMethod1:移动后 getX()=" + button.getX() + " getY= " + button.getY());
                Log.i(TAG, "moveMethod1:移动后 getLeft()=" + button.getLeft() + " getTop()= " + button.getTop());
            }
        });

    }

    //scrollTo,scrollBy
    public void moveMethod1() {
        Log.i(TAG, "moveMethod1:移动前 getX()=" + button.getX() + " getY= " + button.getY());
        Log.i(TAG, "moveMethod1:移动前 getLeft()=" + button.getLeft() + " getTop()= " + button.getTop());
        //向右和向下移动都为 -（负值）
        //View view = (View) button.getParent();
        //view.scrollBy((int) getResources().getDimension(R.dimen.px_500), (int) getResources().getDimension(R.dimen.px_500));
        linear.scrollBy((int) getResources().getDimension(R.dimen.px_500), (int) getResources().getDimension(R.dimen.px_500));


    }

    //属性动画
    public void moveMethod2() {


        /*AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(button, "translationX", 0, 500),//.setDuration(6 * 1000),
                ObjectAnimator.ofFloat(button, "translationY", 0, 500)//.setDuration(6 * 1000)
        );
        animatorSet.start();*/
       /* ObjectAnimator.ofFloat(button, "translationX", 0, 500).setDuration(6 * 1000).start();
        ObjectAnimator.ofFloat(button, "translationY", 0, 500).setDuration(6 * 1000).start();*/


        button.setTranslationX(300);
        button.setTranslationY(300);

    }

    //位移动画
    public void moveMethod22() {
        TranslateAnimation anim = new TranslateAnimation(0, 500, 0, 500);
        anim.setFillAfter(true);
        anim.setDuration(2 * 1000);
        button.startAnimation(anim);
    }

    //setLayoutParams
    public void moveMethod3() {
        Log.i(TAG, "moveMethod1:移动前 getX()=" + button.getX() + " getY= " + button.getY());
        Log.i(TAG, "moveMethod1:移动前 getLeft()=" + button.getLeft() + " getTop()= " + button.getTop());

        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) button.getLayoutParams();
        lp.leftMargin = button.getLeft() + 500;
        lp.topMargin = button.getTop() + 300;
        //button.setLayoutParams(lp);
        //或者
       button.requestLayout();

    }

    /**
     * 如果你将滑动后的目标位置的坐标传递给layout()，这样子就会把view的位置给重新布置了一下，
     * 在视觉上就是view的一个滑动的效果。
     */
    public void moveMethod4() {
        button.layout(button.getLeft() + 300, button.getTop() + 100, button.getRight() + 300, button.getBottom() + 100);
        //和下面的方法一个道理
        /*button.setLeft(button.getLeft() + 300);
        button.setRight(button.getRight() + 300);
        button.setTop(button.getTop() + 100);
        button.setBottom(button.getBottom() + 100);*/
    }

    /**
     * 其实这两个方法分别是对左右移动和上下移动的封装，传入的就是偏移量。
     */
    public void moveMethod44() {
        button.offsetLeftAndRight(500);
        button.offsetTopAndBottom(500);
    }


}
