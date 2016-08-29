package com.example.lenovo.timescroller.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.example.lenovo.timescroller.Fragment.TnGouFragment;
import com.example.lenovo.timescroller.Model.TnGouClassify;

import java.util.List;

/**
 * Created by Kevin_Tian on 16/8/27.
 */
public class TnGouPagerAdapter extends FragmentPagerAdapter {

    List<TnGouClassify.TngouBean> beanList;

    public TnGouPagerAdapter(FragmentManager fm){
        super(fm);
    }

    public TnGouPagerAdapter(FragmentManager fm , List<TnGouClassify.TngouBean> beenList) {
        super(fm);
        this.beanList = beenList;
    }

    public void setBeanList(List<TnGouClassify.TngouBean> beanList) {
        this.beanList = beanList;
    }

    @Override
    public int getCount() {
        return beanList==null?0:beanList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return TnGouFragment.getInstance(beanList.get(position).getId());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return beanList.get(position).getTitle();
    }

}
