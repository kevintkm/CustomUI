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
public class ProjectLearningFragment extends Fragment {
    private SimpleScroller myScroller;
    private Button layout;
    TextView textView;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_scroll_layout, null);
        myScroller = (SimpleScroller) view.findViewById(R.id.myviewGroup);
        layout = (Button) view.findViewById(R.id.ming);
        textView = (TextView) view.findViewById(R.id.text);
        final int index=0;
        ObjectAnimator animator = ObjectAnimator.ofFloat(textView, "translationX", 0, 100,200,300,400,300,200,100);
        animator.setDuration(9000);
        animator.start();
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("======",textView.getRight()+"");
             myScroller.scrollTo(-100);

            }
        });
        return view;
    }


}


