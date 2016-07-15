package com.example.lenovo.timescroller.Activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.lenovo.timescroller.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TabLayout tableLayout;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        textView = (TextView) findViewById(R.id.text);
        textView.setText("Hello World");
        GradientDrawable drawable = (GradientDrawable) textView.getBackground();
        drawable.setStroke(3, Color.parseColor("#fb6947"));
        textView.setBackground(drawable);
        tableLayout = (TabLayout) findViewById(R.id.tablayout);
        for (int i=0;i<4;i++){
            tableLayout.addTab(tableLayout.newTab().setText("title"+i));
        }
    }

    private void okHttpTest() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://api.rottentomatoes.com/api/public/v1.0")
                .build();
        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.v("response====>", response.body().string());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ;
}


