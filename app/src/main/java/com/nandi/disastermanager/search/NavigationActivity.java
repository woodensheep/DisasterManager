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
import com.nandi.disastermanager.ui.WaitingDialog;
import com.nandi.disastermanager.utils.AMapUtil;
import com.nandi.disastermanager.utils.ToastUtils;
import com.nandi.disastermanager.utils.TransformUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

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
        if (AMapUtil.isInstallByRead("com.baidu.BaiduMap")) {
        }else{
            baidu.setText("百度地图（未安装）");
        }
        if (AMapUtil.isInstallByRead("com.autonavi.minimap")) {

        }else{
            gaode.setText("高德地图（未安装）");
        }
    }
    @OnClick({R.id.baidu, R.id.gaode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.baidu:
                if (lat !=0&& lon !=0){

                    if (AMapUtil.isInstallByRead("com.baidu.BaiduMap")) {
                        double[] tobd09 = TransformUtil.wgs84tobd09(lon, lat);
//                    AMapUtil.goToBaidu(this, name, tobd09[1]+"",tobd09[0]+"");
                        AMapUtil.goToBaidu(this, name, lat+"",lon+"");

                    }else{
                        ToastUtils.showShort(this,"您尚未安装百度地图");
                    }
                } else{
                    ToastUtils.showShort(this,"未获取到经纬度");
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
