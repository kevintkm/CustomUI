package com.example.lenovo.timescroller.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.lenovo.timescroller.Activity.MainActivity;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.HeadsUps;

/**
 * Created by kevin.tian on 2016/9/5.
 */
public class AlarmReciver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        HeadsUps.show(context, MainActivity.class,"有新的更新","一大波福利即将来临", R.drawable.kevin,R.mipmap.ic_play,3242);
    }
}
