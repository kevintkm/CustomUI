package com.example.lenovo.timescroller.Fragment;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

import com.example.lenovo.timescroller.Adapter.GankDetailAdapter;
import com.example.lenovo.timescroller.Model.DayBean;
import com.example.lenovo.timescroller.Model.MeiZhi;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.HttpUtil;
import com.example.lenovo.timescroller.Util.ImageLoaderUtil;
import com.example.lenovo.timescroller.View.LoveVideoView;
import com.example.lenovo.timescroller.View.VideoImageView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kevin.tian on 2016/8/18.
 */
public class GankFragment extends BaseFragment {
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";
    @InjectView(R.id.rv_gank)
    RecyclerView rvGank;
    @InjectView(R.id.iv_video)
    VideoImageView ivVideo;
    @InjectView(R.id.stub_empty_view)
    ViewStub stubEmptyView;
    @InjectView(R.id.stub_video_view)
    ViewStub stubVideoView;
    GankDetailAdapter adapter;
    String date;
    List<MeiZhi.ResultsBean> beanList;
    @InjectView(R.id.iv_video)
    VideoImageView mVideoImageView;
    LoveVideoView mVideoView;
    String mVideoPreviewUrl;
    boolean mIsVideoViewInflated = false;
    int mYear, mMonth, mDay;

    public static GankFragment getInstance(int year, int month, int day) {
        Bundle bundle = new Bundle();
        bundle.putInt(YEAR, year);
        bundle.putInt(MONTH, month);
        bundle.putInt(DAY, day);
        GankFragment gankFragment = new GankFragment();
        gankFragment.setArguments(bundle);
        return gankFragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_gank_layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beanList = new ArrayList<>();
        adapter = new GankDetailAdapter(getContext());
        setRetainInstance(true);
        setHasOptionsMenu(true);
        parseDate();
        netWork();
    }

    private void parseDate() {
        Bundle bundle = getArguments();
        mYear = bundle.getInt(YEAR);
        mMonth = bundle.getInt(MONTH);
        mDay = bundle.getInt(DAY);
        date = mYear+"/"+mMonth+"/"+mDay;
    }

    @Override
    public void initUI() {
        super.initUI();
        initRecycleView();
        setVideoViewPosition(getResources().getConfiguration());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setVideoViewPosition(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mVideoPreviewUrl != null) {
            ImageLoaderUtil.loadImage(getmContext(), mVideoPreviewUrl, mVideoImageView);
        }
    }

    private void showEmptyView() {
        stubEmptyView.inflate();
    }

    private void initRecycleView() {
        rvGank.setLayoutManager(new LinearLayoutManager(getmContext()));
        rvGank.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeVideoView();
    }

    private void setVideoViewPosition(Configuration newConfig) {
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE: {
                if (mIsVideoViewInflated) {
                    stubVideoView.setVisibility(View.VISIBLE);
                } else {
                    mVideoView = (LoveVideoView) stubVideoView.inflate();
                    mIsVideoViewInflated = true;
                }
                if (beanList.size() > 0 && beanList.get(0).getType().equals("休息视频")) {
                    mVideoView.loadUrl(beanList.get(0).getUrl());
                }
                break;
            }
            case Configuration.ORIENTATION_PORTRAIT:
            case Configuration.ORIENTATION_UNDEFINED:
            default: {
                stubVideoView.setVisibility(View.GONE);
                break;
            }
        }
    }

    @Override
    public void initData() {
        super.initData();
    }

    private void netWork() {
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
                else showEmptyView();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @OnClick(R.id.header_appbar)
    void onPlayVideo() {
        resumeVideoView();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (beanList.size() > 0 && beanList.get(0).getType().equals("休息视频")) {
            Toast.makeText(getmContext(), "加载中...", Toast.LENGTH_SHORT).show();
        } else {
            closePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseVideoView();
        clearVideoView();
    }

    private void pauseVideoView() {
        if (mVideoView != null) {
            mVideoView.onPause();
            mVideoView.pauseTimers();
        }
    }

    void closePlayer() {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void resumeVideoView() {
        if (mVideoView != null) {
            mVideoView.resumeTimers();
            mVideoView.onResume();
        }
    }


    private void clearVideoView() {
        if (mVideoView != null) {
            mVideoView.clearHistory();
            mVideoView.clearCache(true);
            mVideoView.loadUrl("about:blank");
            mVideoView.pauseTimers();
        }
    }

    private List<MeiZhi.ResultsBean> handle(DayBean bean) {
        if (bean.getResults().getAndroid() != null) {
            beanList.addAll(bean.getResults().getAndroid());
        }
        if (bean.getResults().getiOS() != null) {
            beanList.addAll(bean.getResults().getiOS());
        }
        if (bean.getResults().get休息视频() != null) {
            beanList.addAll(0, bean.getResults().get休息视频());
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
