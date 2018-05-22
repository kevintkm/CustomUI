package com.example.lenovo.timescroller.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.timescroller.Model.NewTab;
import com.example.lenovo.timescroller.View.MenuView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin.tian on 2016/8/9.
 */
public class SourceAnalyseFragment extends Fragment{
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

//        View view = inflater.inflate(R.layout.fragment_draglinear_layout,container,false);
//        PaperFlyView group = (PaperFlyView) view;
        MenuView view = new MenuView(getActivity());
        List<NewTab> tabs = new ArrayList<>();
        for (int i=0;i<8;i++){
            NewTab tab = new NewTab();
//            if (i%3==0) {
//                tab.setIconUrl("http://storage.slide.news.sina.com.cn/slidenews/77_ori/2018_21/74766_823190_337932.gif");
//            }
            tab.setName("ming"+i);
            tabs.add(tab);
        }
//        view.setMenuMode(MenuMode.ONLY_ICON);
        view.setDataTxTAndLable(tabs);
        return view;

    }

}
