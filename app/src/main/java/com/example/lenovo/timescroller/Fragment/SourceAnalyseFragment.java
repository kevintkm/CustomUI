package com.example.lenovo.timescroller.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.Util;
import com.example.lenovo.timescroller.View.DragViewGroup;

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

        View view = inflater.inflate(R.layout.fragment_draglinear_layout,container,false);
        DragViewGroup group = (DragViewGroup) view;
        group.init();
        return view;

    }

}
