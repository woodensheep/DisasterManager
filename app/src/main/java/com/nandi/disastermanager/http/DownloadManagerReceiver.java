package com.nandi.disastermanager.http;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.nandi.disastermanager.utils.DownloadUtils;
import com.nandi.disastermanager.utils.ToastUtils;

import java.io.File;
import java.util.Arrays;

/**
 * Created by qingsong on 2017/9/26.
 */

public class DownloadManagerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(action)) {
            System.out.println("用户点击了通知");

            // 点击下载进度通知时, 对应的下载ID以数组的方式传递
            long[] ids = intent.getLongArrayExtra(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
            System.out.println("ids: " + Arrays.toString(ids));
            //点击通知栏取消下载
            manager.remove(ids);
            ToastUtils.showShort(context, "取消下载");
        } else if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
            System.out.println("id: " + id);
            installAPK(context);
        }
    }

    //下载到本地后执行安装
    private void installAPK(Context mContext) {
        File file = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                , "app-release.apk");
        Intent i = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri =
                    FileProvider.getUriForFile(mContext, "com.nandi.disastermanager.fileprovider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            i.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            i.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }
        mContext.startActivity(i);

    }

}
