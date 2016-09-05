package com.example.lenovo.timescroller.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.timescroller.Activity.MainActivity;
import com.example.lenovo.timescroller.Model.MenuBean;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.ViewHolder.MenuViewHolder;

/**
 * Created by kevin.tian on 2016/8/9.
 */
public class MenuAdapter extends BaseRecyclerViewAdapter<MenuBean> {

    Context mContext;
    MenuItemClickListener listener;

    public MenuAdapter(Context context) {
        this.mContext = context;
    }

    public void setListener(MenuItemClickListener listener) {
        this.listener = listener;
    }

    public interface MenuItemClickListener {
        void menuItemClick(Object object);
    }

    @Override
    public boolean isPinnedItem(int viewType) {
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_menu_item, parent,false);
        MenuViewHolder holder = new MenuViewHolder(mContext,view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MenuViewHolder menuViewHolder = (MenuViewHolder) holder;
        menuViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.menuItemClick(lists.get(position));
            }
        });
        menuViewHolder.setData(lists.get(position));
    }
}
