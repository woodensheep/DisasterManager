package com.nandi.disastermanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.google.gson.Gson;
import com.nandi.disastermanager.dao.GreenDaoManager;
import com.nandi.disastermanager.http.DownloadMapService;
import com.nandi.disastermanager.http.ReplaceService;
import com.nandi.disastermanager.search.entity.DisasterData;
import com.nandi.disastermanager.search.entity.DisasterPoint;
import com.nandi.disastermanager.search.entity.GTSLocation;
import com.nandi.disastermanager.search.entity.GTSLocationPoint;
import com.nandi.disastermanager.search.entity.MonitorData;
import com.nandi.disastermanager.search.entity.MonitorListData;
import com.nandi.disastermanager.search.entity.MonitorListPoint;
import com.nandi.disastermanager.search.entity.MonitorPoint;
import com.nandi.disastermanager.ui.WaitingDialog;
import com.nandi.disastermanager.utils.AppUtils;
import com.nandi.disastermanager.utils.Constant;
import com.nandi.disastermanager.utils.DownloadUtils;
import com.nandi.disastermanager.utils.InputUtil;
import com.nandi.disastermanager.utils.SharedUtils;
import com.nandi.disastermanager.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
    @BindView(R.id.downloadDisater)
    TextView downloadDisater;
    @BindView(R.id.toggle_btn)
    ToggleButton toggleBtn;
    @BindView(R.id.showeyes_btn1)
    ImageView showeyesBtn1;
    @BindView(R.id.showeyes_btn2)
    ImageView showeyesBtn2;
    @BindView(R.id.showeyes_btn3)
    ImageView showeyesBtn3;
    @BindView(R.id.downloadMonitor)
    TextView downloadMonitor;
    @BindView(R.id.downloadMonDate)
    TextView downloadMonDate;
    @BindView(R.id.downloadLocation)
    TextView downloadLocation;

    private Context mContext;
    private String id;
    private String level;
    private String password;
    private String name;
    private boolean openGprs;

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
        openGprs = (boolean) SharedUtils.getShare(this, Constant.isOpenGPRS, false);
        name = (String) SharedUtils.getShare(this, Constant.USER_NAME, "");
        id = (String) SharedUtils.getShare(this, Constant.AREA_ID, "");
        level = (String) SharedUtils.getShare(this, Constant.LEVEL, "");
        userLevel.setText(GreenDaoManager.queryAreaLevel(Integer.parseInt(level)).get(0).getName());
        password = (String) SharedUtils.getShare(this, Constant.PASSWORD, "");
        userName.setText(name);
        etUserName.setText(name);
        etUserName.setEnabled(false);
            /*设置初始化网络选择*/
        if (openGprs) {
            toggleBtn.setChecked(true);
        } else {
            toggleBtn.setChecked(false);
        }
        setListener();
    }

    private void setListener() {
        toggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.i(TAG, "选择允许");
                    SharedUtils.putShare(mContext, Constant.isOpenGPRS, true);
                } else {
                    Log.i(TAG, "选择不允许");
                    SharedUtils.putShare(mContext, Constant.isOpenGPRS, false);
                }
            }
        });

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
    private void loginPost(final int ids) {
        OkHttpUtils.get().url(getString(R.string.base_gz_url) + "/appdocking/login/" + name + "/" + password + "/2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(final String response, int id) {
                        if (ids == 1) {
                            startService(new Intent(mContext, DownloadMapService.class));
                            ToastUtils.showShort(mContext, "开始下载地图包");
                        }
                        if (ids == 2) {
                            checkUpdate();
                        }
                        if (ids == 3) {
                            upDisData();
                        }
                        if (ids == 4) {
                            upMonData();
                        }
                        if (ids == 5) {
                            upMonDatas();
                        }
                        if (ids == 6) {
                            upLocation();
                        }
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
                        ToastUtils.showShort(mContext, "获取最新版本失败，请稍后重试");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String aStatic = object.optString("static");
                            String remark = object.optString("remark");
                            if ("1".equals(aStatic)) {

                                ToastUtils.showShort(mContext, remark);
                                new DownloadUtils(mContext).downloadAPK("http://202.98.195.125:8082/gzcmdback/downloadApk.do", "app-release.apk");
                            } else {
                                ToastUtils.showShort(mContext, "当前已经是最新版本");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /*APP更新灾害点数据*/
    private void upDisData() {
        WaitingDialog.createLoadingDialog(mContext, "正在下载...");
        RequestCall build = OkHttpUtils.get().url(getString(R.string.base_gz_url) + "/appdocking/listDisasterPoint/" + id + "/" + level)
                .build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(mContext, "灾害点信息下载失败");
                WaitingDialog.closeDialog();
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
                    ToastUtils.showShort(mContext, "更新灾害点信息成功");
                    WaitingDialog.closeDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /*APP更新监测点列表*/
    private void upMonData() {
        WaitingDialog.createLoadingDialog(mContext, "正在下载...");
        RequestCall build = OkHttpUtils.get().url(getString(R.string.base_gz_url) + "/appdocking/listMonitorOrigin/" + id + "/" + level)
                .build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(mContext, "监测列表下载失败");
                WaitingDialog.closeDialog();
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
                    ToastUtils.showShort(mContext, "更新监测点信息成功");
                    WaitingDialog.closeDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /*APP更新监测点数据*/
    private void upMonDatas() {
        WaitingDialog.createLoadingDialog(mContext, "正在下载...");
        String url = getString(R.string.base_gz_url) + "/appdocking/listMonitor/" + id + "/" + level;
        RequestCall build = OkHttpUtils.get().url(url)
                .build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(mContext, "监测数据下载失败");
                WaitingDialog.closeDialog();
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
                    ToastUtils.showShort(mContext, "更新监测点数据信息成功");
                    WaitingDialog.closeDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @OnClick({R.id.download, R.id.changePassword, R.id.downloadMap,
            R.id.downloadApp, R.id.changeSure, R.id.downloadMonDate,
            R.id.changeStop, R.id.rl_back_1, R.id.rl_back_2, R.id.logOut,
            R.id.rl_back_3, R.id.downloadDisater, R.id.downloadMonitor,
            R.id.downloadLocation, R.id.showeyes_btn1, R.id.showeyes_btn2,
            R.id.showeyes_btn3})
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
                File file = new File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        , "guizhou.tpk");
                if (!file.exists()) {
                    if (NetworkUtils.isConnected()) {
                        if (NetworkUtils.isWifiConnected()) {
                            loginPost(1);

                        } else {
                            if (openGprs) {
                                loginPost(1);
                            } else {
                                ToastUtils.showShort(mContext, "请打开允许4G开关");
                            }
                        }
                    } else {
                        ToastUtils.showShort(mContext, "没有网络请稍后");
                    }
                } else {
                    ToastUtils.showShort(mContext, "地图文件已存在");
                }
                break;
            case R.id.downloadApp:
                if (NetworkUtils.isConnected()) {
                    if (NetworkUtils.isWifiConnected()) {
                        loginPost(2);
                    } else {
                        if (openGprs) {
                            loginPost(2);
                        } else {
                            ToastUtils.showShort(mContext, "请打开允许4G开关");
                        }
                    }
                } else {
                    ToastUtils.showShort(mContext, "没有网络请稍后");
                }
                break;
            case R.id.downloadDisater:
                if (NetworkUtils.isConnected()) {
                    if (NetworkUtils.isWifiConnected()) {
                        loginPost(3);
                    } else {
                        if (openGprs) {
                            loginPost(3);
                        } else {
                            ToastUtils.showShort(mContext, "请打开允许4G开关");
                        }
                    }
                } else {
                    ToastUtils.showShort(mContext, "没有网络请稍后");
                }
                break;
            case R.id.downloadMonitor:
                if (NetworkUtils.isConnected()) {
                    if (NetworkUtils.isWifiConnected()) {
                        loginPost(4);
                    } else {
                        if (openGprs) {
                            loginPost(4);
                        } else {
                            ToastUtils.showShort(mContext, "请打开允许4G开关");
                        }
                    }
                } else {
                    ToastUtils.showShort(mContext, "没有网络请稍后");
                }

                break;
            case R.id.downloadMonDate:
                if (NetworkUtils.isConnected()) {
                    if (NetworkUtils.isWifiConnected()) {
                        loginPost(5);
                    } else {
                        if (openGprs) {
                            loginPost(5);
                        } else {
                            ToastUtils.showShort(mContext, "请打开允许4G开关");
                        }
                    }
                } else {
                    ToastUtils.showShort(mContext, "没有网络请稍后");
                }

                break;
            case R.id.downloadLocation:
                if (NetworkUtils.isConnected()) {
                    if (NetworkUtils.isWifiConnected()) {
                        loginPost(6);
                    } else {
                        if (openGprs) {
                            loginPost(6);
                        } else {
                            ToastUtils.showShort(mContext, "请打开允许4G开关");
                        }
                    }
                } else {
                    ToastUtils.showShort(mContext, "没有网络请稍后");
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
                    ToastUtils.showLong(mContext, "");
                }
                break;
        }
    }

    private void upLocation() {
        WaitingDialog.createLoadingDialog(mContext, "正在下载...");
        String url = getString(R.string.base_gz_url) + "appdocking/listLandJdWd";
        RequestCall build = OkHttpUtils.get().url(url)
                .build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(mContext, "坐标信息下载失败");
                WaitingDialog.closeDialog();
            }

            @Override
            public void onResponse(final String response, int id) {
                SharedUtils.putShare(mContext, Constant.SAVE_DIS_TIME, new Date().getTime());
                Log.i("qingsong", response);
                System.out.println(response);
                GreenDaoManager.deleteGTSLocation();
                Gson gson = new Gson();
                try {
                    final GTSLocation gtsLocation = gson.fromJson(response, GTSLocation.class);
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
                    ToastUtils.showShort(mContext, "更新坐标信息成功");
                    WaitingDialog.closeDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /*APP注销*/
    private void outData() {
        GreenDaoManager.deleteDisaster();
        GreenDaoManager.deleteAllPhoto();
        GreenDaoManager.deleteAllLocation();
        GreenDaoManager.deleteAllMonitor();
        GreenDaoManager.deleteAllMonitorData();
        SharedUtils.removeShare(mContext, Constant.SAVE_DIS_TIME);
        SharedUtils.removeShare(mContext, Constant.SAVE_MON_TIME);
        SharedUtils.removeShare(mContext, Constant.SAVE_MONDATA_TIME);
        SharedUtils.removeShare(mContext, Constant.IS_LOGIN);
        SharedUtils.removeShare(mContext, Constant.SAVE_NOTE);
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
