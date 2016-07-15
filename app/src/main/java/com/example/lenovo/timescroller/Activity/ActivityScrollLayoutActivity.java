package com.example.lenovo.timescroller.Activity;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.View.SimpleScroller;

public class ActivityScrollLayoutActivity extends Activity {

    private SimpleScroller myScroller;
    private Button layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_layout);
        myScroller = (SimpleScroller) findViewById(R.id.myviewGroup);
        layout = (Button) findViewById(R.id.ming);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myScroller.scrollTo(-100);
            }
        });
//        myScroller = (SimpleScroller) findViewById(R.id.myScroller);
//        myScroller.scrollTo(-200);
       /* button = (Button) findViewById(R.id.myButton);
        edit = (EditText) findViewById(R.id.myEdit);
       */
    }

    public void scroll(View view) {

    }

}
