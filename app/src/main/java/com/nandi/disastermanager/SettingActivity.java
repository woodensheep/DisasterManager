package com.nandi.disastermanager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.blankj.utilcode.util.NetworkUtils;
import com.nandi.disastermanager.dao.GreenDaoManager;
import com.nandi.disastermanager.http.ReplaceService;
import com.nandi.disastermanager.http.UpdataService;
import com.nandi.disastermanager.utils.AppUtils;
import com.nandi.disastermanager.utils.Constant;
import com.nandi.disastermanager.utils.LogUtils;
import com.nandi.disastermanager.utils.SharedUtils;
import com.nandi.disastermanager.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class SettingActivity extends Activity {

    private static final String TAG = "SettingActivity";
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.userLevel)
    TextView userLevel;
    @BindView(R.id.download)
    TextView download;
    @BindView(R.id.changePassword)
    TextView changePassword;
    @BindView(R.id.logOut)
    TextView logOut;
    @BindView(R.id.userMessage)
    LinearLayout userMessage;
    @BindView(R.id.downloadMap)
    TextView downloadMap;
    @BindView(R.id.downloadApp)
    TextView downloadApp;
    @BindView(R.id.ll_download)
    LinearLayout llDownload;
    @BindView(R.id.et_userName)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_new_password1)
    EditText etNewPassword1;
    @BindView(R.id.changeSure)
    TextView changeSure;
    @BindView(R.id.changeStop)
    TextView changeStop;
    @BindView(R.id.ll_change_password)
    LinearLayout llChangePassword;
    @BindView(R.id.rl_back_1)
    ImageView rlBack1;
    @BindView(R.id.rl_back_2)
    ImageView rlBack2;
    @BindView(R.id.rl_back_3)
    ImageView rlBack3;
    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.downloadMonitor)
    TextView downloadMonitor;
    @BindView(R.id.downloadDisater)
    TextView downloadDisater;
    @BindView(R.id.downloadMonDate)
    TextView downloadMonDate;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mContext = this;
        MyApplication.getActivities().add(this);
        initView();
    }

    private void initView() {
        userName.setText((String) SharedUtils.getShare(this, "loginname", ""));
        userLevel.setText((String) SharedUtils.getShare(this, "loginlevel", ""));
    }

    private void checkUpdate() {
        OkHttpUtils.get().url("http://202.98.195.125:8082/gzcmdback/findNewVersionNumber.do")
                .addParams("version", AppUtils.getVerCode(mContext))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        message.setText("获取最新版本失败，请稍后重试");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String aStatic = object.optString("static");
                            if ("1".equals(aStatic)) {
                                message.setText("正在后台下载最新APK");
                                Intent service = new Intent(mContext, UpdataService.class);
                                startService(service);
                            } else {
                                message.setText("当前已经是最新版本");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @OnClick({R.id.download, R.id.changePassword, R.id.downloadMap,
            R.id.downloadApp, R.id.changeSure, R.id.downloadMonDate,
            R.id.changeStop, R.id.rl_back_1, R.id.rl_back_2, R.id.logOut,
            R.id.rl_back_3, R.id.downloadMonitor, R.id.downloadDisater})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.download:
                userMessage.setVisibility(View.GONE);
                llDownload.setVisibility(View.VISIBLE);
                break;
            case R.id.changePassword:
                userMessage.setVisibility(View.GONE);
                llChangePassword.setVisibility(View.VISIBLE);
                break;
            case R.id.downloadMap:
                break;
            case R.id.downloadApp:
                checkUpdate();
                break;
            case R.id.changeSure:
                isNotNull();
                break;
            case R.id.changeStop:
                clearAll();
                userMessage.setVisibility(View.VISIBLE);
                llChangePassword.setVisibility(View.GONE);
                break;
            case R.id.rl_back_1:
                finish();
                break;
            case R.id.rl_back_2:
                userMessage.setVisibility(View.VISIBLE);
                llDownload.setVisibility(View.GONE);
                break;
            case R.id.rl_back_3:
                userMessage.setVisibility(View.VISIBLE);
                llChangePassword.setVisibility(View.GONE);
                break;
            case R.id.downloadMonitor:
                break;
            case R.id.downloadDisater:
                break;
            case R.id.downloadMonDate:
                break;
            case R.id.logOut:
//                GreenDaoManager.deleteArea();
                GreenDaoManager.deleteDisaster();
                SharedUtils.removeShare(mContext, Constant.SAVE_DIS_TIME);
                SharedUtils.removeShare(mContext, Constant.SAVE_MON_TIME);
                SharedUtils.removeShare(mContext,"isLogin");

                PushServiceFactory.getCloudPushService().turnOffPushChannel(new CommonCallback() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.d(TAG, "推送通道关闭成功！");
                    }

                    @Override
                    public void onFailed(String s, String s1) {
                        LogUtils.d(TAG, "推送通道关闭失败！");

                    }
                });
                getApplicationContext().stopService(new Intent(SettingActivity.this, ReplaceService.class));
//                getApplicationContext().unbindService(serviceConnection);
                for (Activity activity : MyApplication.getActivities()) {
                    if (!activity.isFinishing()) {
                        activity.finish();
                    }
                }
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void clearAll() {
        etUserName.setText("");
        etPassword.setText("");
        etNewPassword.setText("");
        etNewPassword1.setText("");
    }

    private void isNotNull() {
        if (TextUtils.isEmpty(etUserName.getText().toString())) {
            etUserName.setError("账户名不能为空");
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("原密码不能为空");
        }
        if (TextUtils.isEmpty(etNewPassword.getText().toString())) {
            etNewPassword.setError("新密码不能为空");
        }
        if (TextUtils.isEmpty(etNewPassword1.getText().toString())) {
            etNewPassword1.setError("再次输入新密码不能为空");
        } else {
            if (!etNewPassword.getText().toString().equals(etNewPassword1.getText().toString())) {
                ToastUtils.showLong(mContext, "两次新密码输入不相同");
            } else {
            }
        }


    }
}
