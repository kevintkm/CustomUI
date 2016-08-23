package com.example.lenovo.timescroller.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.ImageLoaderUtil;

import java.io.File;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kevin.tian on 2016/8/16.
 */
public class ImageActivity extends BaseActivity {
    @InjectView(R.id.activity_image_im)
    ImageView image;
    public static final String PICTURE = "picture";
    public static final String TITLE = "title";
    String title;
    Bitmap bitmap;

    public static Intent startImageActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(BaseActivity.OBJECT_EXTRA, url);
        intent.putExtra(TITLE, title);
        return intent;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_image_layout;
    }

    @Override
    public void initData() {
        extra = getIntent().getStringExtra(BaseActivity.OBJECT_EXTRA);
        title = getIntent().getStringExtra(TITLE);
        ViewCompat.setTransitionName(image, PICTURE);
        //ImageLoaderUtil.loadImage(this,extra,image);
        Glide.with(this).load(extra).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                bitmap = resource;
                image.setImageBitmap(resource);
            }
        });
    }

    @OnClick(R.id.activity_image_im)
    void onSave(View v) {
        File file = new File(Environment.getExternalStorageDirectory(), "Keming");
        if (!file.exists()) {
           
        }
    }

    @Override
    public void initUI() {
        setToolBarTitle(getIntent().getStringExtra(TITLE));
    }
}
