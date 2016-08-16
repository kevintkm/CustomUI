package com.example.lenovo.timescroller.Fragment;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.example.lenovo.timescroller.Adapter.GankAdapter;
import com.example.lenovo.timescroller.Model.MeiZhi;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.HttpUtil;
import com.example.lenovo.timescroller.View.ExRecyclerView;
import com.google.gson.Gson;

import butterknife.InjectView;

/**
 * Created by kevin.tian on 2016/8/9.
 */
public class ProjectLearningFragment extends BaseFragment {
    @InjectView(R.id.fragment_recyclerview)
    ExRecyclerView recyclerView;
    GankAdapter adapter;
    MeiZhi fuli;
    MeiZhi shipin;
    int page=1;

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
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adapter = new GankAdapter(getActivity());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        super.initData();
        String url = "http://gank.io/api/data/福利/10/"+page;
        final String shipinUrl = "http://gank.io/api/data/休息视频/10/"+page;
        HttpUtil.getInstance().getAsync(url, new HttpUtil.HttpCallBack() {
            @Override
            public void onLoading() {
                Log.d("=======","onLoading");
            }

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                fuli = gson.fromJson(result,MeiZhi.class);
                HttpUtil.getInstance().getAsync(shipinUrl, new HttpUtil.HttpCallBack() {
                    @Override
                    public void onLoading() {
                        Log.d("=======","onLoading");
                    }

                    @Override
                    public void onSuccess(String result) {
                        Gson gson = new Gson();
                        shipin = gson.fromJson(result,MeiZhi.class);
                        if (fuli!=null&&fuli.getResults().size()>0){
                            for (int i=0;i<fuli.getResults().size();i++){
                                shipin.getResults().get(i).setUrl(fuli.getResults().get(i).getUrl());
                            }
                            adapter.setLists(shipin.getResults());
                            adapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onError(String error) {
                        Log.d("=======",error);
                    }
                });
            }

            @Override
            public void onError(String error) {
                Log.d("=======",error);
            }
        });


    }
}


