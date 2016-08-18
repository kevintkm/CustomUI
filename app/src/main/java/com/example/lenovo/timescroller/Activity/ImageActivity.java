package com.example.lenovo.timescroller.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.ImageLoaderUtil;

import butterknife.InjectView;

/**
 * Created by kevin.tian on 2016/8/16.
 */
public class ImageActivity extends BaseActivity {
    @InjectView(R.id.activity_image_im)
    ImageView image;
    public static final String PICTURE="picture";
    public static final String TITLE= "title";

    public static Intent startImageActivity(Context context,String url,String title){
        Intent intent =new Intent(context,ImageActivity.class);
        intent.putExtra(BaseActivity.OBJECT_EXTRA,url);
        intent.putExtra(TITLE,title);
        return intent;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_image_layout;
    }

    @Override
    public void initData() {
        extra = getIntent().getStringExtra(BaseActivity.OBJECT_EXTRA);
        ViewCompat.setTransitionName(image,PICTURE);
        ImageLoaderUtil.loadImage(this,extra,image);
    }

    @Override
    public void initUI() {
        setToolBarTitle(getIntent().getStringExtra(TITLE));
    }
}
