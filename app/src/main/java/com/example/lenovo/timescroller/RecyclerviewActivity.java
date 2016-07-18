package com.example.lenovo.timescroller;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.lenovo.timescroller.Adapter.ItemDecorationAdapter;
import com.example.lenovo.timescroller.View.ExRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerviewActivity extends Activity {

    private ExRecyclerView myExRecyclerView;
    private ItemDecorationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);

        myExRecyclerView = (ExRecyclerView) findViewById(R.id.myExRecyclerView);
        myExRecyclerView.addItemDecoration(new DividerLinearItemDecoration(this, R.color.red, 16, StaggeredGridLayoutManager.VERTICAL));
        myExRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new ItemDecorationAdapter(this);
        myExRecyclerView.setAdapter(mAdapter);
        myExRecyclerView.setHeaderView(R.layout.uicomponent_header_view_indiana);
        myExRecyclerView.setFooterView(R.layout.uicomponent_footer_view_indiana);
        myExRecyclerView.setOnRefreshListener(new ExRecyclerView.OnRefreshListener() {
            @Override
            public void onHeaderRefresh() {
                try {
                    Thread.sleep(300);
                    myExRecyclerView.onRefreshComplete();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFooterRefresh() {
                try {
                    Thread.sleep(300);
                    myExRecyclerView.onLoadNoMoreComplete();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            strings.add("kevin" + i);
        }
        mAdapter.setLists(strings);
        mAdapter.notifyDataSetChanged();

    }

}
