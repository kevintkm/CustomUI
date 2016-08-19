package com.example.lenovo.timescroller.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lenovo.timescroller.Fragment.GankFragment;
import com.example.lenovo.timescroller.Util.DateUtil;

import java.util.Date;

/**
 * Created by kevin.tian on 2016/8/19.
 */
public class GankPageAdapter extends FragmentPagerAdapter{

    Date mDate;

    public GankPageAdapter(FragmentManager fm , Date date) {
        super(fm);
        this.mDate = date;
    }

    @Override
    public Fragment getItem(int position) {
        return GankFragment.getInstance(DateUtil.getYear(mDate),DateUtil.getMonth(mDate),DateUtil.getDay(mDate)-position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
