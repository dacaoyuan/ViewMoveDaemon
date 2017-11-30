package com.example.viewmovedaemon.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.viewmovedaemon.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 判断逻辑，仅供参考，效果还是很差
 */
public class SlidingHidActivity3 extends AppCompatActivity {
    private static final String TAG = "SlidingHidActivity3";
    private RelativeLayout top_rl;
    private ListView listview;
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    private int mTouchShop;//最小滑动距离
    protected float mFirstY;//触摸下去的位置
    protected float mCurrentY;//滑动时Y的位置
    protected int direction;//判断是否上滑或者下滑的标志

    protected boolean mShow;//判断是否执行了上滑动画
    private Animator mAnimator;//动画属性

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_hid3);


        setViews();

        //加载listview
        setListView();


    }

    /**
     * 初始化id
     */
    private void setViews() {
        top_rl = (RelativeLayout) findViewById(R.id.rl_ttt);
        listview = (ListView) findViewById(R.id.listview);

    }

    /**
     * 加载listview
     */
    private void setListView() {
        View header = View.inflate(this, R.layout.headview, null);//自定义一个头布局和顶部执行动画的布局等高就行
        listview.addHeaderView(header);//加载头布局

        //获得一个最小滑动距离
        mTouchShop = ViewConfiguration.get(this).getScaledTouchSlop();//系统级别的一个属性,判断用户的最小滑动距离的,可查看源码为16

        //给集合添加数据
        for (int i = 0; i < 40; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("str", "第" + i + "个item");
            list.add(map);
        }
        String a[] = {"str"};
        int b[] = {R.id.tv01};
        //simpleadapter加载集合数据
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.item, a, b);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {//listview的点击方法
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Toast.makeText(SlidingHidActivity3.this, list.get(arg2 - 1).get("str") + "", Toast.LENGTH_LONG).show();//-1是因为加载的头布局
            }
        });
        listview.setOnTouchListener(new View.OnTouchListener() {//listview的触摸事件
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mFirstY = event.getY();//按下时获取位置
                        break;

                    case MotionEvent.ACTION_MOVE:
                        mCurrentY = event.getY();//得到滑动的位置
                        if (mCurrentY - mFirstY > mTouchShop) {//滑动的位置减去按下的位置大于最小滑动距离  则表示向下滑动
                            direction = 0;//down
                        } else if (mFirstY - mCurrentY > mTouchShop) {//反之向上滑动
                            direction = 1;//up
                        }

                        if (direction == 1) {//判断如果是向上滑动 则执行向上滑动的动画
                            if (mShow) {//判断动画是否执行了  执行了则改变状态
                                //执行往上滑动的动画
                                tolbarAnim(1);
                                mShow = !mShow;
                            }
                        } else if (direction == 0) {//判断如果是向下滑动 则执行向下滑动的动画
                            if (!mShow) {//判断动画是否执行了  执行了则改变状态
                                //执行往下滑动的动画
                                tolbarAnim(0);
                                mShow = !mShow;
                            }
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        break;

                }
                return false;
            }
        });
    }


    private void tolbarAnim(int flag) {
        if (mAnimator != null && mAnimator.isRunning()) {//判断动画存在  如果启动了,则先关闭
            mAnimator.cancel();
        }
        if (flag == 0) {
            mAnimator = ObjectAnimator.ofFloat(top_rl, "translationY", top_rl.getTranslationY(), 0);//从当前位置位移到0位置
        } else {
            mAnimator = ObjectAnimator.ofFloat(top_rl, "translationY", top_rl.getTranslationY(), -top_rl.getHeight());//从当前位置移动到布局负高度的wiz
        }
        mAnimator.start();//执行动画

    }


}
