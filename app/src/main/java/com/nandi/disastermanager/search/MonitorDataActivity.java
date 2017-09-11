package com.nandi.disastermanager.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.search.entity.MonitorData;
import com.nandi.disastermanager.ui.WaitingDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class MonitorDataActivity extends Activity {

    @BindView(R.id.date_show)
    RecyclerView dateShow;
    @BindView(R.id.data_monitor)
    TextView dataMonitor;
    @BindView(R.id.data_curve)
    TextView dataCurve;
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
        Log.i("TAG", getIntent().getStringExtra("ID"));
        monitorListRequest(getIntent().getStringExtra("ID"));
    }

    private void monitorListRequest(String id) {

        OkHttpUtils.get().url("http://192.168.10.195:8080/gzcmd/detection/findMonitorData/" + id + "/1/10000")
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
                        setAdapter();
                    }
                });

    }
    private void monitorCurveRequest(String id) {

        OkHttpUtils.get().url("http://192.168.10.195:8080/gzcmd/tabcollect/findHighcharts/521421060058_01/2017-07-08%2019:55:34/2017-09-20%2019:55:34")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.i("qingsong", response);
//                        Gson gson = new Gson();
//                        mMonitorData = gson.fromJson(response, MonitorData.class);
//                        setAdapter();
                    }
                });

    }

    private void setAdapter() {

        dateShow.setLayoutManager(new LinearLayoutManager(mContext));
        monitorAdapter = new MonitorAdapter(mContext, mMonitorData);
        monitorAdapter.setOnItemClickListener(new MonitorAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(mContext, MonitorPhotoActivity.class);
                intent.putExtra("ID", mMonitorData.getData().getResult().get(position - 1).getID());
                intent.putExtra("Time", mMonitorData.getData().getResult().get(position - 1).getTime());
                startActivity(intent);
            }
        });
        dateShow.setAdapter(monitorAdapter);

    }

    @OnClick({R.id.data_monitor, R.id.data_curve})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.data_monitor:
                dateShow.setVisibility(View.VISIBLE);
                break;
            case R.id.data_curve:
                dateShow.setVisibility(View.GONE);
                monitorCurveRequest("521421060058_01");
                break;
        }
    }
}
