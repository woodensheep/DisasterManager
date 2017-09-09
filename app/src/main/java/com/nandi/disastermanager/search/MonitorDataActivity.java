package com.nandi.disastermanager.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.search.entity.DisasterPoint;
import com.nandi.disastermanager.search.entity.MonitorData;
import com.nandi.disastermanager.search.entity.MonitorListData;
import com.nandi.disastermanager.ui.WaitingDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MonitorDataActivity extends Activity {

    @BindView(R.id.date_show)
    RecyclerView dateShow;
    private MonitorAdapter monitorAdapter;
    /**
     * 数据
     **/
    private MonitorData mMonitorData;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_data);
        ButterKnife.bind(this);
        mContext = this;
        mMonitorData = new MonitorData();
        monitorListRequest();
    }

    private void monitorListRequest() {

        OkHttpUtils.get().url("http://192.168.10.195:8080/gzcmd/detection/findMonitorData/521421060058_01/1/10000")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        WaitingDialog.closeDialog();
                        Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        WaitingDialog.closeDialog();
                        Log.i("qingsong", response);
                        Gson gson = new Gson();
                        mMonitorData = gson.fromJson(response, MonitorData.class);
                        dateShow.setLayoutManager(new LinearLayoutManager(mContext));
                        monitorAdapter = new MonitorAdapter(mContext, mMonitorData);
                        dateShow.setAdapter(monitorAdapter);
                    }
                });

    }
}
