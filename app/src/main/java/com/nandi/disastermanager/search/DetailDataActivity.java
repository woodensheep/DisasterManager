package com.nandi.disastermanager.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.search.entity.DetailData;
import com.nandi.disastermanager.search.entity.MonitorListData;
import com.nandi.disastermanager.ui.WaitingDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class DetailDataActivity extends Activity {

    @BindView(R.id.date_show)
    RecyclerView dateShow;
    private DetailDataAdapter detailAdapter;
    /**
     * 数据
     **/
    private DetailData mDetailData;
    private Context mContext;
    private String[] array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deail);
        ButterKnife.bind(this);
        mContext = this;
        mDetailData = new DetailData();

        monitorListRequest();
    }

    private void monitorListRequest() {

        OkHttpUtils.get().url("http://192.168.10.195:8080/gzcmd/disaterpoint/findByDisaterCode/520402010001")
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
                        mDetailData = gson.fromJson(response, DetailData.class);
                        dateShow.setLayoutManager(new LinearLayoutManager(mContext));
                        addArray();
                        detailAdapter = new DetailDataAdapter(mContext, array);
                        dateShow.setAdapter(detailAdapter);
                    }
                });

    }

    private void addArray() {
        array = new String[18];
        array[0] = mDetailData.getData().get(0).getName();
        if ("01".equals(mDetailData.getData().get(0).getType())) {
            array[1] = "滑坡";
        } else if ("02".equals(mDetailData.getData().get(0).getType())) {
            array[1] = "地面塌陷";
        } else if ("03".equals(mDetailData.getData().get(0).getType())) {
            array[1] = "泥石流";
        } else if ("05".equals(mDetailData.getData().get(0).getType())) {
            array[1] = "地裂缝";
        } else if ("06".equals(mDetailData.getData().get(0).getType())) {
            array[1] = "不稳定斜坡";
        } else if ("07".equals(mDetailData.getData().get(0).getType())) {
            array[1] = "崩塌";
        }
        array[2] = mDetailData.getData().get(0).getDisNum();
        array[3] = mDetailData.getData().get(0).getVillages();
        array[4] = mDetailData.getData().get(0).getLon() + "";
        array[5] = mDetailData.getData().get(0).getLat() + "";
        array[6] = mDetailData.getData().get(0).getSite();
        array[7] = mDetailData.getData().get(0).getInducement();
        array[8] = mDetailData.getData().get(0).getThreatLevel();
        array[9] = mDetailData.getData().get(0).getThreatNum();
        array[10] = mDetailData.getData().get(0).getThreatMoney();
        array[11] = mDetailData.getData().get(0).getWxqt();
        array[12] = mDetailData.getData().get(0).getSbsj();
        array[13] = mDetailData.getData().get(0).getFxsj();
        array[14] = mDetailData.getData().get(0).getPreparer();
        array[15] = mDetailData.getData().get(0).getAuditor();
        array[16] = mDetailData.getData().get(0).getFzzrdw();
        array[17] = " ";

    }
}