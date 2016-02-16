package com.gaoyy.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by gaoyy on 2016/2/4/0004.
 */
public class ReViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private LayoutInflater inflater;
    private Context context;
    private LinkedList<String> data;
    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //顶部FootView

    public ReViewAdapter(Context context, LinkedList<String> data)
    {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof MyViewHolder)
        {
            ((MyViewHolder) holder).tv.setText(data.get(position));
            ((MyViewHolder) holder).tv.setTag(position);
        } else
        {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            footViewHolder.loadNextPageText.setText("数据加载中...");
        }
    }

    public void addMoreItem(LinkedList<String> newDatas)
    {
        for(int i=0;i<newDatas.size();i++)
        {
            data.addLast(newDatas.get(i));
        }
        notifyItemRangeInserted(getItemCount()-1,newDatas.size());
        notifyItemRangeChanged(getItemCount()-1, getItemCount()-newDatas.size());
    }

    @Override
    public int getItemCount()
    {
        return data.size() + 1;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position + 1 == getItemCount())
        {
            return TYPE_FOOTER;
        } else
        {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == TYPE_ITEM)
        {
            View rootView = inflater.inflate(R.layout.item, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(rootView);
            return myViewHolder;
        } else if (viewType == TYPE_FOOTER)
        {
            View rootView = inflater.inflate(R.layout.footer, parent, false);
            FootViewHolder footViewHolder = new FootViewHolder(rootView);
            return footViewHolder;
        }
        return null;
    }

    public void addItem(LinkedList<String> newDatas)
    {
        for(int i=0;i<newDatas.size();i++)
        {
            data.addFirst(newDatas.get(i));
        }
        notifyItemRangeInserted(0,newDatas.size());
        notifyItemRangeChanged(0 + newDatas.size(), getItemCount()-newDatas.size());
    }

    public void removeItem(int position)
    {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tv;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }

    public static class FootViewHolder extends RecyclerView.ViewHolder
    {
        public TextView loadNextPageText;

        public FootViewHolder(View itemView)
        {
            super(itemView);
            loadNextPageText = (TextView) itemView.findViewById(R.id.load_next_page_text);
        }
    }
}





