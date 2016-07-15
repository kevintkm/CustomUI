package com.example.lenovo.timescroller.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by kevin.tian on 2016/7/15.
 */
public class DecorationViewHolder extends BaseRecyclerViewHolder{

    TextView textView;
    Context mContext;

    public DecorationViewHolder(View itemView) {
        super(itemView);
    }

    public DecorationViewHolder(View itemView, Context context) {
        super(itemView, context);
        this.mContext = context;
        textView = (TextView) itemView.findViewById(android.R.id.text1);
    }

    public void setText(final String text){
        textView.setText(text);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,text,Toast.LENGTH_LONG).show();
            }
        });
    }



}
