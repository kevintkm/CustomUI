package com.example.lenovo.timescroller.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.timescroller.R;

/**
 * Created by kevin.tian on 2016/8/9.
 */
public class MenuViewHolder extends RecyclerView.ViewHolder {
    TextView menuText;
    MenuItemClickListener listener;

    public void setListener(MenuItemClickListener listener) {
        this.listener = listener;
    }

    public interface MenuItemClickListener {
        void menuItemClick(Object object);
    }

    public MenuViewHolder(View itemView) {
        super(itemView);
        menuText = (TextView) itemView.findViewById(R.id.menu_item_text);
    }

/**
* @Description: ${当设置itemView即CardView时，点击无效果。待深入研究}
* @param ${tags}
* @return ${return_type}
* @throws
*/
    public void setData(final String text) {
        menuText.setText(text);
        menuText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("========","ViewHolderClick");
                listener.menuItemClick(text);
            }
        });
    }

}
