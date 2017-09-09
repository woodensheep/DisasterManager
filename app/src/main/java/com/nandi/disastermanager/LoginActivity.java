package com.nandi.disastermanager;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = this;
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
                    loginPost();
                }

            }
        });
    }

    /**
     * 登录请求
     */
    private void loginPost(){

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
