package com.nandi.disastermanager.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nandi.disastermanager.LoginActivity;
import com.nandi.disastermanager.MainActivity;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.utils.SharedUtils;

public class WelconmeActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welconme);
        context=this;
        clearShared();
        boolean isLogin = (boolean) SharedUtils.getShare(context, "isLogin", false);
        Handler handler = new Handler();
        if (isLogin) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(context, MainActivity.class));
                    finish();
                }
            }, 2000);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(context, LoginActivity.class));
                    finish();
                }
            }, 2000);
        }
    }
    private void clearShared() {
        int currentCode= Integer.parseInt(MainActivity.getVerCode(context));
        int oldCode= (int) SharedUtils.getShare(context,"versionCode",0);
        if (currentCode>oldCode){
            SharedUtils.clearShare(context);
        }
    }
}
