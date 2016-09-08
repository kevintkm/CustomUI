package com.example.lenovo.timescroller.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by kevin.tian on 2016/9/5.
 */
public class AlarmManagers {
    public static void register(Context context) {
        Calendar today = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY,11);
        today.set(Calendar.MINUTE,22);
        today.set(Calendar.SECOND,48);
        if (now.after(today)){
            return;
        }

        Intent intent = new Intent("com.example.lenovo.timescroller");
        intent.setClass(context, AlarmReciver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,235,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,today.getTimeInMillis(),pendingIntent);


    }
}
