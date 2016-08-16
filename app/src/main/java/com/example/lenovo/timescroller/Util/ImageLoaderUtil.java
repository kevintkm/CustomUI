package com.example.lenovo.timescroller.Util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lenovo.timescroller.R;

/**
 * Created by kevin.tian on 2016/8/16.
 */
public class ImageLoaderUtil {

    public static void loadImage(Context context, String url , ImageView imageView){
        Glide.with(context).load(url).error(R.drawable.kevin).placeholder(R.drawable.kevin).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

}
