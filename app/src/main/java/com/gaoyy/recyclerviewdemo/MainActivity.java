package com.gaoyy.recyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private LinkedList<String> list = null;
    private ReViewAdapter reViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //de
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
        reViewAdapter = new ReViewAdapter(this, list);
        recyclerView.setAdapter(reViewAdapter);

        //设置布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //设置分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    public void initViews()
    {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    public void initData()
    {
        list = new LinkedList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            list.add("" + (char) i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_listview:
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                break;
            case R.id.action_gridview:
                recyclerView.setLayoutManager(new GridLayoutManager(this,4));
                break;
            case R.id.action_hor_gridview:
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(6,StaggeredGridLayoutManager.HORIZONTAL));
                break;
            case R.id.action_staggered:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
