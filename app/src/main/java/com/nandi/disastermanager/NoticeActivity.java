package com.nandi.disastermanager;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nandi.disastermanager.entity.NoticeInfo;
import com.nandi.disastermanager.ui.WaitingDialog;
import com.nandi.disastermanager.utils.Constant;
import com.nandi.disastermanager.utils.SharedUtils;
import com.nandi.disastermanager.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @author qingsong
 * 公告
 */public class NoticeActivity extends Activity {

    @BindView(R.id.date_show)
    RecyclerView dateShow;
    @BindView(R.id.notice_title)
    TextView noticeTitle;
    @BindView(R.id.notice_time)
    TextView noticeTime;
    private NoticeAdapter noticeAdapter;
    private NoticeInfo noticeInfo;
    private Context mContext;
    private String name;
    private String password;
    private String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);
        ButterKnife.bind(this);
        MyApplication.getActivities().add(this);
        mContext = this;
        name = (String) SharedUtils.getShare(this, Constant.USER_NAME, "");
        password = (String) SharedUtils.getShare(this, Constant.PASSWORD, "");
        level = (String) SharedUtils.getShare(this, Constant.LEVEL, "");
        loginPost();
    }

    /**
     * 登录请求
     */

    private void loginPost() {
        WaitingDialog.createLoadingDialog(mContext, "正在获取公告信息");
        OkHttpUtils.get().url("http://192.168.10.195:8080/gzcmd/appdocking/login/" + name + "/" + password + "/2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showShort(mContext, "获取权限失败");
                    }

                    @Override
                    public void onResponse(final String response, int id) {
                        downloadNotice();
                    }
                });

    }

    private void downloadNotice() {
        OkHttpUtils.get().url("http://192.168.10.195:8080/gzcmd/appdocking/listTabNotice/" + level)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Toast.makeText(mContext, "Notice请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        noticeInfo = gson.fromJson(response, NoticeInfo.class);
                        setAdapter();
                        WaitingDialog.closeDialog();
                    }
                });

    }

    private void setAdapter() {
        dateShow.setLayoutManager(new LinearLayoutManager(mContext));
        noticeAdapter = new NoticeAdapter(mContext, noticeInfo);
        noticeAdapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                noticeTime.setText(noticeInfo.getData().get(position).getAnn_time());
                noticeTitle.setText("\u3000\u3000" + noticeInfo.getData().get(position).getContent());
            }
        });
        dateShow.setAdapter(noticeAdapter);
    }

}
