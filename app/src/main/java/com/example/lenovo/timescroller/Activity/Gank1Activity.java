package com.example.lenovo.timescroller.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.example.lenovo.timescroller.Fragment.GankFragment;
import com.example.lenovo.timescroller.R;

import butterknife.InjectView;

/**
 * Created by kevin.tian on 2016/8/18.
 */
public class Gank1Activity extends BaseActivity implements ViewPager.OnPageChangeListener{

    FragmentManager manager;

    @Override
    protected int setLayoutId() {
        return R.layout.text_scrollview;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initUI() {
        manager = getSupportFragmentManager();
        GankFragment fragment = new GankFragment();
        manager.beginTransaction().replace(R.id.gank_content,fragment).commit();
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
