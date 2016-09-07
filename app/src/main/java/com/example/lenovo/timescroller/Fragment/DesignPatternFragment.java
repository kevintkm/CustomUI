package com.example.lenovo.timescroller.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.Util;
import com.example.lenovo.timescroller.View.AliArc;
import com.example.lenovo.timescroller.View.DragViewGroup;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kevin.tian on 2016/8/9.
 */
public class DesignPatternFragment extends BaseFragment {

    @InjectView(R.id.design_button_ali)
    TextView designButtonAli;
    @InjectView(R.id.design_button_drag)
    TextView designButtonDrag;
    @InjectView(R.id.design_linear_layout)
    LinearLayout designLinearLayout;
    @InjectView(R.id.design_frame_layout)
    FrameLayout frameLayout;
    @InjectView(R.id.viewStud)
    ViewStub viewStub;
    AliArc aliArc;
    TextView textView;
    DragViewGroup dragViewGroup;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_designpattern_layout;
    }

    @Override
    public void initUI() {
        aliArc = IFindViwById(R.id.iView);
        textView = IFindViwById(R.id.txtView);
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.design_button_ali)
    void aliClick(final View v) {
        if (aliArc == null) {
            viewStub.inflate();
            initUI();
        } else {
            aliArc.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
        if (dragViewGroup!=null)
        dragViewGroup.setVisibility(View.GONE);
        aliArc.setValue(680, new AliArc.RotateListener() {
            @Override
            public void rotate(float sweepAngle, final float value) {

                textView.setText(Util.formatSum(value));
            }
        });
    }

    @OnClick(R.id.design_button_drag)
    void dragClick(View v) {
        if (aliArc!=null&&textView!=null) {
            aliArc.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
        if (dragViewGroup == null){
            dragViewGroup = new DragViewGroup(getmContext());
            dragViewGroup.init();
            frameLayout.addView(dragViewGroup);
        }
        else dragViewGroup.setVisibility(View.VISIBLE);

    }

}


