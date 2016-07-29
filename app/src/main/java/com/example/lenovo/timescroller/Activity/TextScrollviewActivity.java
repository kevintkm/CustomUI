package com.example.lenovo.timescroller.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.timescroller.R;

public class TextScrollviewActivity extends Activity implements TabLayout.OnTabSelectedListener {

    private LinearLayout layout;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_scrollview);

        layout = (LinearLayout) findViewById(R.id.layout);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        tabLayout.setOnTabSelectedListener(this);

        tabLayout.addTab(tabLayout.newTab().setText("News").setIcon(R.drawable.md_refresh_loading01));
        tabLayout.addTab(tabLayout.newTab().setText("Socities").setIcon(R.drawable.md_refresh_loading02));
        tabLayout.addTab(tabLayout.newTab().setText("People").setIcon(R.drawable.md_refresh_loading03));
        tabLayout.addTab(tabLayout.newTab().setText("hello"));

        for (int i = 0; i < 9; i++) {
            TextView textView = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textView.setTextColor(Color.RED);
            textView.setText("hahaha" + i);
            textView.setPadding(10, 10, 10, 10);
            layout.addView(textView);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Toast.makeText(this, tab.getText().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
