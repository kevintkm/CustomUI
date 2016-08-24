package com.example.lenovo.timescroller.Util;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;

/**
 * Created by kevin.tian on 2016/8/22.
 */
public class BlockCanaryApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        BlockCanary.install(this,new AppBlockCanaryContext()).start();
    }
}
