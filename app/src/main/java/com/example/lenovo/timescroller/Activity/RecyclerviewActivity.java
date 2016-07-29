package com.example.lenovo.timescroller.Activity;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.timescroller.Adapter.ItemDecorationAdapter;
import com.example.lenovo.timescroller.View.AliArc;
import com.example.lenovo.timescroller.View.DividerGridViewItemDecoration;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.View.ExRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RecyclerviewActivity extends Activity {

    private ExRecyclerView myExRecyclerView;
    private ItemDecorationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
        myExRecyclerView = (ExRecyclerView) findViewById(R.id.recyclerView);

        mAdapter = new ItemDecorationAdapter(this);
        myExRecyclerView.addItemDecoration(new DividerGridViewItemDecoration(this));
        myExRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
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
        for (int i = 0; i < 26; i++) {
            strings.add("kevin" + i);
        }
        try {
            Thread.sleep(3000);
            mAdapter.setLists(strings);
            mAdapter.notifyDataSetChanged();
            myExRecyclerView.onRefreshComplete();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void click(View v) {
        int length = mAdapter.getLists().size();
        mAdapter.getLists().remove(length-3);
        mAdapter.notifyItemRemoved(length - 3);

    }

}
