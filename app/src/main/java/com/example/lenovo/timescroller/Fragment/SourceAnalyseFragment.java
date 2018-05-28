package com.example.lenovo.timescroller.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.timescroller.Adapter.StickAdapter;
import com.example.lenovo.timescroller.Model.NewTab;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.View.CategoryView;
import com.example.lenovo.timescroller.ViewHolder.BaseRecyclerViewHolder;
import com.example.lenovo.timescroller.ViewHolder.HeaderItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kevin.tian on 2016/8/9.
 */
public class SourceAnalyseFragment extends Fragment {
    RecyclerView recyclerView;
    StickAdapter adapter;
    List<String> names = new ArrayList<>();
    LinearLayoutManager manager;
    boolean click;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //TODO
        /**
         * 多次循环向四周拖动，viewGroup会越来越小
         DragViewGroup viewGroup = new DragViewGroup(getContext());
         viewGroup.setBackgroundColor(getResources().getColor(R.color.A9A9A9));
         Button textView = new Button(getContext());
         textView.setPadding(120,120,120,120);
         textView.setText("ViewGroup拖拽");
         textView.setBackgroundColor(getResources().getColor(R.color.blue));
         viewGroup.addView(textView);
         return viewGroup;
         */

//        View view = inflater.inflate(R.category_item_layout.fragment_draglinear_layout,container,false);
//        PaperFlyView group = (PaperFlyView) view;
        View layout = inflater.inflate(R.layout.view_menu_layout, container, false);
        final CategoryView view = (CategoryView) layout.findViewById(R.id.menuView);
        List<NewTab> tabs = new ArrayList<>();
        for (int i = 0; i < 1946 / 7; i++) {
            NewTab tab = new NewTab();
            tab.setName("类目" + i * 7);
            tabs.add(tab);
        }

        for (int i = 0; i < 1946; i++) {
            names.add("tiankeming" + i);
        }
        recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        adapter = new StickAdapter(names, getActivity());
        recyclerView.addItemDecoration(new HeaderItemDecoration(recyclerView, adapter));
        recyclerView.setAdapter(adapter);
//        view.setMenuMode(MenuMode.ONLY_ICON);
        view.setDataTxTAndLable(tabs);

        view.setOnItemClickListener(new BaseRecyclerViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int index, Object object) {
//                recyclerView.scrollToPosition(index*7);
                manager.scrollToPositionWithOffset(index * 7, 0);
                Log.d("itemClick====index-", index + "");
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = manager.findFirstVisibleItemPosition();
                Log.d("scroll====position-", position + "");
                view.scrollToItemView(position / 7);
            }
        });

        return layout;

    }

}
