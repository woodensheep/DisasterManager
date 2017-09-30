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
import com.nandi.disastermanager.search.entity.MonitorListPoint;
import com.nandi.disastermanager.ui.WaitingDialog;
import com.nandi.disastermanager.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by qingsong on 2017/9/8.
 * 监测点列表
 */
public class MonitorListActivity extends Activity {
    @BindView(R.id.date_show)
    RecyclerView dateShow;
    private MonitorListAdapter monitorListAdapter;
    private Context mContext;
    private List<MonitorListPoint> monitorListPoints;
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_list);
        MyApplication.getActivities().add(this);
        ButterKnife.bind(this);
        mContext = this;
        setAdapter();
    }

    private void setAdapter() {
        Long id = getIntent().getLongExtra("id", 0);
        DisasterPoint disasterPoint = GreenDaoManager.queryDisasterById(id + "");
        monitorListPoints = GreenDaoManager.queryMonitorName(disasterPoint.getDisasterNum());
        if (monitorListPoints.size() == 0){
            ToastUtils.showShort(mContext,"当前没有监测点");
        }else{
            dateShow.setLayoutManager(new LinearLayoutManager(mContext));
            monitorListAdapter = new MonitorListAdapter(mContext, monitorListPoints);
            monitorListAdapter.setOnItemClickListener(new MonitorListAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    long currentTime = Calendar.getInstance().getTimeInMillis();
                    if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                        lastClickTime = currentTime;
                        Intent intent = new Intent(mContext, MonitorDataActivity.class);
                        intent.putExtra("ID", monitorListPoints.get(position).getMonitorId());
                        startActivity(intent);
                    }
                }
            });
            dateShow.setAdapter(monitorListAdapter);
        }

    }

}
