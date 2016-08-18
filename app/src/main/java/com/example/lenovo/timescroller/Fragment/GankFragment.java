package com.example.lenovo.timescroller.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewStub;

import com.example.lenovo.timescroller.Adapter.GankDetailAdapter;
import com.example.lenovo.timescroller.Model.DayBean;
import com.example.lenovo.timescroller.Model.MeiZhi;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.HttpUtil;
import com.example.lenovo.timescroller.View.ExRecyclerView;
import com.example.lenovo.timescroller.View.VideoImageView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by kevin.tian on 2016/8/18.
 */
public class GankFragment extends BaseFragment {
    @InjectView(R.id.rv_gank)
    ExRecyclerView rvGank;
    @InjectView(R.id.iv_video)
    VideoImageView ivVideo;
    @InjectView(R.id.stub_empty_view)
    ViewStub stubEmptyView;
    @InjectView(R.id.stub_video_view)
    ViewStub stubVideoView;
    GankDetailAdapter adapter;
    String date = "2016/08/18";
    List<MeiZhi.ResultsBean> beanList;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_gank_layout;
    }

    @Override
    public void initUI() {
        super.initUI();
        setToolBarTitle(date);
        initRecycleView();
    }

    private void initRecycleView() {
        adapter = new GankDetailAdapter(getmContext());
        rvGank.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        rvGank.setAdapter(adapter);
    }

    @Override
    public void initData() {
        super.initData();
        beanList = new ArrayList<>();
        String url = "http://gank.io/api/day/" + date;
        HttpUtil.getInstance().getAsync(url, new HttpUtil.HttpCallBack() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onSuccess(String result) {
                DayBean bean = new Gson().fromJson(result, DayBean.class);
                if (bean != null && bean.getResults() != null)
                    adapter.setLists(handle(bean));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private List<MeiZhi.ResultsBean> handle(DayBean bean) {
        if (bean.getResults().getAndroid() != null) {
            beanList.addAll(bean.getResults().getAndroid());
        }
        if (bean.getResults().getiOS() != null) {
            beanList.addAll(bean.getResults().getiOS());
        }
        if (bean.getResults().get休息视频() != null) {
            beanList.addAll(bean.getResults().get休息视频());
        }
        if (bean.getResults().get拓展资源() != null) {
            beanList.addAll(bean.getResults().get拓展资源());
        }
        if (bean.getResults().get瞎推荐() != null) {
            beanList.addAll(bean.getResults().get瞎推荐());
        }
        if (bean.getResults().get福利() != null) {
            beanList.addAll(bean.getResults().get福利());
        }
        return beanList;
    }

}
