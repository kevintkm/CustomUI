package com.example.lenovo.timescroller.Util;

import com.github.moduth.blockcanary.BlockCanaryContext;
import com.github.moduth.blockcanary.BuildConfig;

/**
 * Created by kevin.tian on 2016/8/22.
 */
public class AppBlockCanaryContext extends BlockCanaryContext {
    @Override
    public int getConfigBlockThreshold() {
        return 500;
    }

    @Override
    public boolean isNeedDisplay() {
        return BuildConfig.DEBUG;
    }

    @Override
    public String getLogPath() {
        return "/blockcanary/performance";
    }
}
