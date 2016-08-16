package com.example.lenovo.timescroller.Fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.View.SimpleScroller;

/**
 * Created by kevin.tian on 2016/8/9.
 */
public class AboutUserFragment extends Fragment {
    private SimpleScroller myScroller;
    private Button layout;
    TextView textView;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_layout, null);
        return view;
    }


}


