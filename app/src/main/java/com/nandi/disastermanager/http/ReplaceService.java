package com.nandi.disastermanager.http;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
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
import com.nandi.disastermanager.search.entity.GTSLocation;
import com.nandi.disastermanager.search.entity.GTSLocationPoint;
import com.nandi.disastermanager.search.entity.MonitorData;
import com.nandi.disastermanager.search.entity.MonitorListData;
import com.nandi.disastermanager.search.entity.MonitorListPoint;
import com.nandi.disastermanager.search.entity.MonitorPoint;
import com.nandi.disastermanager.utils.Constant;
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
    private String id;
    private String level;
    private String name;
    private String password;
    private boolean openGprs;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        sendToFace();
        name = (String) SharedUtils.getShare(this, Constant.USER_NAME, "");
        password = (String) SharedUtils.getShare(this, Constant.PASSWORD, "");
        id = (String) SharedUtils.getShare(this, Constant.AREA_ID, "");
        level = (String) SharedUtils.getShare(this, Constant.LEVEL, "");
        openGprs = (boolean) SharedUtils.getShare(this, Constant.isOpenGPRS, false);
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    try {
                        if (isDisRequest()) {
                            if (NetworkUtils.isConnected()) {
                                if (NetworkUtils.isWifiConnected()) {
                                    loginPost(1);
                                } else {
                                    if (openGprs) {
                                        loginPost(1);
                                    }
                                }
                            }
                        }
                        if (isMonRequest()) {
                            if (NetworkUtils.isConnected()) {
                                if (NetworkUtils.isWifiConnected()) {
                                    loginPost(2);
                                } else {
                                    if (openGprs) {
                                        loginPost(2);
                                    }
                                }
                            }
                        }
                        if (isMonDataRequest()) {
                            if (NetworkUtils.isConnected()) {
                                if (NetworkUtils.isWifiConnected()) {
                                    loginPost(3);
                                } else {
                                    if (openGprs) {
                                        loginPost(3);
                                    }
                                }
                            }
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

    /**
     * 登录请求
     * @param i
     */

    private void loginPost(final int i) {
        OkHttpUtils.get().url(getString(R.string.base_gz_url) + "/appdocking/login/" + name + "/" + password + "/2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(final String response, int id) {
                        switch (i){
                            case 1:
                                upDisData();
                                break;
                            case 2:
                                upMonData();
                                break;
                            case 3:
                                upMonDatas();
                                break;
                        }
                    }
                });

    }

    /*置前台*/
    private void sendToFace() {
        /**
         *创建Notification
         */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.icon);
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

    /*灾害点的请求时间*/
    private boolean isDisRequest() {
        long currentTime = new Date().getTime();
        long lastTime = (long) SharedUtils.getShare(context, Constant.SAVE_DIS_TIME, 0L);

        return currentTime - lastTime > 24 * 60 * 60 * 1000;
    }

    /*监测点列表的请求时间*/
    private boolean isMonRequest() {
        long currentTime = new Date().getTime();
        long lastTime = (long) SharedUtils.getShare(context, Constant.SAVE_MON_TIME, 0L);

        return currentTime - lastTime > 24 * 60 * 60 * 1000;
    }

    /*监测点数据的请求时间*/
    private boolean isMonDataRequest() {
        long currentTime = new Date().getTime();
        long lastTime = (long) SharedUtils.getShare(context, Constant.SAVE_MONDATA_TIME, 0L);

        return currentTime - lastTime > 60 * 60 * 1000;
    }

    /*灾害点数据的请求*/
    private void upDisData() {
        RequestCall build = OkHttpUtils.get().url(getString(R.string.base_gz_url) + "/appdocking/listDisasterPoint/" + id + "/" + level)
                .build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                upDisData();
            }

            @Override
            public void onResponse(final String response, int id) {
                LogUtils.d(TAG, response);

                Gson gson = new Gson();
                try {
                    final DisasterData disasterData = gson.fromJson(response, DisasterData.class);
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            GreenDaoManager.deleteDisaster();
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
                    upLocation();
                    LogUtils.d(TAG, "数据保存结束");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /*监测点列表的请求*/
    private void upMonData() {
        RequestCall build = OkHttpUtils.get().url(getString(R.string.base_gz_url) + "/appdocking/listMonitorOrigin/" + id + "/" + level)
                .build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                upMonData();
            }

            @Override
            public void onResponse(final String response, int id) {
                SharedUtils.putShare(context, Constant.SAVE_MON_TIME, new Date().getTime());
                Log.i("qingsong", response);

                Gson gson = new Gson();
                try {
                    final MonitorListData monitorListData = gson.fromJson(response, MonitorListData.class);
                    GreenDaoManager.deleteAllMonitor();
                    new Thread() {
                        @Override
                        public void run() {
                            for (MonitorListData.DataBean dataBean : monitorListData.getData()) {
                                MonitorListPoint monitorListPoint = new MonitorListPoint();
                                monitorListPoint.setMonitorId(dataBean.getID());
                                monitorListPoint.setName(dataBean.getNAME());
                                monitorListPoint.setDisNum(dataBean.getHTPID());
                                monitorListPoint.setLat(dataBean.getLATITUDE());
                                monitorListPoint.setLon(dataBean.getLONGITUDE());
                                monitorListPoint.setReginId(dataBean.getREGIONID());
                                GreenDaoManager.insertMonitorListPoint(monitorListPoint);
                            }
                        }
                    }.start();
                    LogUtils.d(TAG, "监测数据保存结束");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /*监测点数据的请求*/
    private void upMonDatas() {
        String url = getString(R.string.base_gz_url) + "/appdocking/listMonitor/" + id + "/" + level;
        RequestCall build = OkHttpUtils.get().url(url)
                .build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

                upMonDatas();
            }

            @Override
            public void onResponse(final String response, int id) {
                SharedUtils.putShare(context, Constant.SAVE_MONDATA_TIME, new Date().getTime());
                Log.i("qingsong", response);
                System.out.println(response);

                Gson gson = new Gson();
                try {
                    final MonitorData monitorData = gson.fromJson(response, MonitorData.class);
                    GreenDaoManager.deleteAllMonitorData();
                    new Thread() {
                        @Override
                        public void run() {
                            for (MonitorData.DataBean dataBean : monitorData.getData()) {
                                MonitorPoint monitorPoint = new MonitorPoint();
                                monitorPoint.setMonitorId(dataBean.getMonitorId());
                                monitorPoint.setName(dataBean.getName());
                                monitorPoint.setTime(dataBean.getTime());
                                monitorPoint.setMonitorData(dataBean.getMonitorData());
                                GreenDaoManager.insertMonitorPoint(monitorPoint);
                            }
                        }
                    }.start();
                    LogUtils.d(TAG, "监测数据保存结束");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
    /*国土所坐标数据的请求*/
    private void upLocation() {

        String url = getString(R.string.base_gz_url) +"appdocking/listLandJdWd";
        RequestCall build = OkHttpUtils.get().url(url)
                .build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                upLocation();
            }

            @Override
            public void onResponse(final String response, int id) {
                SharedUtils.putShare(context, Constant.SAVE_DIS_TIME, new Date().getTime());
                Log.i("qingsong", response);
                System.out.println(response);
                GreenDaoManager.deleteGTSLocation();
                Gson gson = new Gson();
                try {
                    final GTSLocation gtsLocation= gson.fromJson(response, GTSLocation.class);
                    new Thread() {
                        @Override
                        public void run() {
                            for (GTSLocation.DataBean dataBean : gtsLocation.getData()) {
                                GTSLocationPoint gtsLocationPoint = new GTSLocationPoint();
                                gtsLocationPoint.setAdminname(dataBean.getAdminname());
                                gtsLocationPoint.setJd(dataBean.getJd());
                                gtsLocationPoint.setWd(dataBean.getWd());
                                gtsLocationPoint.setTown(dataBean.getTown());
                                GreenDaoManager.insertGTSLocation(gtsLocationPoint);
                            }
                        }
                    }.start();
                    ToastUtils.showShort(context, "更新坐标信息成功");
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
