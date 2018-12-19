package com.example.viewmovedaemon.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viewmovedaemon.R;
import com.example.viewmovedaemon.view.CustomLinearLayout;
import com.example.viewmovedaemon.view.DragView;

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
    @BindView(R.id.dragView)
    DragView mDragView;

    @BindView(R.id.imageView)
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_move);
        ButterKnife.bind(this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ViewMoveActivity.onClick");
                //animationSummary(v);

                likeAnimation(mImageView);
            }
        });


        //linear.smoothScrollTo(-500, -500, 9);
    }


    private void likeAnimation(final View view) {

        view.setVisibility(View.VISIBLE);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 2f, 1f).setDuration(1 * 1000),
                ObjectAnimator.ofFloat(view, "scaleY", 2f, 1f).setDuration(1 * 1000)

                //ObjectAnimator.ofFloat(view, "alpha", 0f).setDuration(2 * 1000)
        );
        //animatorSet.setInterpolator(new FastOutLinearInInterpolator());
        //animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        //animatorSet.setInterpolator(new AnticipateInterpolator());
        // animatorSet.setInterpolator(new AnticipateOvershootInterpolator());//其变化开始向后甩，然后向前甩，过冲到目标值，最后又回到了终值
        animatorSet.setInterpolator(new BounceInterpolator());//其变化在结束时反弹

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "onAnimationEnd: ");


                ObjectAnimator objectAnimator = ObjectAnimator
                        .ofFloat(view, "alpha", 0f)
                        .setDuration(1 * 1000);

                objectAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.i(TAG, "onAnimationEnd: ");
                        view.setVisibility(View.GONE);
                        view.setAlpha(1f);//切记：在动画执行完毕后，要把控件的透明度再设置回来，这样每次点击都有效果。
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                objectAnimator.start();


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.i(TAG, "onAnimationRepeat: ");
            }
        });

        animatorSet.start();


    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        moveMethod3();

        animationSummary(button);//如果想一打开就展示揭露动画的话，需要在这个方法内，使用揭露动画

        //你移动后，它还是获取的是原始状态时相对于父容器的坐标。因此前后的值是一样的。
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
        linear.scrollTo((int) getResources().getDimension(R.dimen.px_500), (int) getResources().getDimension(R.dimen.px_500));

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


    /**
     * 这个方法与view的移动关系不大，着重总结，各种动画效果的实现。
     */
    public void animationSummary(final View view) {
        /**
         * 平移
         */
      /*  AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, 500),//.setDuration(6 * 1000),
                ObjectAnimator.ofFloat(view, "translationY", 0, 500)//.setDuration(6 * 1000)
        );
        animatorSet.start();*/
       /* ObjectAnimator.ofFloat(view, "translationX", 0, 500).setDuration(6 * 1000).start();
        ObjectAnimator.ofFloat(view, "translationY", 0, 500).setDuration(6 * 1000).start();*/


        //view.setTranslationX(300);
        //view.setTranslationY(300);


        /*ViewCompat.animate(view).translationX(500).translationY(500).setDuration(3 * 1000)
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {

                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        ViewCompat.animate(view).translationX(0).translationY(0).setDuration(3 * 1000)
                                .setStartDelay(2 * 1000)
                                .setListener(new ViewPropertyAnimatorListener() {
                                    @Override
                                    public void onAnimationStart(View view) {

                                    }

                                    @Override
                                    public void onAnimationEnd(View view) {

                                    }

                                    @Override
                                    public void onAnimationCancel(View view) {

                                    }
                                }).start();

                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                }).start();*/
        /*ViewCompat.animate(view).translationZ(500).setDuration(3 * 1000)
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {

                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        ViewCompat.animate(view).translationZ(0).setDuration(3 * 1000)
                                .setListener(new ViewPropertyAnimatorListener() {
                                    @Override
                                    public void onAnimationStart(View view) {

                                    }

                                    @Override
                                    public void onAnimationEnd(View view) {
                                    }

                                    @Override
                                    public void onAnimationCancel(View view) {

                                    }
                                }).start();
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                }).start();*/


        /**
         * 缩放
         */
        /*AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 0f).setDuration(2 * 1000),
                ObjectAnimator.ofFloat(view, "scaleY", 0f).setDuration(2 * 1000),
                ObjectAnimator.ofFloat(view, "alpha", 0f).setDuration(2 * 1000)
        );
        animatorSet.setInterpolator(new FastOutLinearInInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "onAnimationEnd: ");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.i(TAG, "onAnimationRepeat: ");
            }
        });

        animatorSet.start();*/

       /* ObjectAnimator scaleXObject = ObjectAnimator.ofFloat(view, "scaleX", 0f);
        scaleXObject.setDuration(4 * 1000);
        scaleXObject.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "onAnimationEnd: ");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        scaleXObject.start();*/
        //ObjectAnimator.ofFloat(view, "scaleY", 0, 5).setDuration(4 * 1000).start();
        
        
        /*ViewCompat.animate(view).scaleX(0.0F).scaleY(0.0F).alpha(0.0F).setDuration(3 * 1000).setStartDelay(2 * 1000)
                .setInterpolator(new FastOutSlowInInterpolator())
                .withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        Log.i(TAG, "onAnimationStart: ");
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        Log.i(TAG, "onAnimationEnd: ");
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        Log.i(TAG, "onAnimationCancel: ");
                    }
                }).start();*/


        /**
         * 旋转
         */
       /* AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
               // ObjectAnimator.ofFloat(view, "rotationX", 360f).setDuration(4 * 1000),
                ObjectAnimator.ofFloat(view, "rotationY", 360f).setDuration(4 * 1000)
        );
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "onAnimationEnd: ");
                ObjectAnimator.ofFloat(view, "rotationX", -360f).setDuration(4 * 1000).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.i(TAG, "onAnimationRepeat: ");
            }
        });

        animatorSet.start();*/

       /* ViewCompat.animate(view).rotationX(360F).setDuration(3 * 1000)
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {

                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        ViewCompat.animate(view).rotationX(-360F).setDuration(3 * 1000)
                                .setListener(new ViewPropertyAnimatorListener() {
                                    @Override
                                    public void onAnimationStart(View view) {

                                    }

                                    @Override
                                    public void onAnimationEnd(View view) {
                                    }

                                    @Override
                                    public void onAnimationCancel(View view) {

                                    }
                                }).start();
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                }).start();*/


        /**
         * 揭露动画
         * >=Android5.0 API>=21才可以使用
         createCircularReveal (View view,  动画作用的View
         int centerX,      圆中心的x坐标
         int centerY,      圆中心的y坐标
         float startRadius, 圆的开始半径
         float endRadius)   圆的结束半径
         */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = ViewAnimationUtils.createCircularReveal
                    (mDragView,
                            mDragView.getWidth() / 2,
                            mDragView.getHeight() / 2,
                            0,
                            mDragView.getHeight());
            animator.setDuration(3 * 1000);
            animator.start();
        }


    }


}
