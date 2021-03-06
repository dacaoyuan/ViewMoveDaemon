package com.example.viewmovedaemon.view;

/**
 * Created by yuanpk on 2018/1/10.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 监听ScrollView滚动到顶部或者底部做相关事件拦截
 * <p>
 * 两种方式：
 * <p>
 * 1. onScrollChanged方式，自己计算
 * <p>
 * 2. onOverScrolled使用系统计算的结果，api >= 9才支持
 * <p>
 * 可能忽视的细节1：
 * <p>
 * 如果是手势滑动，上面两种方式都对，但是如果是调用ScrollView的smoothScrollTo和scrollTo方法来滚动的话，
 * <p>
 * 只有onScrollChanged监听对，onOverScrolled监听不对，因为通过代码来滚动话是精确滚动，onOverScrolled方法没处理这种情况
 */
public class SmartScrollView extends ScrollView {
    private static final String TAG = "SmartScrollView";
    private boolean isScrolledToTop = true;// 初始化的时候设置一下值
    private boolean isScrolledToBottom = false;

    public SmartScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private ISmartScrollChangedListener mSmartScrollChangedListener;


    /**
     * 定义监听接口
     */
    public interface ISmartScrollChangedListener {
        void onScrolledToBottom();

        void onScrolledToTop();
    }

    public void setScanScrollChangedListener(ISmartScrollChangedListener smartScrollChangedListener) {
        mSmartScrollChangedListener = smartScrollChangedListener;
    }

  /*  @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollY == 0) {
            isScrolledToTop = clampedY;
            isScrolledToBottom = false;
        } else {
            isScrolledToTop = false;
            isScrolledToBottom = clampedY;
        }
        notifyScrollChangedListeners();
    }*/





    // l oldl 分别代表水平位移
    // t oldt 代表当前左上角距离Scrollview顶点的距离
    //onScrollChanged里面有4个参数，l代表滑动后当前ScrollView可视界面的左上角在整个ScrollView的X轴中的位置，oldi也就是滑动前的X轴位置了。
    //同理，t也是当前可视界面的左上角在整个ScrollView的Y轴上的位置，oldt也就是移动前的Y轴位置了。
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //if (android.os.Build.VERSION.SDK_INT < 9) {  // API 9及之后走onOverScrolled方法监听
        //Log.i(TAG, "onScrollChanged: l=" + l+" t="+t+" oldl="+oldl+" oldt="+oldt);
        // Log.i(TAG, "onScrollChanged:  l=" + l + " oldl=" + oldl);
         Log.i(TAG, "onScrollChanged:  t=" + t + " oldt=" + oldt);

        // Log.i(TAG, "onScrollChanged: getScrollY()=" + getScrollY());

        if (t - oldt > 0) {
            Log.i(TAG, "onScrollChanged: 下滑" );
        }else {
            Log.i(TAG, "onScrollChanged: 上滑" );
        }



        if (getScrollY() == 0) {    // 小心踩坑1: 这里不能是getScrollY() <= 0
            isScrolledToTop = true;
            isScrolledToBottom = false;
        } else if (getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() == getChildAt(0).getHeight()) {
            // 小心踩坑2: 这里不能是 >=
            // 小心踩坑3（可能忽视的细节2）：这里最容易忽视的就是ScrollView上下的padding　
            isScrolledToBottom = true;
            isScrolledToTop = false;
        } else {
            isScrolledToTop = false;
            isScrolledToBottom = false;
        }
        notifyScrollChangedListeners();
        // }
        // 有时候写代码习惯了，为了兼容一些边界奇葩情况，上面的代码就会写成<=,>=的情况，结果就出bug了
        // 我写的时候写成这样：getScrollY() + getHeight() >= getChildAt(0).getHeight()
        // 结果发现快滑动到底部但是还没到时，会发现上面的条件成立了，导致判断错误
        // 原因：getScrollY()值不是绝对靠谱的，它会超过边界值，但是它自己会恢复正确，导致上面的计算条件不成立
        // 仔细想想也感觉想得通，系统的ScrollView在处理滚动的时候动态计算那个scrollY的时候也会出现超过边界再修正的情况
    }

    private void notifyScrollChangedListeners() {
        if (isScrolledToTop) {
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToTop();
            }
        } else if (isScrolledToBottom) {
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToBottom();
            }
        }
    }

    public boolean isScrolledToTop() {
        return isScrolledToTop;
    }

    public boolean isScrolledToBottom() {
        return isScrolledToBottom;
    }
}