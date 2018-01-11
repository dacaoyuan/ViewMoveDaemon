package com.example.viewmovedaemon.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.example.viewmovedaemon.R;
import com.example.viewmovedaemon.adapter.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 滑动时的渐变隐藏和渐变显示
 */

public class SlidingGradientHidActivity extends AppCompatActivity {
    private static final String TAG = "SlidingGradientHidActiv";
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.home_text)
    TextView mTextView ;

    MyRecyclerViewAdapter myAdapter;
    private int mTextViewHeight;
    private RecyclerViewHeader mRecyclerViewHeader;
    private int mRecyclerHeaderBannerHeight;

    //头部的高度
    private int mRecyclerHeaderHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_gradient_hid);
        ButterKnife.bind(this);

        myAdapter = new MyRecyclerViewAdapter(getData(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(myAdapter);

        //获取到文本的高度
        mTextViewHeight = mTextView.getHeight();
        Log.i(TAG, "onCreate: mTextViewHeight="+mTextViewHeight);

        //轮播图片的高度--和xml图片的高度是一样的
        mRecyclerHeaderBannerHeight = (int) getResources().getDimension(R.dimen.home_page_banner_height);
        //RecyclerView每个Item之间的距离,和Adapter中设置的距离一样
        final int recyclerItemHeight = (int) getResources().getDimension(R.dimen.home_page_list_item_margin_top);

        //添加头部视图,其布局文件就忽略
        mRecyclerViewHeader = RecyclerViewHeader.fromXml(this, R.layout.headview2);
        //将头部视图添加到RecyclerView中
        mRecyclerViewHeader.attachTo(mRecyclerView);

        //设置其滑动事件
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i(TAG, "onScrolled: dx="+dx);
                //设置其透明度
                float alpha = 0;
                int scollYHeight = getScollYHeight(true, mRecyclerHeaderHeight);
                //起始截止变化高度,如可以变化高度为mRecyclerHeaderHeight
                int baseHeight = mRecyclerHeaderBannerHeight - recyclerItemHeight - mTextViewHeight;
                if(scollYHeight >= baseHeight) {
                    //完全不透明
                    alpha = 1;
                }else {
                    //产生渐变效果
                    alpha = scollYHeight / (baseHeight*1.0f);
                }
                mTextView.setAlpha(alpha);

            }
        });

        //第一次进来其状态显示
        mRecyclerViewHeader.post(new Runnable() {
            @Override
            public void run() {
                System.out.println("SlidingGradientHidActivity.run 第一次进来其状态显示");
                mRecyclerHeaderHeight =  mRecyclerViewHeader.getHeight();
                mTextViewHeight = mTextView.getHeight();
                Log.i(TAG, "onCreate: mTextViewHeight="+mTextViewHeight+ "mRecyclerHeaderHeight="+mRecyclerHeaderHeight);
                mTextView.setVisibility(View.VISIBLE);
                mTextView.setAlpha(0);
            }
        });










    }

    private List<String> getData() {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            stringList.add("i+ " + i);
        }
        return stringList;
    }

    /**
     * 计算RecyclerView滑动的距离
     * @param hasHead 是否有头部
     * @param headerHeight RecyclerView的头部高度
     * @return 滑动的距离
     */
    private int getScollYHeight(boolean hasHead, int headerHeight) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        //获取到第一个可见的position,其添加的头部不算其position当中
        int position = layoutManager.findFirstVisibleItemPosition();
        //通过position获取其管理器中的视图
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        //获取自身的高度
        int itemHeight = firstVisiableChildView.getHeight();
        //有头部
        if(hasHead) {
            return headerHeight + itemHeight*position - firstVisiableChildView.getTop();
        }else {
            return itemHeight*position - firstVisiableChildView.getTop();
        }
    }
}
