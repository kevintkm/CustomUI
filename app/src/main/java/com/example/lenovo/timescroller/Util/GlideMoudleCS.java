package com.example.lenovo.timescroller.Util;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

/**
 * Created by kevin.tian on 2016/8/23.
 */
public class GlideMoudleCS implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        final File file = new File(Environment.getExternalStorageDirectory(), "Keming");
        if (!file.exists()){
            file.mkdir();
        }
        /**
         * 缓存到外部目录Android/data/应用报名下

        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context,file.getAbsolutePath(),250*1024*1024));*/
        /**
         * 缓存到SD根目录下
         */
        builder.setDiskCache(new DiskCache.Factory() {
            @Override public DiskCache build() {
                return DiskLruCacheWrapper.get(file, 256*1024);
            }
        });
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
