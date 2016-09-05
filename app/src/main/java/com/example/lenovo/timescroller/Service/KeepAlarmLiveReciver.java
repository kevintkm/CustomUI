package com.example.lenovo.timescroller.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by kevin.tian on 2016/9/5.
 */
public class KeepAlarmLiveReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      //  if (intent!=null && intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
            AlarmManagers.register(context);
       // }
    }
}
