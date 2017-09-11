package com.nandi.disastermanager.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.search.entity.MonitorListData;
import com.nandi.disastermanager.ui.WaitingDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MonitorListActivity extends Activity {
    @BindView(R.id.date_show)
    RecyclerView dateShow;
    private MonitorListAdapter monitorListAdapter;
    private Context mContext;
    private MonitorListData monitorListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_list);
        ButterKnife.bind(this);
        mContext = this;
        monitorListData = new MonitorListData();

        monitorListRequest();
    }

    private void monitorListRequest() {

        OkHttpUtils.get().url("http://192.168.10.195:8080/gzcmd/detection/findMonitorAll/520402010001/1/10000")
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
                        monitorListData = gson.fromJson(response, MonitorListData.class);
                        setAdapter();

                    }
                });

    }

    private void setAdapter() {
        dateShow.setLayoutManager(new LinearLayoutManager(mContext));
        monitorListAdapter = new MonitorListAdapter(mContext, monitorListData);
        monitorListAdapter.setOnItemClickListener(new MonitorListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(mContext, MonitorDataActivity.class);
                intent.putExtra("ID",monitorListData.getData().getResult().get(position-1).getID());
                startActivity(intent);
            }
        });
        dateShow.setAdapter(monitorListAdapter);
    }

}
