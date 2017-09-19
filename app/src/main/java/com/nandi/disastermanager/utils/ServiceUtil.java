package com.nandi.disastermanager.utils;

import java.text.SimpleDateFormat;
import java.util.List;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.nandi.disastermanager.http.AlarmReceiver;


public class ServiceUtil {
    /*两次定时器间隔时间*/
    public static final int ELAPSED_TIME =60*1000;
    /*后台最大进程运行数*/
    public static final int RETRIVE_SERVICE_COUNT = 50;
    /*两次定时器间隔时间*/
    public static final String POI_SERVICE = "com.nandi.disastermanager.http.ReplaceService";

    /*判断service是否处于运行状态*/
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceInfos = activityManager.getRunningServices(RETRIVE_SERVICE_COUNT);

        if (null == serviceInfos || serviceInfos.size() < 1) {
            return false;
        }

        for (int i = 0; i < serviceInfos.size(); i++) {
            if (serviceInfos.get(i).service.getClassName().contains(className)) {
                isRunning = true;
                break;
            }
        }
        System.out.println("当前类：" + className + "，当前运行状态：" + isRunning);
        return isRunning;
    }

    //启动定时器
    public static void invokeTimerPOIService(Context context) {
        Log.e("Tag", "启动定时任务....");
        PendingIntent alarmSender = null;
        Intent starti = new Intent(context, AlarmReceiver.class);
        try {
            alarmSender = PendingIntent.getBroadcast(context, 0, starti,
                    PendingIntent.FLAG_CANCEL_CURRENT);
        } catch (Exception e) {
            Log.e("Tag", "定时任务启动失败 " + e.toString());
        }
        System.out.println("到闹钟服务了");
        System.out.println("闹钟服务时间：" +
         new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis()));
        AlarmManager am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), ELAPSED_TIME, alarmSender);
        System.out.println("闹钟服务执行了？");
    }


    /*解除定时器*/
    public static void cancleAlarmManager(Context context) {
        Log.e("Tag", "取消定时任务... ");
        Intent endi = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, endi,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
        alarm.cancel(pendingIntent);
    }

    /**

     * 检测是否已经连接网络。

     * @param context

     * @return 当且仅当连上网络时返回true,否则返回false。

     */

    public static boolean isConnectingToInternet(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context

                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {

            return false;

        }

        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        return (info != null) && info.isAvailable();
    }

}

