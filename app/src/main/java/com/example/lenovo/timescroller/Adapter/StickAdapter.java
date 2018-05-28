package com.example.lenovo.timescroller.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.lenovo.timescroller.ViewHolder.HeaderItemDecoration;

import java.util.List;


public class StickAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements HeaderItemDecoration.StickyHeaderInterface {
    List<String> names;
    Context mContext;

    public StickAdapter(List<String> names, Context context) {
        this.names = names;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent,false);
        if (viewType==100) {
            view.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_orange_light));
        }else {
            view.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
        }
        return new StickHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StickHolder holder1 = (StickHolder) holder;
        holder1.setText(names.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return position%7==0?100:200;
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        return itemPosition/7;
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        return android.R.layout.simple_list_item_1;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {
        TextView tv = (TextView) header.findViewById(android.R.id.text1);
        tv.setText(names.get(headerPosition*7));
        header.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_orange_light));
        tv.setTextSize(16);
        tv.setTextColor(mContext.getResources().getColor(android.R.color.darker_gray));
    }

    @Override
    public boolean isHeader(int itemPosition) {
        return itemPosition % 7 == 0;
    }

    class StickHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public StickHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        void setText(String name) {
            textView.setText(name);
            textView.setTextSize(16);
            textView.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
        }
    }

}
