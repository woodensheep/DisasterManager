package com.nandi.disastermanager.search;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nandi.disastermanager.MyApplication;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.dao.GreenDaoManager;
import com.nandi.disastermanager.search.entity.DetailData;
import com.nandi.disastermanager.search.entity.DisasterPoint;
import com.nandi.disastermanager.search.entity.DisasterPointDao;
import com.nandi.disastermanager.search.entity.MonitorData;
import com.nandi.disastermanager.search.entity.MonitorListData;
import com.nandi.disastermanager.search.entity.MonitorPoint;
import com.nandi.disastermanager.search.entity.MonitorPointDao;
import com.nandi.disastermanager.ui.WaitingDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by qingsong on 2017/9/8.
 * 隐患点详细信息页面
 */
public class DetailDataActivity extends Activity {

    @BindView(R.id.date_show)
    RecyclerView dateShow;
    /*适配器*/
    private DetailDataAdapter detailAdapter;
    private Context mContext;
    private String[] array;
    private DisasterPoint disasterPoint;
    private List<MonitorPoint> monitorPoints;
    private String gather;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deail);
        ButterKnife.bind(this);
        MyApplication.getActivities().add(this);
        mContext = this;
        Long id = getIntent().getLongExtra("id", 0);
        disasterPoint = GreenDaoManager.queryDisasterById(id + "");
//        monitorPoints = GreenDaoManager.queryMonitorData1(disasterPoint.getDisasterNum());
//        for (MonitorPoint point : monitorPoints) {
//            System.out.println(point.getTime());
//            gather = point.getGather();
//            phone = point.getPhone();
//        }
        dateShow.setLayoutManager(new LinearLayoutManager(mContext));
        addArray();
        detailAdapter = new DetailDataAdapter(mContext, array);
        dateShow.setAdapter(detailAdapter);

    }

    /**
     * 加入信息
     */
    private void addArray() {
        array = new String[18];
        /*隐患点名称*/
        array[0] = disasterPoint.getDisasterName();
        /*隐患点类型*/
        array[1] = disasterPoint.getDisasterType();
        /*隐患点编号*/
        array[2] = "null".equals(disasterPoint.getDisasterNum()) ? "" : disasterPoint.getDisasterNum();
        /*所在地*/
        array[3] = "null".equals(disasterPoint.getTown()) ? "" : disasterPoint.getTown();
        /*灾害点经度*/
        array[4] = disasterPoint.getDisasterLon() + "";
          /*灾害点纬度*/
        array[5] = disasterPoint.getDisasterLat() + "";
          /*详细地址*/
        array[6] = "null".equals(disasterPoint.getDisasterSite()) ? "" : disasterPoint.getDisasterSite();
          /*主要诱因*/
        array[7] = "null".equals(disasterPoint.getMajorIncentives()) ? "" : disasterPoint.getMajorIncentives();
          /*灾害等级*/
        array[8] = "null".equals(disasterPoint.getDisasterGrade()) ? "" : disasterPoint.getDisasterGrade();
        /*受威胁人数*/
        array[9] = "null".equals(disasterPoint.getThreatNum()) ? "" : disasterPoint.getThreatNum();
        /*受威胁财产*/
        array[10] = "null".equals(disasterPoint.getThreatMoney()) ? "" : disasterPoint.getThreatMoney();
        /*受威胁对象*/
        array[11] = "null".equals(disasterPoint.getThreatObject()) ? "" : disasterPoint.getThreatObject();
        /*发现时间*/
        array[12] = "null".equals(disasterPoint.getFormationTime()) ? "" : disasterPoint.getFormationTime();
        /*上报时间*/
        array[13] = "null".equals(disasterPoint.getTableTime()) ? "" : disasterPoint.getTableTime();
        /*防灾责任单位*/
        array[14] = "null".equals(disasterPoint.getInvestigationUnit()) ? "" : disasterPoint.getInvestigationUnit();
        /*监测人*/
        array[15] = "null".equals(disasterPoint.getMonitorPersonnel()) ? "" : disasterPoint.getMonitorPersonnel();
        /*监测号码*/
        array[16] = "null".equals(disasterPoint.getPhoneNum()) ? "" : disasterPoint.getPhoneNum();
    }
}
