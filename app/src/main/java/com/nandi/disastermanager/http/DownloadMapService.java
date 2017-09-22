package com.nandi.disastermanager.http;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.FileProvider;
import android.webkit.MimeTypeMap;

import com.nandi.disastermanager.R;
import com.nandi.disastermanager.utils.ToastUtils;

import java.io.File;

public class DownloadMapService extends Service {
    String url;

    private long mReference = 0;
    private DownloadManager downloadManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(receiver, filter);
        url = getString(R.string.base_gz_url)+"/gzcmd-back/downloadPackage.do";
        // 调用下载
        File file = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                , "map.tpk");
        if (file.exists()) {
            boolean delete = file.delete();
        }
        initDownManager();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initDownManager() {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        //下载网络需求  手机数据流量、wifi
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);

        //设置是否允许漫游网络 建立请求 默认true
        request.setAllowedOverRoaming(true);

        //设置通知类型
        setNotification(request);

        //设置下载路径
        setDownloadFilePath(request);

		/*在默认的情况下，通过Download Manager下载的文件是不能被Media Scanner扫描到的 。
        进而这些下载的文件（音乐、视频等）就不会在Gallery 和  Music Player这样的应用中看到。
		为了让下载的音乐文件可以被其他应用扫描到，我们需要调用Request对象的
		 */
        request.allowScanningByMediaScanner();

		/*如果我们希望下载的文件可以被系统的Downloads应用扫描到并管理，
		我们需要调用Request对象的setVisibleInDownloadsUi方法，传递参数true。*/
        request.setVisibleInDownloadsUi(true);

        //设置请求的Mime
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        request.setMimeType(mimeTypeMap.getMimeTypeFromExtension(url));

        //开始下载
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        mReference = downloadManager.enqueue(request);

		/*
		下载管理器中有很多下载项，怎么知道一个资源已经下载过，避免重复下载呢？
		我的项目中的需求就是apk更新下载，用户点击更新确定按钮，第一次是直接下载，
		后面如果用户连续点击更新确定按钮，就不要重复下载了。
		可以看出来查询和操作数据库查询一样的
		 */
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(mReference);
        Cursor cursor = downloadManager.query(query);
        if (!cursor.moveToFirst()) {// 没有记录

        } else {
            //有记录
        }
    }


    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    /**
     * 设置状态栏中显示Notification
     */
    void setNotification(DownloadManager.Request request) {
        //设置Notification的标题
        request.setTitle("下载");

        //设置描述
        request.setDescription("下载离线地图");

        //request.setNotificationVisibility( Request.VISIBILITY_VISIBLE ) ;

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        //request.setNotificationVisibility( Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION ) ;

        //request.setNotificationVisibility( Request.VISIBILITY_HIDDEN ) ;
    }

    /**
     * 设置下载文件存储目录
     */
    void setDownloadFilePath(DownloadManager.Request request) {
        /**
         * 方法4
         * 文件将存放在外部存储的确实download文件内，如果无此文件夹，创建之，如果有，下面将返回false。
         * 系统有个下载文件夹，比如小米手机系统下载文件夹  SD卡--> Download文件夹
         */
        //创建目录
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();

        //设置文件存放路径
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "map.tpk");
    }

    /**
     * 广播接受器, 下载完成监听器
     */
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                //下载完成了
                //获取当前完成任务的ID
                long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                ToastUtils.showShort(context, "安装包下载完成！");

            }

            if (action.equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
              ToastUtils.showShort(context,"广播被点击了");
            }
        }
    };



    @Override
    public void onDestroy() {

        // 注销下载广播
        if (receiver != null)
            unregisterReceiver(receiver);

        super.onDestroy();
    }
}
