package com.example.lenovo.timescroller.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.lenovo.timescroller.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.ExecutionException;

/**
 * Created by kevin.tian on 2016/8/16.
 */
public class ImageLoaderUtil {

    public static void loadImage(Context context, String url , ImageView imageView){
        Glide.with(context).load(url).error(R.drawable.kevin).placeholder(R.drawable.kevin).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadImage(Context context, String url  , SimpleTarget<Bitmap> target){
        Glide.with(context).load(url).asBitmap().error(R.drawable.kevin).placeholder(R.drawable.kevin).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(target);
    }
}
