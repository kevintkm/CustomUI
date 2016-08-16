package com.example.lenovo.timescroller.Activity;

import android.widget.ImageView;

import com.example.lenovo.timescroller.R;

import butterknife.InjectView;

/**
 * Created by kevin.tian on 2016/8/16.
 */
public class ImageActivity extends BaseActivity {
    @InjectView(R.id.activity_image_im)
    ImageView image;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_image_layout;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initUI() {
        super.initUI();
    }
}
