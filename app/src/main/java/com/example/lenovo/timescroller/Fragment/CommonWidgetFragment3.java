package com.example.lenovo.timescroller.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lenovo.timescroller.Adapter.StickAdapter;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.View.ShoppingCountView;
import com.example.lenovo.timescroller.ViewHolder.HeaderItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin.tian on 2016/8/9.
 */
public class CommonWidgetFragment3 extends BaseFragment {
    @InjectView(R.id.shopping_count_view)
    ShoppingCountView countView;
    @InjectView(R.id.targetView)
    View targetView;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.demo_layout;
    }

    @Override
    public void initUI() {
        countView.setShoppingCount(0);
        countView.setAnimTargetView(targetView);
        countView.setOnShoppingClickListener(new ShoppingCountView.ShoppingClickListener() {
            @Override
            public void onAddClick(int num) {
                countView.setShoppingCount(num);
            }

            @Override
            public void onMinusClick(int num) {
                countView.setShoppingCount(num);
            }
        });
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
