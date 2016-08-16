package com.example.lenovo.timescroller.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.timescroller.Model.MeiZhi;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.View.RatioImageView;

/**
 * Created by kevin.tian on 2016/8/15.
 */
public class GankViewHolder extends BaseRecyclerViewHolder{

    RatioImageView imageView;
    TextView textView;

    public GankViewHolder(View itemView , Context context) {
        super(itemView,context);
        imageView = (RatioImageView) itemView.findViewById(R.id.project_item_im);
        textView = (TextView) itemView.findViewById(R.id.project_item_tv);
    }

    public void setData(Object object) {
        imageView.setOriginalSize(50,50);
        final MeiZhi.ResultsBean bean = (MeiZhi.ResultsBean) object;
        Glide.with(context).load(bean.getUrl()).into(imageView);
        textView.setText(bean.getDesc());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(0,bean);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(1,bean);
            }
        });
    }
}
