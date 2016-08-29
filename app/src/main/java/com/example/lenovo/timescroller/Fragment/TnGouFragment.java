package com.example.lenovo.timescroller.Fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.lenovo.timescroller.Adapter.GankAdapter;
import com.example.lenovo.timescroller.Adapter.TnGouAdapter;
import com.example.lenovo.timescroller.Model.TnBase;
import com.example.lenovo.timescroller.Model.TnGouPicListBean;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.BaseUrl;
import com.example.lenovo.timescroller.Util.HttpUtil;
import com.example.lenovo.timescroller.Util.Util;
import com.example.lenovo.timescroller.View.ExRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;

/**
 * Created by Kevin_Tian on 16/8/27.
 */
public class TnGouFragment extends BaseFragment implements ExRecyclerView.OnRefreshListener{

    public final static String EXTRA_ID = "id";
    private int id;
    @InjectView(R.id.tn_recyclerview)
    ExRecyclerView recyclerView;
    TnGouAdapter adapter ;
    List<TnGouPicListBean.TngouBean> lists;

    private int pag;
    private int rows = 10;

    public static TnGouFragment getInstance(int id){
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_ID,id);
        TnGouFragment fragment = new TnGouFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_tnlist_layout;
    }

    @Override
    public void initUI() {

        adapter = new TnGouAdapter(getActivity());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHeaderView(R.layout.uicomponent_header_view_indiana);
        recyclerView.setFooterView(R.layout.uicomponent_footer_view_indiana);
        recyclerView.setOnRefreshListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnExScrollListener(new ExRecyclerView.OnExScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

               /* 图片过多过大，取消滑动不加载限制
               if (newState==RecyclerView.SCROLL_STATE_IDLE){
                    Glide.with(getmContext()).resumeRequests();
                }else {
                    Glide.with(getmContext()).pauseRequests();
                }*/
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });


    }

    @Override
    public void initData() {
lists = new ArrayList<>();
id=getArguments().getInt(EXTRA_ID);
        onHeaderRefresh();
    }

    private void getPicList(String id, String page , final String row) {
        HashMap<String,String> map = new HashMap<>();
        map.put(EXTRA_ID,id);
        map.put("page",page);
        map.put("rows",row);
        HttpUtil.getInstance().postAsyncForm(BaseUrl.TNGOU_PICLIST, map, new HttpUtil.HttpCallBack() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onSuccess(String result) {
TnGouPicListBean bean = (TnGouPicListBean) Util.jsonToObject(result,TnGouPicListBean.class);
                if (bean.getTngou().size()<rows){
                    recyclerView.onLoadNoMoreComplete();
                }else {
                    recyclerView.onRefreshComplete();
                }
                if (pag == 1) {
                    lists.clear();
                }
                lists.addAll(bean.getTngou());
                adapter.setLists(lists);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {
recyclerView.onRefreshComplete();
            }
        });
    }

    @Override
    public void onHeaderRefresh() {
        recyclerView.setOnTopRefresh();
        pag = 1;
        getPicList(String.valueOf(id),String.valueOf(pag),String.valueOf(rows));
    }

    @Override
    public void onFooterRefresh() {
        pag++;
        getPicList(String.valueOf(id),String.valueOf(pag),String.valueOf(rows));
    }
}
