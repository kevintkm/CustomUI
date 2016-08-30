package com.example.lenovo.timescroller.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.timescroller.Adapter.TnGouPagerAdapter;
import com.example.lenovo.timescroller.Model.TnGouClassify;
import com.example.lenovo.timescroller.Model.TnGouPicListBean;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.BaseUrl;
import com.example.lenovo.timescroller.Util.HttpUtil;
import com.example.lenovo.timescroller.View.ExRecyclerView;
import com.google.gson.Gson;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin.tian on 2016/8/9.
 */
public class CommonWidgetFragment extends BaseFragment {
    @InjectView(R.id.widget_tablayout)
    TabLayout widgetTablayout;
    @InjectView(R.id.widget_viewpager)
    ViewPager widgetViewPager;
    List<TnGouClassify.TngouBean> beanList;
    TnGouPagerAdapter adapter;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_widget_layout;
    }

    @Override
    public void initUI() {
        adapter = new TnGouPagerAdapter(getActivity().getSupportFragmentManager());
        widgetViewPager.setAdapter(adapter);
        widgetTablayout.setupWithViewPager(widgetViewPager);
    }

    @Override
    public void initData() {
        getNavigation();

    }

    private void getNavigation() {
        HttpUtil.getInstance().getAsync(BaseUrl.TNGOU_CLASSIFY, new HttpUtil.HttpCallBack() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                TnGouClassify classify = gson.fromJson(result,TnGouClassify.class);
                beanList = classify.getTngou();
                handleNavigation(beanList);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void handleNavigation(List<TnGouClassify.TngouBean> beanList) {
        TnGouClassify.TngouBean bean = new TnGouClassify.TngouBean();
        bean.setTitle(getString(R.string.lastest_picture));
        beanList.add(0,bean);
        adapter.setBeanList(beanList);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
