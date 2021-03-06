package com.nandi.disastermanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nandi.disastermanager.dao.GreenDaoManager;
import com.nandi.disastermanager.search.entity.AreaInfo;
import com.nandi.disastermanager.search.entity.DisasterData;
import com.nandi.disastermanager.search.entity.DisasterPoint;
import com.nandi.disastermanager.search.entity.LoginInfo;
import com.nandi.disastermanager.ui.WaitingDialog;
import com.nandi.disastermanager.utils.AppUtils;
import com.nandi.disastermanager.utils.Constant;
import com.nandi.disastermanager.utils.InputUtil;
import com.nandi.disastermanager.utils.PermissionUtils;
import com.nandi.disastermanager.utils.SharedUtils;
import com.nandi.disastermanager.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 登录界面
 * Created by baohongyan on 2017/9/8.
 */

public class LoginActivity extends AppCompatActivity {

    private Context mContext;

    @BindView(R.id.loginName)
    EditText userName;
    @BindView(R.id.loginPwd)
    EditText userPwd;
    @BindView(R.id.loginBtn)
    Button loginBtn;

    private String name;
    private String pswd;
    private LoginInfo loginInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = this;
        PermissionUtils.requestMultiPermissions(LoginActivity.this, mPermissionGrant);
        userName.setText((String) SharedUtils.getShare(mContext, Constant.USER_NAME, ""));
        initEvent();
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_CAMERA:
                    break;
                case PermissionUtils.CODE_RECORD_AUDIO:
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
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

