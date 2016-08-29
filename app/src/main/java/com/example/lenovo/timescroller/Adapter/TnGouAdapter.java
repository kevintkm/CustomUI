package com.example.lenovo.timescroller.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.timescroller.Model.TnGouClassify;
import com.example.lenovo.timescroller.Model.TnGouPicListBean;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.ViewHolder.TnGouViewHolder;

/**
 * Created by Kevin_Tian on 16/8/27.
 */
public class TnGouAdapter extends BaseRecyclerViewAdapter<TnGouPicListBean.TngouBean>{
    Context context;

    public TnGouAdapter(Context context) {
        this.context = context;
    }

    @Override
    public boolean isPinnedItem(int viewType) {
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_project_item,parent,false);
        return new TnGouViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
TnGouViewHolder viewHolder = (TnGouViewHolder) holder;
        viewHolder.setData(lists.get(position));
    }
}
