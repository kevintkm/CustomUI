package com.example.lenovo.timescroller.Fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.lenovo.timescroller.Adapter.GankAdapter;
import com.example.lenovo.timescroller.Model.MeiZhi;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.HttpUtil;
import com.example.lenovo.timescroller.View.ExRecyclerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by kevin.tian on 2016/8/9.
 */
public class ProjectLearningFragment extends BaseFragment implements ExRecyclerView.OnRefreshListener {
    @InjectView(R.id.fragment_recyclerview)
    ExRecyclerView recyclerView;
    GankAdapter adapter;
    List<MeiZhi.ResultsBean> shipins;
    int page = 1;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_project_layout;
    }

    @Override
    public void initUI() {
        super.initUI();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHeaderView(R.layout.uicomponent_header_view_indiana);
        recyclerView.setFooterView(R.layout.uicomponent_footer_view_indiana);
        recyclerView.setOnRefreshListener(this);
        adapter = new GankAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setOnExScrollListener(new ExRecyclerView.OnExScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState==RecyclerView.SCROLL_STATE_IDLE){
                    Glide.with(getmContext()).resumeRequests();
                }else {
                    Glide.with(getmContext()).pauseRequests();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        shipins = new ArrayList<>();
        onHeaderRefresh();
    }

    private void loadData(final int page) {
        String url = "http://gank.io/api/data/福利/10/" + page;
        final String shipinUrl = "http://gank.io/api/data/休息视频/10/" + page;
        HttpUtil.getInstance().getAsync(url, new HttpUtil.HttpCallBack() {
            @Override
            public void onLoading() {
                Log.d("=======", "onLoading");
            }

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final MeiZhi fuli = gson.fromJson(result, MeiZhi.class);
                HttpUtil.getInstance().getAsync(shipinUrl, new HttpUtil.HttpCallBack() {
                    @Override
                    public void onLoading() {
                        Log.d("=======", "onLoading");
                    }

                    @Override
                    public void onSuccess(String result) {
                        Gson gson = new Gson();
                        MeiZhi shipin = gson.fromJson(result, MeiZhi.class);
                        if (shipin.getResults().size()<10){
                            recyclerView.onLoadNoMoreComplete();
                        }else {
                            recyclerView.onRefreshComplete();
                        }
                        if (page == 1) {
                            shipins.clear();
                        }
                        shipins.addAll(handleData(fuli.getResults(), shipin.getResults()));
                        adapter.setLists(shipins);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String error) {
                        Log.d("=======", error);
                    }
                });
            }

            @Override
            public void onError(String error) {
                Log.d("=======", error);
            }
        });
    }

    private List<MeiZhi.ResultsBean> handleData(List<MeiZhi.ResultsBean> shipins, List<MeiZhi.ResultsBean> fulis) {
        int count = shipins.size() > fulis.size() ? fulis.size() : shipins.size();
        for (int i = 0; i < count; i++) {
            fulis.get(i).setUrl(shipins.get(i).getUrl());
            StringBuffer sb = new StringBuffer(shipins.get(i).getDesc());
            sb.append(" ");
            sb.append(fulis.get(i).getDesc());
            fulis.get(i).setDesc(sb.toString());
        }
        return fulis;
    }

    @Override
    public void onHeaderRefresh() {
        recyclerView.setOnTopRefresh();
        page = 1;
        loadData(page);
    }

    @Override
    public void onFooterRefresh() {
        page++;
        loadData(page);
    }
}


