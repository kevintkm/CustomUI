package com.example.lenovo.timescroller.Activity;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.example.lenovo.timescroller.Adapter.ItemDecorationAdapter;
import com.example.lenovo.timescroller.View.AliArc;
import com.example.lenovo.timescroller.View.DividerGridViewItemDecoration;
import com.example.lenovo.timescroller.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RecyclerviewActivity extends Activity {

    private RecyclerView myExRecyclerView;
    private ItemDecorationAdapter mAdapter;
    private AliArc aliArc;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
        aliArc = (AliArc) findViewById(R.id.myAliArc);
        textView = (TextView) findViewById(R.id.myText);
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
        aliArc.setValue(682, new AliArc.RotateListener() {
            @Override
            public void rotate(float sweepAngle, final float value) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(Math.round(value) + "");
                    }
                });
            }
        });
//            }
//        }, 1000);

       /* mAdapter = new ItemDecorationAdapter(this);
        myExRecyclerView = (RecyclerView) findViewById(R.id.myExRecyclerView);
        myExRecyclerView.addItemDecoration(new DividerGridViewItemDecoration(this));
        myExRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
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
            //myExRecyclerView.onRefreshComplete();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
    }

}
