package com.example.lenovo.timescroller.Activity;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.timescroller.R;

import java.util.ArrayList;
import java.util.List;

public class TextScrollviewActivity extends Activity implements TabLayout.OnTabSelectedListener {

    private LinearLayout layout;
    private TabLayout tabLayout;
    private List<TabObject> tabs;
    private TextView mAnimator;

    public class TabObject {
        String text;
        int image;

        public TabObject(String text, int image) {
            this.text = text;
            this.image = image;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_scrollview);
        layout = (LinearLayout) findViewById(R.id.layout);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        mAnimator = (TextView) findViewById(R.id.animator);
        tabLayout.setOnTabSelectedListener(this);

        TabObject object = new TabObject("Tab1",R.drawable.md_refresh_loading01);
        TabObject object1 = new TabObject("Tab2",R.drawable.md_refresh_loading02);
        TabObject object2 = new TabObject("Tab3",R.drawable.md_refresh_loading03);
        tabs=new ArrayList<>();
        tabs.add(object);
        tabs.add(object1);
        tabs.add(object2);



        for (int i = 0;i<tabs.size();i++ ){
            TabObject tab = tabs.get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.custom_tablayout_tabview, null, false);
         /*   TextView text = (TextView) view.findViewById(R.id.text1);
            text.setText(tab.getText());
            ImageView imageView = (ImageView) view.findViewById(R.id.icon);
            imageView.setImageResource(tab.getImage());*/
            tabLayout.addTab(tabLayout.newTab().setCustomView(view).setText(tab.getText()).setIcon(tab.getImage()));
        }

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
