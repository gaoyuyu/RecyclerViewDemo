package com.gaoyy.recyclerviewdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.Toast;

import java.util.LinkedList;

/**
 * Created by gaoyy on 2016/2/15/0015.
 */
public class SwipeRefreshActivity extends AppCompatActivity
{
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView swRecyclerView;
    private LinkedList<String> data;
    private ReViewAdapter reViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int lastVisibleItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refresh_main);
        initDatas();
        assignViews();
        configViews();


    }

    private void initDatas()
    {
        data = new LinkedList<String>();
        for (int i = 0; i < 15; i++)
        {
            data.add(i, "Dcviod kop@" + i);
        }

    }
    private void assignViews()
    {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swRecyclerView = (RecyclerView) findViewById(R.id.sw_recyclerView);
    }

    private void configViews()
    {

        reViewAdapter = new ReViewAdapter(SwipeRefreshActivity.this, data);
        swRecyclerView.setAdapter(reViewAdapter);
        //设置布局
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        swRecyclerView.setLayoutManager(linearLayoutManager);
        swRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        LinkedList<String> newDatas = new LinkedList<String>();
                        for (int i = 0; i < 5; i++)
                        {
                            int index = i + 1;
                            newDatas.add("new item" + index+""+(int)(Math.random()*100));
                        }
                        reViewAdapter.addItem(newDatas);
                        swRecyclerView.scrollToPosition(0);
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(SwipeRefreshActivity.this, "更新了数据...", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });

        swRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == reViewAdapter.getItemCount())
                {
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            LinkedList<String> newDatas = new LinkedList<String>();
                            for (int i = 0; i < 5; i++)
                            {
                                int index = i + 1;
                                newDatas.add("more item" + index+""+(int)(Math.random()*100));
                            }
                            reViewAdapter.addMoreItem(newDatas);
                        }
                    }, 2000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });

    }
}
