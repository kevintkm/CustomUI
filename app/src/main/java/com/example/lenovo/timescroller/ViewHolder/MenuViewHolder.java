package com.example.lenovo.timescroller.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.timescroller.R;

/**
 * Created by kevin.tian on 2016/8/9.
 */
public class MenuViewHolder extends RecyclerView.ViewHolder {
    TextView menuText;

    public MenuViewHolder(View itemView) {
        super(itemView);
        menuText = (TextView) itemView.findViewById(R.id.menu_item_text);
    }

    public void setData(String text) {
        menuText.setText(text);
    }

}
