package com.example.lenovo.timescroller.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by chris on 16/4/20.
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    public interface OnItemClickListener {
        public void onItemClick(int index, Object object);
    }

    protected Context context = null;
    protected int width = 0;
    protected float density = 0f;
    private OnItemClickListener listener = null;
    public String pageName = null;

    public BaseRecyclerViewHolder(View itemView) {
        this(itemView, null);
    }

    public BaseRecyclerViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        width = context.getResources().getDisplayMetrics().widthPixels;
        density = context.getResources().getDisplayMetrics().density;
    }

    protected int getViewId(String name) {
        return context.getResources().getIdentifier(name, "id", context.getPackageName());
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        listener = l;
    }

    protected void onItemClick(int index, Object object) {
        if (listener != null) {
            listener.onItemClick(index, object);
        }

    }
}
