package com.example.lenovo.timescroller.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.timescroller.View.BaseRecyclerViewAdapter;
import com.example.lenovo.timescroller.ViewHolder.DecorationViewHolder;

/**
 * Created by kevin.tian on 2016/7/15.
 */
public class ItemDecorationAdapter extends BaseRecyclerViewAdapter<String>{

    private Context mContext;
    private LayoutInflater mInflater;

    public ItemDecorationAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public boolean isPinnedItem(int viewType) {
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(android.R.layout.activity_list_item,null);
        return new DecorationViewHolder(view,mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DecorationViewHolder)holder).setText(lists.get(position));
    }

    @Override
    public int getItemCount() {
        return lists==null?0:lists.size();
    }
}
