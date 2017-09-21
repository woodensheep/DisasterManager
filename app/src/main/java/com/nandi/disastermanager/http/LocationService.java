package com.nandi.disastermanager.http;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.dao.GreenDaoManager;
import com.nandi.disastermanager.entity.LocationInfo;
import com.nandi.disastermanager.utils.Constant;
import com.nandi.disastermanager.utils.SharedUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by ChenPeng on 2017/9/19.
 */

public class LocationService extends Service {
    private static final String TAG = "LocationService";
    private Context context;
    private LocationManager locationManager;

    //通过binder实现了 调用者（client）与 service之间的通信
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        sendToFace();
        saveLocationInfo();
        startLocation();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private void saveLocationInfo() {
        GreenDaoManager.deleteAllLocation();
        LocationInfo locationInfo = new LocationInfo();
        locationInfo.setStartTime(TimeUtils.millis2String(new Date().getTime()));
        locationInfo.setUserName((String) SharedUtils.getShare(context, Constant.USER_NAME, ""));
        GreenDaoManager.insertLocation(locationInfo);
    }

    private void startLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取当前可用的位置控制器
        List<String> list = locationManager.getProviders(true);
        Log.d(TAG, "开始定位。。。。");
        String provider;
        if (list.contains(LocationManager.GPS_PROVIDER)) {
            Log.d(TAG, "GPS定位。。。。");
            //是否为GPS位置控制器
            provider = LocationManager.GPS_PROVIDER;
        } else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
            //是否为网络位置控制器
            Log.d(TAG, "网络定位。。。。");
            provider = LocationManager.NETWORK_PROVIDER;

        } else {
            Toast.makeText(this, "请检查网络或GPS是否打开",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "请检查网络或GPS是否打开",
                    Toast.LENGTH_LONG).show();
            return;
        }

        /**
         * 绑定监听
         * 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种，前者是GPS,后者是GPRS以及WIFI定位
         * 参数2，位置信息更新周期.单位是毫秒
         * 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
         * 参数4，监听
         * 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新
         */
        locationManager.requestLocationUpdates(provider, 0, 10, locationListener);
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            //位置信息变化时触发
            String longitude = String.valueOf(location.getLongitude());
            String latitude = String.valueOf(location.getLatitude());
            Log.d(TAG, "jd:" + longitude + "/wd:" + latitude);
            LocationInfo locationInfo = GreenDaoManager.queryLocation();
            String userName = locationInfo.getUserName();
            String startTime = locationInfo.getStartTime();
            Long id = locationInfo.getId();
            String lonAndLat = locationInfo.getLonAndLat();
            LocationInfo l = new LocationInfo();
            l.setId(id);
            l.setUserName(userName);
            l.setStartTime(startTime);
            l.setEndTime(TimeUtils.millis2String(new Date().getTime()));
            if (lonAndLat == null || "".equals(lonAndLat)) {
                l.setLonAndLat(longitude + "," + latitude);
            } else {
                l.setLonAndLat(lonAndLat + "|" + longitude + "," + latitude);
            }
            GreenDaoManager.updateLocation(l);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //GPS状态变化时触发
        }

        @Override
        public void onProviderEnabled(String provider) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        @Override
        public void onProviderDisabled(String provider) {
            //GPS开启时触发
        }
    };

    private void sendToFace() {
        /**
         *创建Notification
         */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.icon);
        builder.setContentTitle("定位服务");
        builder.setContentText("正在定位中...");
        builder.setAutoCancel(false);
        Notification notification = builder.build();
        //启动到前台
        startForeground(1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
