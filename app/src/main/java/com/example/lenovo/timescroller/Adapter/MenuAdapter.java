package com.example.lenovo.timescroller.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.timescroller.Activity.MainActivity;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.View.BaseRecyclerViewAdapter;
import com.example.lenovo.timescroller.ViewHolder.MenuViewHolder;

/**
 * Created by kevin.tian on 2016/8/9.
 */
public class MenuAdapter extends BaseRecyclerViewAdapter<String> {

    Context mContext;

    public MenuAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public boolean isPinnedItem(int viewType) {
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_menu_item, null);
        MenuViewHolder holder = new MenuViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MenuViewHolder menuViewHolder = (MenuViewHolder) holder;
        menuViewHolder.setListener((MainActivity)mContext);
        menuViewHolder.setData(lists.get(position));
    }
}
