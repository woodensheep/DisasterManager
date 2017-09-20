package com.nandi.disastermanager.http;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.blankj.utilcode.util.NetworkUtils;
import com.google.gson.Gson;
import com.nandi.disastermanager.MainActivity;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.dao.GreenDaoManager;
import com.nandi.disastermanager.search.entity.DisasterData;
import com.nandi.disastermanager.search.entity.DisasterPoint;
import com.nandi.disastermanager.utils.LogUtils;
import com.nandi.disastermanager.utils.SharedUtils;
import com.nandi.disastermanager.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Date;

import okhttp3.Call;

/**
 * 检测数据更新服务
 *
 * @author qingsong
 */
public class ReplaceService extends Service {
    private static final String TAG = "ReplaceService";
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        sendToFace();
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    try {
                        if (isRequest()) {
                            NetworkUtils.isConnected();
                            updateData();
                            Log.d(TAG, "开始请求数据");
                        }
                        Thread.sleep(5 * 60 * 1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        Log.i(TAG, "onCreate");
    }

    private void sendToFace() {
        /**
         *创建Notification
         */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.icon);
        builder.setContentTitle("前台服务");
        builder.setContentText("后台数据持续更新");
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity
                (this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        //启动到前台
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d(TAG, "执行了onStartCommand（）方法 ");
        return START_STICKY;
    }

    private boolean isRequest() {
        long currentTime = new Date().getTime();
        long lastTime = (long) SharedUtils.getShare(context, "saveTime", 0L);

        return currentTime - lastTime > 24 * 60 * 60 * 1000;
    }

    private void updateData() {
        String id = (String) SharedUtils.getShare(this, "ID", "");
        String level = (String) SharedUtils.getShare(this, "loginlevel", "");

        RequestCall build = OkHttpUtils.get().url(getString(R.string.base_gz_url) + "/appdocking/listDisaster/" + id + "/" + level)
                .build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                updateData();
            }

            @Override
            public void onResponse(final String response, int id) {
                SharedUtils.putShare(context, "saveTime", new Date().getTime());
                GreenDaoManager.deleteDisaster();
                Gson gson = new Gson();
                try {
                    final DisasterData disasterData = gson.fromJson(response, DisasterData.class);
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            for (DisasterData.DataBean dataBean : disasterData.getData()) {
                                String type = "";
                                switch (dataBean.getDisasterType()) {
                                    case "01":
                                        type = "滑坡";
                                        break;
                                    case "02":
                                        type = "地面塌陷";
                                        break;
                                    case "03":
                                        type = "泥石流";
                                        break;
                                    case "04":
                                        break;
                                    case "05":
                                        type = "地裂缝";
                                        break;
                                    case "06":
                                        type = "不稳定斜坡";
                                        break;
                                    case "07":
                                        type = "崩塌";
                                        break;
                                }
                                DisasterPoint disasterPoint = new DisasterPoint();
                                disasterPoint.setDisasterNum(dataBean.getDisasterNum());
                                disasterPoint.setDisasterName(dataBean.getDisasterName());
                                disasterPoint.setDisasterType(type);
                                disasterPoint.setDisasterSite(dataBean.getDisasterSite());
                                disasterPoint.setDisasterLon(dataBean.getDisasterLon());
                                disasterPoint.setDisasterLat(dataBean.getDisasterLat());
                                disasterPoint.setDisasterAdress(dataBean.getDisasterAdress());
                                disasterPoint.setMajorIncentives(dataBean.getMajorIncentives());
                                disasterPoint.setDisasterGrade(dataBean.getDisasterGrade());
                                disasterPoint.setThreatNum(dataBean.getThreatNum());
                                disasterPoint.setThreatObject(dataBean.getThreatObject());
                                disasterPoint.setThreatMoney(dataBean.getThreatMoney());
                                disasterPoint.setFormationTime(dataBean.getFormationTime());
                                disasterPoint.setTableTime(dataBean.getTableTime());
                                disasterPoint.setInvestigationUnit(dataBean.getInvestigationUnit());
                                disasterPoint.setMonitorPersonnel(dataBean.getMonitorPersonnel());
                                disasterPoint.setPhoneNum(dataBean.getPhoneNum());
                                disasterPoint.setCity(dataBean.getCity());
                                disasterPoint.setCounty(dataBean.getCounty());
                                disasterPoint.setTown(dataBean.getTown());
                                GreenDaoManager.insertDisasterPoint(disasterPoint);
                            }
                        }
                    }.start();
                    LogUtils.d(TAG, "数据保存结束");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
