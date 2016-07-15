package com.example.lenovo.timescroller.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.example.lenovo.timescroller.R;


/**
 * Created by chris on 16/4/5.
 */
public class SingleLineRecyclerViewHolder extends BaseRecyclerViewHolder {

    public LinearLayout layout = null;

    public SingleLineRecyclerViewHolder(View itemView) {
        this(itemView, null);
    }

    public SingleLineRecyclerViewHolder(View itemView, Context context) {
        super(itemView, context);
        layout = (LinearLayout) itemView.findViewById(R.id.layout);
    }

    public SingleLineRecyclerViewHolder setWidth(int width) {
        this.width = width;
        return this;
    }

    public SingleLineRecyclerViewHolder setAnchorView(View anchorView){
        return this;
    }

    @Override
    protected void onItemClick(int index, Object object) {
        super.onItemClick(index, object);
    }
}
