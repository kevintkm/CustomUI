package com.example.lenovo.timescroller.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.timescroller.Adapter.StickAdapter;
import com.example.lenovo.timescroller.Adapter.TnGouPagerAdapter;
import com.example.lenovo.timescroller.Model.TnGouClassify;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.BaseUrl;
import com.example.lenovo.timescroller.Util.HttpUtil;
import com.example.lenovo.timescroller.ViewHolder.HeaderItemDecoration;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin.tian on 2016/8/9.
 */
public class CommonWidgetFragment2 extends BaseFragment {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    StickAdapter adapter;
    List<String> names = new ArrayList<>();

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_widget_layout2;
    }

    @Override
    public void initUI() {
        for (int i = 0; i < 1945; i++) {
            names.add("tiankeming"+i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new StickAdapter(names,getActivity());
        recyclerView.addItemDecoration(new HeaderItemDecoration(recyclerView,adapter));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
