package com.nandi.disastermanager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.blankj.utilcode.util.Utils;
import com.nandi.disastermanager.dao.GreenDaoManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by lemon on 2017/7/18.
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    private static List<Activity> activities;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        initCloudChannel(this);
        Utils.init(this);
        mContext = getApplicationContext();
        GreenDaoManager.getInstance();
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("网络请求"))
                .cookieJar(cookieJar)
                .connectTimeout(10000 * 6L, TimeUnit.MILLISECONDS)
                .readTimeout(10000 * 6L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public static List<Activity> getActivities() {
        if (activities == null) {
            synchronized (Object.class) {
                if (activities == null) {
                    activities = new ArrayList<>();
                }
            }
        }
        return activities;
    }
    public static Context getContext() {
        return mContext;
    }

    private void initCloudChannel(Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "init cloudchannel success--------------------------------------------");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d(TAG, "---------------------------------------------init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });


    }

}
