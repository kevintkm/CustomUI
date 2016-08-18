package com.example.lenovo.timescroller.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;


import com.example.lenovo.timescroller.R;

import butterknife.InjectView;

/**
 * Created by kevin.tian on 2016/8/18.
 */
public class GankActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    @InjectView(R.id.activity_gank_tb)
    TabLayout activityGankTb;
    @InjectView(R.id.activity_gank_vp)
    ViewPager activityGankVp;



    @Override
    protected int setLayoutId() {
        return R.layout.activity_gank_layout;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initUI() {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
