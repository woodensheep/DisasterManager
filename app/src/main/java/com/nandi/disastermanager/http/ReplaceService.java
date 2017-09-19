package com.nandi.disastermanager.http;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.nandi.disastermanager.MainActivity;
import com.nandi.disastermanager.R;

/**
 * 检测数据更新服务
 *
 * @author qingsong
 */
public class ReplaceService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        sendToFace();
        Log.i("qingsong","onCreate");
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
        Log.i("qingsong","onStartCommand");
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
