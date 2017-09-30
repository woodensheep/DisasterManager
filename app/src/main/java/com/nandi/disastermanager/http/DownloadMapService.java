package com.nandi.disastermanager.http;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.nandi.disastermanager.R;

public class DownloadMapService extends Service {
    private static final String TAG = "DownloadMapService";
    private FileDownloader impl;
    private NotificationManager manager;
    private NotificationCompat.Builder builder;
    private String path;
    private int id;

    @Override
    public void onCreate() {
        super.onCreate();
        FileDownloader.setup(getApplicationContext());
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/guizhou.tpk";
        impl = FileDownloader.getImpl();
        BaseDownloadTask baseDownloadTask = impl.create("http://202.98.195.125:8082/gzcmdback/downloadPackage.do")
                .setPath(path)
                .setListener(new DownloadListener());
        id = baseDownloadTask.getId();
        baseDownloadTask.start();
        builder = new NotificationCompat.Builder(getApplicationContext());
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class DownloadListener extends FileDownloadLargeFileListener {

        @Override
        protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
            Log.d(TAG, "pending/soFarBytes:" + soFarBytes + "/totalBytes:" + totalBytes);
            builder.setSmallIcon(R.drawable.icon)
                    .setContentTitle("地图下载")
                    .setContentText("正在下载离线地图包")
                    .setProgress(100, 0, false)
                    .setAutoCancel(false);
            manager.notify(1, builder.build());
        }

        @Override
        protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
            Log.d(TAG, "progress/soFarBytes:" + soFarBytes + "/totalBytes:" + totalBytes);
            long l = 2282787977L / 100;
            int l1 = (int) Math.ceil(soFarBytes / l);
            builder.setProgress(100, l1, false);
            builder.setContentText("已下载" + l1 + "%");
            manager.notify(1, builder.build());
        }

        @Override
        protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
            Log.d(TAG, "paused/soFarBytes:" + soFarBytes + "/totalBytes:" + totalBytes);
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            builder.setProgress(0, 0, false);
            builder.setContentText("离线地图下载完成");
            builder.setAutoCancel(true);
            manager.notify(1, builder.build());
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            Log.d(TAG, "error/" + e.getMessage());
            builder.setProgress(0, 0, false);
            builder.setAutoCancel(true);
            builder.setContentText("下载失败，请重试");
            manager.notify(1, builder.build());
            impl.clear(id, path);
            stopSelf();
        }

        @Override
        protected void warn(BaseDownloadTask task) {
            Log.d(TAG, "warn");
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
