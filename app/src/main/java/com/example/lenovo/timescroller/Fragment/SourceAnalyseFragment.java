package com.example.lenovo.timescroller.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.View.DragViewGroup;
import com.example.lenovo.timescroller.View.SimpleScroller;

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
        DragViewGroup viewGroup = new DragViewGroup(getContext());
        viewGroup.setTextColor(getResources().getColor(R.color.red));
        viewGroup.setPadding(20,20,20,20);
        viewGroup.setBackgroundColor(getResources().getColor(R.color.blue));
        ViewGroup.LayoutParams params = container.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        viewGroup.setLayoutParams(params);
        return viewGroup;
    }

}
