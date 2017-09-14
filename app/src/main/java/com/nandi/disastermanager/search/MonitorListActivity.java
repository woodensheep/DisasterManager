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
import com.nandi.disastermanager.MyApplication;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.dao.GreenDaoManager;
import com.nandi.disastermanager.search.entity.DisasterPoint;
import com.nandi.disastermanager.search.entity.MonitorListData;
import com.nandi.disastermanager.ui.WaitingDialog;
import com.nandi.disastermanager.utils.ToastUtils;
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
        Long id = getIntent().getLongExtra("id",0);
        DisasterPoint disasterPoint = GreenDaoManager.queryDisasterById(id + "");

        monitorListRequest(disasterPoint.getDisasterCode());
        MyApplication.getActivities().add(this);
    }

    private void monitorListRequest(String code) {
        WaitingDialog.createLoadingDialog(mContext,"正在加载...");
        OkHttpUtils.get().url(getString(R.string.base_gz_url)+"/detection/findMonitorAll/"+code+"/1/10000")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showShort(mContext,"请求失败");
                        WaitingDialog.closeDialog();
                        finish();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("TAG", response);
                        Gson gson = new Gson();
                        monitorListData = gson.fromJson(response, MonitorListData.class);
                            setAdapter();
                        WaitingDialog.closeDialog();
                        int size = monitorListData.getData().getResult().size();
                        if (size==0){
                            ToastUtils.showShort(mContext,"当前没有监测点信息");
                        }
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
                intent.putExtra("ID", monitorListData.getData().getResult().get(position - 1).getID());
                startActivity(intent);
            }
        });
        dateShow.setAdapter(monitorListAdapter);
    }

}
