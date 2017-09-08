package com.nandi.disastermanager.videocall.helloanychat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.videocall.config.ConfigEntity;
import com.nandi.disastermanager.videocall.config.ConfigService;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class VideoCallActivity extends Activity implements AnyChatBaseEvent {
    // 视频配置界面标识
    private static final String TAG = "VideoCallActivity";
    @BindView(R.id.ll_progress)
    LinearLayout llProgress;

    private String mStrIP = "183.230.108.112";
    private String mStrName = "平板客户端";
    private int mSPort = 8906;
    private String number;
    private final int LOCALVIDEOAUTOROTATION = 1; // 本地视频自动旋转控制

    public AnyChatCoreSDK anyChatSDK;
    private int myUserId;
    private boolean success = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video_main);
        ButterKnife.bind(this);
        number = getIntent().getStringExtra("PHONE_NUMBER");
        InitSDK();
        InitLayout();
        ApplyVideoConfig();
    }

    private void InitSDK() {
        if (anyChatSDK == null) {
            anyChatSDK = AnyChatCoreSDK.getInstance(this);
            anyChatSDK.SetBaseEvent(this);
            anyChatSDK.InitSDK(Build.VERSION.SDK_INT, 0);
            AnyChatCoreSDK.SetSDKOptionInt(
                    AnyChatDefine.BRAC_SO_LOCALVIDEO_AUTOROTATION,
                    LOCALVIDEOAUTOROTATION);
        }
    }

    private void InitLayout() {

        /**
         *AnyChat可以连接自主部署的服务器、也可以连接AnyChat视频云平台；
         *连接自主部署服务器的地址为自设的服务器IP地址或域名、端口；
         *连接AnyChat视频云平台的服务器地址为：cloud.anychat.cn；端口为：8906
         */
        anyChatSDK.Connect(mStrIP, mSPort);
        /***
         * AnyChat支持多种用户身份验证方式，包括更安全的签名登录，
         * 详情请参考：http://bbs.anychat.cn/forum.php?mod=viewthread&tid=2211&highlight=%C7%A9%C3%FB
         */
        anyChatSDK.Login(mStrName, "");

    }


    protected void onDestroy() {
        if (!success) {
            anyChatSDK.removeEvent(this);
            anyChatSDK.LeaveRoom(-1);
            anyChatSDK.Logout();
            anyChatSDK.Release();
        }
        super.onDestroy();
    }

    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        anyChatSDK.SetBaseEvent(this);
        // 一种简便的方法，当断网的时候，返回到登录界面，不去刷新用户列表，下面广播已经清空了列表
    }

    @Override
    public void OnAnyChatConnectMessage(boolean bSuccess) {
        //
        if (!bSuccess) {
            System.out.println("connect failed");
        }
    }

    @Override
    public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
        if (dwErrorCode == 0) {
            int roomId = 1122;
            anyChatSDK.EnterRoom(roomId, "");
            myUserId = dwUserId;
            setRequest(myUserId, roomId);
        } else {
            llProgress.setVisibility(View.INVISIBLE);
        }
    }

    private void setRequest(int myUserId, int roomId) {
        OkHttpUtils.get().url(getResources().getString(R.string.invite_video_chat_url))
                .addParams("phoneId", number)
                .addParams("userId", myUserId + "")
                .addParams("person", mStrName)
                .addParams("pushType", 1 + "")
                .addParams("roomId", roomId + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(VideoCallActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG, "邀请成功！");
                        Toast.makeText(VideoCallActivity.this, "邀请成功！", Toast.LENGTH_SHORT).show();
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    Thread.sleep(30000);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!success) {
                                                Toast.makeText(VideoCallActivity.this, "对方无应答！", Toast.LENGTH_SHORT).show();
                                                VideoCallActivity.this.finish();
                                            }
                                        }
                                    });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }
                });
    }

    @Override
    public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
        System.out.println("OnAnyChatEnterRoomMessage" + dwRoomId + "err:"
                + dwErrorCode);
    }

    @Override
    public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
        int[] onlineUsers = anyChatSDK.GetRoomOnlineUsers(dwRoomId);
        List<String> names = new ArrayList<>();
        for (int onlineUser : onlineUsers) {
            String s = anyChatSDK.GetUserName(onlineUser);
            names.add(s);
        }
        Log.d(TAG, "当前在线人数：" + onlineUsers.length + "\n" + "在线人员名称:" + names);

    }

    @Override
    public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
        if (bEnter) {
            if (dwUserId != myUserId) {
                Intent intent = new Intent();
                intent.putExtra("UserID", dwUserId + "");
                intent.setClass(this, VideoActivity.class);
                startActivity(intent);
                success = true;
                finish();
            }
        }
    }


    // 根据配置文件配置视频参数
    private void ApplyVideoConfig() {
        ConfigEntity configEntity = ConfigService.LoadConfig(this);
        if (configEntity.mConfigMode == 1) // 自定义视频参数配置
        {
            // 设置本地视频编码的码率（如果码率为0，则表示使用质量优先模式）
            AnyChatCoreSDK.SetSDKOptionInt(
                    AnyChatDefine.BRAC_SO_LOCALVIDEO_BITRATECTRL,
                    configEntity.mVideoBitrate);
//			if (configEntity.mVideoBitrate == 0) {
            // 设置本地视频编码的质量
            AnyChatCoreSDK.SetSDKOptionInt(
                    AnyChatDefine.BRAC_SO_LOCALVIDEO_QUALITYCTRL,
                    configEntity.mVideoQuality);
//			}
            // 设置本地视频编码的帧率
            AnyChatCoreSDK.SetSDKOptionInt(
                    AnyChatDefine.BRAC_SO_LOCALVIDEO_FPSCTRL,
                    configEntity.mVideoFps);
            // 设置本地视频编码的关键帧间隔
            AnyChatCoreSDK.SetSDKOptionInt(
                    AnyChatDefine.BRAC_SO_LOCALVIDEO_GOPCTRL,
                    configEntity.mVideoFps * 4);
            // 设置本地视频采集分辨率
            AnyChatCoreSDK.SetSDKOptionInt(
                    AnyChatDefine.BRAC_SO_LOCALVIDEO_WIDTHCTRL,
                    configEntity.mResolutionWidth);
            AnyChatCoreSDK.SetSDKOptionInt(
                    AnyChatDefine.BRAC_SO_LOCALVIDEO_HEIGHTCTRL,
                    configEntity.mResolutionHeight);
            // 设置视频编码预设参数（值越大，编码质量越高，占用CPU资源也会越高）
            AnyChatCoreSDK.SetSDKOptionInt(
                    AnyChatDefine.BRAC_SO_LOCALVIDEO_PRESETCTRL,
                    configEntity.mVideoPreset);
        }
        // 让视频参数生效
        AnyChatCoreSDK.SetSDKOptionInt(
                AnyChatDefine.BRAC_SO_LOCALVIDEO_APPLYPARAM,
                configEntity.mConfigMode);
        // P2P设置
        AnyChatCoreSDK.SetSDKOptionInt(
                AnyChatDefine.BRAC_SO_NETWORK_P2PPOLITIC,
                configEntity.mEnableP2P);
        // 本地视频Overlay模式设置
        AnyChatCoreSDK.SetSDKOptionInt(
                AnyChatDefine.BRAC_SO_LOCALVIDEO_OVERLAY,
                configEntity.mVideoOverlay);
        // 回音消除设置
        AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_AUDIO_ECHOCTRL,
                configEntity.mEnableAEC);
        // 平台硬件编码设置
        AnyChatCoreSDK.SetSDKOptionInt(
                AnyChatDefine.BRAC_SO_CORESDK_USEHWCODEC,
                configEntity.mUseHWCodec);
        // 视频旋转模式设置
        AnyChatCoreSDK.SetSDKOptionInt(
                AnyChatDefine.BRAC_SO_LOCALVIDEO_ROTATECTRL,
                configEntity.mVideoRotateMode);
        // 本地视频采集偏色修正设置
        AnyChatCoreSDK.SetSDKOptionInt(
                AnyChatDefine.BRAC_SO_LOCALVIDEO_FIXCOLORDEVIA,
                configEntity.mFixColorDeviation);
        // 视频GPU渲染设置
        AnyChatCoreSDK.SetSDKOptionInt(
                AnyChatDefine.BRAC_SO_VIDEOSHOW_GPUDIRECTRENDER,
                configEntity.mVideoShowGPURender);
        // 本地视频自动旋转设置
        AnyChatCoreSDK.SetSDKOptionInt(
                AnyChatDefine.BRAC_SO_LOCALVIDEO_AUTOROTATION,
                configEntity.mVideoAutoRotation);
    }


    @Override
    public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
        anyChatSDK.LeaveRoom(-1);
        anyChatSDK.Logout();
    }


}
