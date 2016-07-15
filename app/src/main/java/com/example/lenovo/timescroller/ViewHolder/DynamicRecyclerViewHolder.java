package com.example.lenovo.timescroller.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.example.lenovo.timescroller.R;


/**
 * Created by chris on 16/4/5.
 */
public class DynamicRecyclerViewHolder extends BaseRecyclerViewHolder {

    public LinearLayout layout = null;
    protected int width = 0;

    public DynamicRecyclerViewHolder(View itemView) {
        this(itemView, null);
    }

    public DynamicRecyclerViewHolder(View itemView, Context context) {
        super(itemView, context);
        width = context.getResources().getDisplayMetrics().widthPixels;
        layout = (LinearLayout) itemView.findViewById(R.id.layout);
    }

    public DynamicRecyclerViewHolder setWidth(int width) {
        this.width = width;
        return this;
    }

    public DynamicRecyclerViewHolder setAnchorView(View anchorView){
        return this;
    }

    @Override
    protected void onItemClick(int index, Object object) {
        super.onItemClick(index, object);
    }

    public boolean isFullSpan(){
        return false;
    }
}
