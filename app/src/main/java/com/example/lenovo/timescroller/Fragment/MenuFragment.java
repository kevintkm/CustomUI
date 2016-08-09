package com.example.lenovo.timescroller.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.timescroller.Adapter.MenuAdapter;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.View.ExRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin.tian on 2016/8/9.
 */
public class MenuFragment extends Fragment{

    ExRecyclerView mRecyclerView;
    List<String> mDatas;
    View menuView;
    MenuAdapter menuAdapter;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         menuView = inflater.inflate(R.layout.fragment_menu_layout,null);
        init();
        return menuView;
    }

    private void init() {
        mDatas = new ArrayList<>();
        mDatas.add("首页");
        mDatas.add("新闻");
        mDatas.add("代码家");
        mDatas.add("Gank");
        mRecyclerView = (ExRecyclerView) menuView.findViewById(R.id.menu_recyclerview);
        menuAdapter = new MenuAdapter(getActivity());
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setHeaderView(R.layout.uicomponent_header_view_indiana);
        mRecyclerView.setFooterView(R.layout.uicomponent_footer_view_indiana);
        mRecyclerView.setAdapter(menuAdapter);
        menuAdapter.setLists(mDatas);
        menuAdapter.notifyDataSetChanged();
    }
}
