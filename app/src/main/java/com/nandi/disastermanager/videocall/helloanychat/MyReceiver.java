package com.nandi.disastermanager.videocall.helloanychat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by ChenPeng on 2017/5/31.
 */

public class MyReceiver extends MessageReceiver {
    private static final String TAG = "AliPush";
    private Notification notification;
    private NotificationManager manager;
    private Message message;
    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        // TODO 处理推送通知
        Log.e(TAG, "Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
    }
    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.e(TAG, "onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }
    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.e(TAG, "onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }
    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.e(TAG, "onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
    }
    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        Log.e(TAG, "onNotificationRemoved");
    }
    @Override
    protected void onMessage(Context context, CPushMessage cPushMessage) {
        super.onMessage(context, cPushMessage);
        manager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String msg=cPushMessage.getContent();
        LogUtils.d(TAG,msg);
        try {
            message=new Message();
            JSONObject object=new JSONObject(msg);
            message.setInvite_userId(object.getInt("userid"));
            message.setRoom_id(object.getInt("roomid"));
            message.setInvite_man(object.getString("invite"));
            message.setPush_msg(object.getString("message"));
            sendNotification(context);
            Intent intent=new Intent();
            intent.putExtra("MESSAGE",message);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(context,ReceiveVideoActivity.class);
            context.startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(Context context) {
        Notification.Builder builder=new Notification.Builder(context);
        Intent intent=new Intent();
        intent.setClass(context,MyService.class);
        intent.putExtra("ROOM_ID",message.getRoom_id());
        intent.putExtra("USER_ID",message.getInvite_userId());
        PendingIntent pendingIntent=PendingIntent.getService(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification=builder.setContentTitle("视频来电")
                .setContentText(message.getPush_msg())
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        manager.notify(1,notification);
    }
}