    private void initEvent() {

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = userName.getText().toString();
                pswd = userPwd.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    initSnackbar(userName, "账号不能为空!");
                } else if (TextUtils.isEmpty(pswd)) {
                    initSnackbar(userPwd, "密码不能为空!");
                } else {
                    loginPost(name, pswd, checkAreaData(name));
                }

            }
        });
    }

    private String checkAreaData(String name) {
        String s = "";
        String loginname = (String) SharedUtils.getShare(mContext, Constant.USER_NAME, "");
        if (name.equals(loginname)) {
            s = "2";
            SharedUtils.putShare(mContext,Constant.CHANGE_USER,true);
        } else {
            s = "1";
            SharedUtils.putShare(mContext,Constant.CHANGE_USER,false);
        }
        return s;
    }

    /**
     * 登录请求
     */
    private void loginPost(String userNumber, String password, String s) {
        WaitingDialog.createLoadingDialog(mContext, "正在登录...");
        OkHttpUtils.get().url(getString(R.string.base_gz_url) + "/appdocking/login/" + userNumber + "/" + password + "/" + s)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        WaitingDialog.closeDialog();
                        ToastUtils.showShort(mContext, "请求失败");
                    }

                    @Override
                    public void onResponse(final String response, int id) {
                        Gson gson = new Gson();
                        try {
                            loginInfo = gson.fromJson(response, LoginInfo.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                            WaitingDialog.closeDialog();
                            return;
                        }
                        if (!loginInfo.getMeta().isSuccess()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mContext, loginInfo.getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            WaitingDialog.closeDialog();
                            return;
                        }
                        if (loginInfo.getData().get(1).getProvince().size() != 0) {
                            saveArea();
                        } else {
                            ToastUtils.showShort(mContext, "登录成功！");
                            skipActivity();
                        }
                        SharedUtils.putShare(mContext, Constant.USER_NAME, name+"");
                        SharedUtils.putShare(mContext, Constant.PASSWORD, pswd+"");
                        SharedUtils.putShare(mContext,Constant.IS_LOGIN,true);
                        SharedUtils.putShare(mContext,Constant.VERSION_CODE,Integer.parseInt(AppUtils.getVerCode(mContext)));
                    }
                });

    }

    private void saveArea() {
        GreenDaoManager.deleteArea();
        int level = loginInfo.getData().get(0).getLevel();
        SharedUtils.putShare(mContext, Constant.LEVEL, level + "");
        int area_code = 0;
        switch (level) {
            case 1:
                area_code = loginInfo.getData().get(1).getProvince().get(0).getArea_code();
                break;
            case 2:
                area_code = loginInfo.getData().get(2).getCitymap().get(0).getArea_code();
                break;
            case 3:
                area_code = loginInfo.getData().get(3).getCountymap().get(0).getArea_code();
        }
        SharedUtils.putShare(mContext, "areacode", area_code + "");
        for (int i=0;i<loginInfo.getData().get(1).getProvince().size();i++){
            AreaInfo areaInfo = new AreaInfo(null,
                    loginInfo.getData().get(1).getProvince().get(i).getLevel(),
                    loginInfo.getData().get(1).getProvince().get(i).getArea_name(),
                    loginInfo.getData().get(1).getProvince().get(i).getArea_code(),
                    loginInfo.getData().get(1).getProvince().get(i).getArea_parent(),
                    loginInfo.getData().get(1).getProvince().get(i).getId());
            GreenDaoManager.insertArea(areaInfo);
        }
        for (int i = 0; i < loginInfo.getData().get(2).getCitymap().size(); i++) {
            AreaInfo areaInfo = new AreaInfo(null,
                    loginInfo.getData().get(2).getCitymap().get(i).getLevel(),
                    loginInfo.getData().get(2).getCitymap().get(i).getArea_name(),
                    loginInfo.getData().get(2).getCitymap().get(i).getArea_code(),
                    loginInfo.getData().get(2).getCitymap().get(i).getArea_parent(),
                    loginInfo.getData().get(2).getCitymap().get(i).getId());
            GreenDaoManager.insertArea(areaInfo);
        }
        for (int i = 0; i < loginInfo.getData().get(3).getCountymap().size(); i++) {
            AreaInfo areaInfo = new AreaInfo(null,
                    loginInfo.getData().get(3).getCountymap().get(i).getLevel(),
                    loginInfo.getData().get(3).getCountymap().get(i).getArea_name(),
                    loginInfo.getData().get(3).getCountymap().get(i).getArea_code(),
                    loginInfo.getData().get(3).getCountymap().get(i).getArea_parent(),
                    loginInfo.getData().get(3).getCountymap().get(i).getId());
            GreenDaoManager.insertArea(areaInfo);
        }
        for (int i = 0; i < loginInfo.getData().get(4).getTownmap().size(); i++) {
            AreaInfo areaInfo = new AreaInfo(null,
                    loginInfo.getData().get(4).getTownmap().get(i).getLevel(),
                    loginInfo.getData().get(4).getTownmap().get(i).getArea_name(),
                    loginInfo.getData().get(4).getTownmap().get(i).getId(),
                    loginInfo.getData().get(4).getTownmap().get(i).getArea_parent(),
                    loginInfo.getData().get(4).getTownmap().get(i).getId());
            GreenDaoManager.insertArea(areaInfo);
        }
        int id = -1;
        if (1 == loginInfo.getData().get(0).getLevel()) {
            id = loginInfo.getData().get(1).getProvince().get(0).getId();
        } else if (2 == loginInfo.getData().get(0).getLevel()) {
            id = loginInfo.getData().get(2).getCitymap().get(0).getId();
        } else if (3 == loginInfo.getData().get(0).getLevel()) {
            id = loginInfo.getData().get(3).getCountymap().get(0).getId();
        } else if (4 == loginInfo.getData().get(0).getLevel()) {
            id = loginInfo.getData().get(4).getCountymap().get(0).getId();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShort(mContext, "登录成功！");
            }
        });
        SharedUtils.putShare(mContext,Constant.AREA_ID,id+"");
        skipActivity();
    }

    private void skipActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        WaitingDialog.closeDialog();
        finish();
    }


    /**
     * Snackbar提示栏
     *
     * @param editText
     * @param msg
     */
    private void initSnackbar(EditText editText, String msg) {
        Snackbar.make(editText, msg, Snackbar.LENGTH_LONG).setAction("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }

}
