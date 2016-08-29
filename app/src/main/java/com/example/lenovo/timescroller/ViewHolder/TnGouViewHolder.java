package com.example.lenovo.timescroller.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.timescroller.Activity.ImageActivity;
import com.example.lenovo.timescroller.Activity.MainActivity;
import com.example.lenovo.timescroller.Model.TnGouPicListBean;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.BaseUrl;
import com.example.lenovo.timescroller.Util.ImageLoaderUtil;
import com.example.lenovo.timescroller.View.RatioImageView;

/**
 * Created by kevin.tian on 2016/8/15.
 */
public class TnGouViewHolder extends BaseRecyclerViewHolder{

    RatioImageView imageView;
    TextView textView;
    Context mContext;

    public TnGouViewHolder(View itemView , Context context) {
        super(itemView,context);
        this.mContext = context;
        imageView = (RatioImageView) itemView.findViewById(R.id.project_item_im);
        textView = (TextView) itemView.findViewById(R.id.project_item_tv);
    }

    public void setData(Object object) {
        imageView.setOriginalSize(50,50);
        final TnGouPicListBean.TngouBean bean = (TnGouPicListBean.TngouBean) object;
        ImageLoaderUtil.loadImage(context.getApplicationContext(), BaseUrl.TNGOU_PICFOR+bean.getImg(),imageView);
        textView.setText(bean.getTitle());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(0,bean);
            }
        });
    }

    @Override
    protected void onItemClick(int index, Object object) {
        super.onItemClick(index, object);
        TnGouPicListBean.TngouBean bean = (TnGouPicListBean.TngouBean) object;
        Intent intent = ImageActivity.startImageActivity(mContext,BaseUrl.TNGOU_PICFOR+bean.getImg(),bean.getTitle());
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation((MainActivity)mContext,imageView,ImageActivity.PICTURE);
        ActivityCompat.startActivity((MainActivity)mContext,intent,compat.toBundle());
    }
}
