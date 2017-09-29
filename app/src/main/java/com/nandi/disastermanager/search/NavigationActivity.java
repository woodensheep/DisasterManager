package com.nandi.disastermanager.search;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nandi.disastermanager.MyApplication;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.dao.GreenDaoManager;
import com.nandi.disastermanager.search.entity.DisasterPoint;
import com.nandi.disastermanager.search.entity.GTSLocationPoint;
import com.nandi.disastermanager.ui.WaitingDialog;
import com.nandi.disastermanager.utils.AMapUtil;
import com.nandi.disastermanager.utils.ToastUtils;
import com.nandi.disastermanager.utils.TransformUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class NavigationActivity extends Activity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.baidu)
    Button baidu;
    @BindView(R.id.gaode)
    Button gaode;
    private double lat;
    private double lon;
    private String name;
    private double jd;
    private double wd;
    private GTSLocationPoint gtsLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        MyApplication.getActivities().add(this);
        Long id = getIntent().getLongExtra("id",0);
        DisasterPoint disasterPoint = GreenDaoManager.queryDisasterById(id + "");
        Log.i("Tag","lon:"+lon+"---"+"lat:"+lat);
        lon=disasterPoint.getDisasterLon();
        lat=disasterPoint.getDisasterLat();
        name = disasterPoint.getDisasterName();
        gtsLocation = GreenDaoManager.queryGTSLocation(disasterPoint.getCounty(), disasterPoint.getTown());

        if (AMapUtil.isInstallByRead("com.autonavi.minimap")) {

        }else{
            title.setText("请选择目的地  （未安装高德地图）");
        }
    }
    @OnClick({R.id.baidu, R.id.gaode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.baidu:
                if (null != gtsLocation){
                    jd = gtsLocation.getJd();
                    wd = gtsLocation.getWd();
                    if (AMapUtil.isInstallByRead("com.autonavi.minimap")) {
                        double[] togcj02 = TransformUtil.wgs84togcj02(jd, wd);
                        AMapUtil.goToGaoDe(NavigationActivity.this, gtsLocation.getTown(),togcj02[1]+"" , togcj02[0]+"", "0");
                    }else{
                        ToastUtils.showShort(this,"您尚未安装高德地图");
                    }
                }else{
                    ToastUtils.showShort(this,"未提交数据");
                }
                break;
            case R.id.gaode:
                if (lat !=0&&lon !=0){
                    if (AMapUtil.isInstallByRead("com.autonavi.minimap")) {
                        double[] togcj02 = TransformUtil.wgs84togcj02(lon, lat);
                        AMapUtil.goToGaoDe(NavigationActivity.this, name,togcj02[1]+"" , togcj02[0]+"", "0");
                    }else{
                        ToastUtils.showShort(this,"您尚未安装高德地图");
                    }
                }else{
                    ToastUtils.showShort(this,"未获取到经纬度");
                }


                break;
        }
    }


}
