package com.nandi.disastermanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.blankj.utilcode.util.NetworkUtils;
import com.google.gson.Gson;
import com.nandi.disastermanager.dao.GreenDaoManager;
import com.nandi.disastermanager.http.ReplaceService;
import com.nandi.disastermanager.http.UpdataService;
import com.nandi.disastermanager.search.entity.DisasterData;
import com.nandi.disastermanager.search.entity.DisasterPoint;
import com.nandi.disastermanager.search.entity.MonitorData;
import com.nandi.disastermanager.search.entity.MonitorListData;
import com.nandi.disastermanager.search.entity.MonitorListPoint;
import com.nandi.disastermanager.search.entity.MonitorPoint;
import com.nandi.disastermanager.utils.AppUtils;
import com.nandi.disastermanager.utils.Constant;
import com.nandi.disastermanager.utils.InputUtil;
import com.nandi.disastermanager.utils.SharedUtils;
import com.nandi.disastermanager.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author qingsong
 *         个人信息页面
 */

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
    @BindView(R.id.toggle_btn)
    ToggleButton toggleBtn;
    @BindView(R.id.toggle_edt)
    EditText toggleEdt;
    @BindView(R.id.showeyes_btn1)
    ImageView showeyesBtn1;
    @BindView(R.id.showeyes_btn2)
    ImageView showeyesBtn2;
    @BindView(R.id.showeyes_btn3)
    ImageView showeyesBtn3;

    private Context mContext;
    private String id;
    private String level;
    private String password;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mContext = this;
        MyApplication.getActivities().add(this);
        initView();
    }

    /*初始化数据*/
    private void initView() {
        name = (String) SharedUtils.getShare(this, Constant.USER_NAME, "");
        id = (String) SharedUtils.getShare(this, Constant.AREA_ID, "");
        level = (String) SharedUtils.getShare(this, Constant.LEVEL, "");
        userLevel.setText(GreenDaoManager.queryAreaLevel(Integer.parseInt(level)).get(0).getName());
        password = (String) SharedUtils.getShare(this, Constant.PASSWORD, "");
        userName.setText(name);

        etUserName.setText(name);
        etUserName.setEnabled(false);
            /*设置初始化网络选择*/
        if ((Boolean) SharedUtils.getShare(mContext, Constant.isOpenGPRS, true)) {
            toggleBtn.setChecked(true);
        } else {
            toggleBtn.setChecked(false);
        }
        setListener();


    }

    private void setListener() {
        toggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    Log.i(TAG, "选择允许");
                    SharedUtils.putShare(mContext, Constant.isOpenGPRS, true);
                } else {
                    Log.i(TAG, "选择不允许");
                    SharedUtils.putShare(mContext, Constant.isOpenGPRS, false);
                }
            }
        });
        loginPost();
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && showeyesBtn1.getVisibility() == View.GONE) {
                    showeyesBtn1.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    showeyesBtn1.setVisibility(View.GONE);
                }
            }
        });
        etNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && showeyesBtn2.getVisibility() == View.GONE) {
                    showeyesBtn2.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    showeyesBtn2.setVisibility(View.GONE);
                }
            }
        });
        etNewPassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && showeyesBtn3.getVisibility() == View.GONE) {
                    showeyesBtn3.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    showeyesBtn3.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (InputUtil.isShouldHideInput(v, ev)) {
                InputUtil.hideSoftInput(v.getWindowToken(), mContext);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /*登录请求*/
    private void loginPost() {
        OkHttpUtils.get().url(getString(R.string.base_gz_url) + "/appdocking/login/" + name + "/" + password + "/2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(final String response, int id) {
                    }
                });

    }

    /*APP更新*/
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

    /*APP更新灾害点数据*/
    private void upDisData() {
        message.setText("正在更新灾害点信息");
        RequestCall build = OkHttpUtils.get().url(getString(R.string.base_gz_url) + "/appdocking/listDisaster/" + id + "/" + level)
                .build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                upDisData();
            }

            @Override
            public void onResponse(final String response, int id) {

                SharedUtils.putShare(mContext, Constant.SAVE_DIS_TIME, new Date().getTime());
                GreenDaoManager.deleteDisaster();
                Gson gson = new Gson();
                try {
                    final DisasterData disasterData = gson.fromJson(response, DisasterData.class);
                    new Thread() {
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
                    message.setText("更新灾害点信息成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /*APP更新监测点列表*/
    private void upMonData() {
        message.setText("正在更新监测点信息");
        RequestCall build = OkHttpUtils.get().url(getString(R.string.base_gz_url) + "/appdocking/listMonitorOrigin/" + id + "/" + level)
                .build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                upMonData();
            }

            @Override
            public void onResponse(final String response, int id) {
                SharedUtils.putShare(mContext, Constant.SAVE_MON_TIME, new Date().getTime());
                GreenDaoManager.deleteAllMonitor();
                Gson gson = new Gson();
                try {
                    final MonitorListData monitorListData = gson.fromJson(response, MonitorListData.class);
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
                    message.setText("更新灾害点信息成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /*APP更新监测点数据*/
    private void upMonDatas() {
        message.setText("正在更新灾害点数据信息");
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
                SharedUtils.putShare(mContext, Constant.SAVE_MONDATA_TIME, new Date().getTime());
                Log.i("qingsong", response);
                System.out.println(response);
                GreenDaoManager.deleteAllMonitorData();
                Gson gson = new Gson();
                try {
                    final MonitorData monitorData = gson.fromJson(response, MonitorData.class);
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
                    message.setText("更新灾害点数据信息成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @OnClick({R.id.download, R.id.changePassword, R.id.downloadMap,
            R.id.downloadApp, R.id.changeSure, R.id.downloadMonDate,
            R.id.changeStop, R.id.rl_back_1, R.id.rl_back_2, R.id.logOut,
            R.id.rl_back_3, R.id.downloadMonitor, R.id.downloadDisater,
            R.id.showeyes_btn1, R.id.showeyes_btn2, R.id.showeyes_btn3})
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
                if ((boolean) SharedUtils.getShare(mContext, Constant.isOpenGPRS, true)) {
                    checkUpdate();
                } else {
                    if (NetworkUtils.isWifiConnected()) {
                        checkUpdate();
                        Log.i(TAG, "不允许4G时更新");
                    } else {
                        message.setText("当前不是WIFI状态不能更新");
                    }
                }
                break;
            case R.id.changeSure:
                String nameStr = etUserName.getText().toString().trim();
                String passwordStr = etPassword.getText().toString().trim();
                String newPasswordStr = etNewPassword.getText().toString().trim();
                if (isNotNull()) {
                    changeRquest(nameStr, passwordStr, newPasswordStr);
                }
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
                clearAll();
                userMessage.setVisibility(View.VISIBLE);
                llDownload.setVisibility(View.GONE);
                break;
            case R.id.rl_back_3:
                userMessage.setVisibility(View.VISIBLE);
                llChangePassword.setVisibility(View.GONE);
                break;
            case R.id.downloadMonitor:
                if ((boolean) SharedUtils.getShare(mContext, Constant.isOpenGPRS, true)) {
                    upMonData();
                } else {
                    if (NetworkUtils.isWifiConnected()) {
                        upMonData();
                        Log.i(TAG, "不允许4G时更新");
                    } else {
                        message.setText("当前不是WIFI状态不能更新");
                    }
                }

                break;
            case R.id.downloadDisater:
                if ((boolean) SharedUtils.getShare(mContext, Constant.isOpenGPRS, true)) {
                    upDisData();
                } else {
                    if (NetworkUtils.isWifiConnected()) {
                        upDisData();
                        Log.i(TAG, "不允许4G时更新");
                    } else {
                        message.setText("当前不是WIFI状态不能更新");
                    }
                }

                break;
            case R.id.downloadMonDate:
                if ((boolean) SharedUtils.getShare(mContext, Constant.isOpenGPRS, true)) {
                    upMonDatas();
                } else {
                    if (NetworkUtils.isWifiConnected()) {
                        upMonDatas();
                        Log.i(TAG, "不允许4G时更新");
                    } else {
                        message.setText("当前不是WIFI状态不能更新");
                    }
                }
                break;
            case R.id.logOut:
                outData();
                break;
            case R.id.showeyes_btn1:
                if (etPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showeyesBtn1.setImageResource(R.mipmap.ic_eye);
                } else {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showeyesBtn1.setImageResource(R.mipmap.ic_uneye);
                }
                break;
            case R.id.showeyes_btn2:
                if (etNewPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    etNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showeyesBtn2.setImageResource(R.mipmap.ic_eye);
                } else {
                    etNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showeyesBtn2.setImageResource(R.mipmap.ic_uneye);
                }
                break;
            case R.id.et_new_password1:
                break;
            case R.id.showeyes_btn3:
                if (etNewPassword1.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    etNewPassword1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showeyesBtn3.setImageResource(R.mipmap.ic_eye);
                } else {
                    etNewPassword1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showeyesBtn3.setImageResource(R.mipmap.ic_uneye);
                }
                break;
        }
    }

    /*APP注销*/
    private void outData() {
        GreenDaoManager.deleteDisaster();
        SharedUtils.removeShare(mContext, Constant.SAVE_DIS_TIME);
        SharedUtils.removeShare(mContext, Constant.SAVE_MON_TIME);
        SharedUtils.removeShare(mContext, Constant.SAVE_MONDATA_TIME);
        SharedUtils.removeShare(mContext, Constant.IS_LOGIN);

//                PushServiceFactory.getCloudPushService().turnOffPushChannel(new CommonCallback() {
//                    @Override
//                    public void onSuccess(String s) {
//                        LogUtils.d(TAG, "推送通道关闭成功！");
//                    }
//
//                    @Override
//                    public void onFailed(String s, String s1) {
//                        LogUtils.d(TAG, "推送通道关闭失败！");
//
//                    }
//                });
        getApplicationContext().stopService(new Intent(SettingActivity.this, ReplaceService.class));
        for (Activity activity : MyApplication.getActivities()) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        Intent intent1 = new Intent(this, LoginActivity.class);
        startActivity(intent1);
    }

    /*修改密码*/
    private void changeRquest(String nameStr, String passwordStr, String newPasswordStr) {
        OkHttpUtils.get().url(getString(R.string.base_gz_url) + "appdocking/updateAppUser/" + nameStr + "/" + passwordStr + "/" + newPasswordStr)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(mContext, "修改密码失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject j = new JSONObject(response);
                    String meta = j.optString("meta");
                    JSONObject jsonObject = new JSONObject(meta);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        ToastUtils.showShort(mContext, "密码修改成功");
                        SharedUtils.removeShare(mContext, Constant.IS_LOGIN);
                        getApplicationContext().stopService(new Intent(SettingActivity.this, ReplaceService.class));
                        for (Activity activity : MyApplication.getActivities()) {
                            if (!activity.isFinishing()) {
                                activity.finish();
                            }
                        }
                        Intent intent1 = new Intent(mContext, LoginActivity.class);
                        startActivity(intent1);
                    } else {
                        ToastUtils.showShort(mContext, "请输入正确的密码");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*清空修改密码页面*/
    private void clearAll() {
        etUserName.setText("");
        etPassword.setText("");
        etNewPassword.setText("");
        etNewPassword1.setText("");
    }

    /*修改密码状态*/
    private boolean isNotNull() {

        if (TextUtils.isEmpty(this.etUserName.getText().toString())) {
            this.etUserName.setError("账户名不能为空");
            return false;
        } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("原密码不能为空");
            return false;
        } else if (TextUtils.isEmpty(etNewPassword.getText().toString())) {
            etNewPassword.setError("新密码不能为空");
            return false;
        } else if (TextUtils.isEmpty(etNewPassword1.getText().toString())) {
            etNewPassword1.setError("再次输入新密码不能为空");
            return false;
        } else {
            if (!etNewPassword.getText().toString().equals(etNewPassword1.getText().toString())) {
                ToastUtils.showLong(mContext, "两次新密码输入不相同");
                return false;
            } else {
                return true;
            }
        }

    }


}
