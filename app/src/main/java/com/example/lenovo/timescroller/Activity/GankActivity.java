package com.example.lenovo.timescroller.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;


import com.example.lenovo.timescroller.Adapter.GankPageAdapter;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.DateUtil;

import java.util.Date;

import butterknife.InjectView;

/**
 * Created by kevin.tian on 2016/8/18.
 */
public class GankActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @InjectView(R.id.tabLayout)
    TabLayout activityGankTb;
    @InjectView(R.id.activity_gank_vp)
    ViewPager activityGankVp;
    GankPageAdapter adapter;
    public static final String DATE = "date";
    private Date date;

    public static void startGankActivity(Context context, Date date) {
        Intent intent = new Intent(context, GankActivity.class);
        intent.putExtra(DATE, date);
        context.startActivity(intent);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_gank_layout;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initUI() {
        date = (Date) getIntent().getSerializableExtra(DATE);
        setTitle(DateUtil.getYear(date)+"/"+(DateUtil.getMonth(date)+1)+"/"+DateUtil.getDay(date));
        initViewPage();
        initTabLayout();
    }

    private void initTabLayout() {
        for (int i = 0; i < adapter.getCount(); i++) {
            activityGankTb.addTab(activityGankTb.newTab());
        }
        activityGankTb.setupWithViewPager(activityGankVp);
    }

    private void initViewPage() {
        adapter = new GankPageAdapter(getSupportFragmentManager(),date);
        activityGankVp.setAdapter(adapter);
        activityGankVp.setOffscreenPageLimit(1);
        activityGankVp.addOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setTitle(DateUtil.getYear(date)+"/"+(DateUtil.getMonth(date)+1)+"/"+(DateUtil.getDay(date)-position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
