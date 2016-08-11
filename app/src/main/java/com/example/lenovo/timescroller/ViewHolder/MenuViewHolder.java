package com.example.lenovo.timescroller.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.timescroller.Model.MenuBean;
import com.example.lenovo.timescroller.R;

/**
 * Created by kevin.tian on 2016/8/9.
 */
public class MenuViewHolder extends RecyclerView.ViewHolder {
    TextView menuText;
    ImageView imageView;
    Context mContext;

    public MenuViewHolder(Context context,View itemView) {
        super(itemView);
        this.mContext = context;
        menuText = (TextView) itemView.findViewById(R.id.menu_item_text);
        imageView = (ImageView) itemView.findViewById(R.id.menu_item_image);
    }

/**
* @Description: ${当设置itemView即CardView时，点击无效果。待深入研究}
* @param ${tags}
* @return ${return_type}
* @throws
*/
    public void setData(final MenuBean bean) {
        menuText.setText(bean.getTitle());
        Glide.with(mContext).load(bean.getIconUrl()).into(imageView);
    }

}
