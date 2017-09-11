package com.nandi.disastermanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.nandi.disastermanager.utils.SharedUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

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
        userName.setText((String)SharedUtils.getShare(mContext,"loginname",""));
        initEvent();
    }

    private void initEvent(){

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = userName.getText().toString();
                pswd = userPwd.getText().toString();

                if (TextUtils.isEmpty(name)){
                    initSnackbar(userName, "账号不能为空!");
                } else if (TextUtils.isEmpty(pswd)){
                    initSnackbar(userPwd, "密码不能为空!");
                } else {
                    loginPost(name,pswd);
                }

            }
        });
    }

    /**
     * 登录请求
     */
    private void loginPost(String userNumber,String password){
            WaitingDialog.createLoadingDialog(mContext,"正在登录。。。");
            OkHttpUtils.get().url("http://192.168.10.73:8080/gzcmd/appdocking/login/"+userNumber+"/"+password)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            WaitingDialog.closeDialog();
                            Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(final String response, int id) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    GreenDaoManager.deleteAll();
                                    saveArea(response);
                                    SharedUtils.putShare(mContext,"loginname",name);
                                    SharedUtils.putShare(mContext,"loginpswd",pswd);
                                    Intent intent=new Intent(mContext,MainActivity.class);
                                    startActivity(intent);
                                    WaitingDialog.closeDialog();
                                }
                            }).start();

                        }
                    });

    }

    private void saveArea(String response) {
        Gson gson = new Gson();
        loginInfo=gson.fromJson(response, LoginInfo.class);
        SharedUtils.putShare(mContext,"loginlevel",loginInfo.getData().get(0).getLevel()+"");
        if (loginInfo.getMeta().isSuccess()==false){
            Toast.makeText(mContext,loginInfo.getMeta().getMessage() , Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i=0;i<loginInfo.getData().get(2).getCitymap().size();i++) {
            AreaInfo areaInfo=new AreaInfo(null,
                    loginInfo.getData().get(2).getCitymap().get(i).getLevel(),
                    loginInfo.getData().get(2).getCitymap().get(i).getArea_name(),
                    loginInfo.getData().get(2).getCitymap().get(i).getArea_code(),
                    loginInfo.getData().get(2).getCitymap().get(i).getArea_parent(),
                    loginInfo.getData().get(2).getCitymap().get(i).getId());
            GreenDaoManager.insertArea(areaInfo);
        }
        for (int i=0;i<loginInfo.getData().get(3).getCountymap().size();i++) {
            AreaInfo areaInfo=new AreaInfo(null,
                    loginInfo.getData().get(3).getCountymap().get(i).getLevel(),
                    loginInfo.getData().get(3).getCountymap().get(i).getArea_name(),
                    loginInfo.getData().get(3).getCountymap().get(i).getArea_code(),
                    loginInfo.getData().get(3).getCountymap().get(i).getArea_parent(),
                    loginInfo.getData().get(3).getCountymap().get(i).getId());
            GreenDaoManager.insertArea(areaInfo);
        }
        for (int i=0;i<loginInfo.getData().get(4).getTownmap().size();i++) {
            AreaInfo areaInfo=new AreaInfo(null,
                    loginInfo.getData().get(4).getTownmap().get(i).getLevel(),
                    loginInfo.getData().get(4).getTownmap().get(i).getArea_name(),
                    loginInfo.getData().get(4).getTownmap().get(i).getId(),
                    loginInfo.getData().get(4).getTownmap().get(i).getArea_parent(),
                    loginInfo.getData().get(4).getTownmap().get(i).getId());
            GreenDaoManager.insertArea(areaInfo);
        }
        int id = -1;
        if (1==loginInfo.getData().get(0).getLevel()){
            id=loginInfo.getData().get(1).getProvince().get(0).getId();
        }else if(2==loginInfo.getData().get(0).getLevel()){
            id=loginInfo.getData().get(2).getCitymap().get(0).getId();
        }else if(3==loginInfo.getData().get(0).getLevel()){
            id=loginInfo.getData().get(3).getCountymap().get(0).getId();
        }else if(4==loginInfo.getData().get(0).getLevel()){
            id=loginInfo.getData().get(4).getCountymap().get(0).getId();
        }
        loginDisaster(id+"",loginInfo.getData().get(0).getLevel()+"");
    }

    /**
     * 请求所有灾害点
     */
    private void loginDisaster(String id,String level){
        RequestCall build = OkHttpUtils.get().url("http://192.168.10.73:8080/gzcmd/appdocking/listDisaster/" + id + "/" + level)
                .build();
        build.execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        WaitingDialog.closeDialog();
                        Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(final String response, int id) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson=new Gson();
                                DisasterData disasterData=gson.fromJson(response, DisasterData.class);
                                for (DisasterData.DataBean dataBean:disasterData.getData()) {
                                    String type="";
                                    switch (dataBean.getZhzl()){
                                        case "01":type="滑坡";break;
                                        case "02":type="地面塌陷"; break;
                                        case "03":type="泥石流";break;
                                        case "04":break;
                                        case "05":type="地裂缝";break;
                                        case "06":type="不稳定斜坡";break;
                                        case "07":type="崩塌";break;
                                    }
                                    DisasterPoint disasterPoint=new DisasterPoint(null,
                                            dataBean.getDzbh(),
                                            dataBean.getJd()+"",
                                            dataBean.getWd()+"",
                                            dataBean.getCity(),
                                            null,
                                            dataBean.getCounty(),
                                            dataBean.getTown(),
                                            dataBean.getXqdj()+"",
                                            type,
                                            dataBean.getYfys(),
                                            dataBean.getDzmc(),
                                            dataBean.getLATITUDE()+"",
                                            dataBean.getLONGITUDE()+"");
                                    GreenDaoManager.insertDisasterPoint(disasterPoint);
                                }


                            }
                        }).start();

                    }
                });

    }


    /**
     * Snackbar提示栏
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
