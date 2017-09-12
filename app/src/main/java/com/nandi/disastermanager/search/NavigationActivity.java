package com.nandi.disastermanager.search;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.dao.GreenDaoManager;
import com.nandi.disastermanager.search.entity.DisasterPoint;
import com.nandi.disastermanager.utils.AMapUtil;
import com.nandi.disastermanager.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavigationActivity extends Activity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.baidu)
    Button baidu;
    @BindView(R.id.gaode)
    Button gaode;
    private String disNo;
    private String lat;
    private String lon;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        Long id = getIntent().getLongExtra("id",0);
        DisasterPoint disasterPoint = GreenDaoManager.queryDisasterById(id + "");
        Log.i("Tag",disasterPoint.getDisasterCode()+"---"+disasterPoint.getLat());
        disNo = disasterPoint.getDisasterCode();
        lat = disasterPoint.getLATITUDE();
        lon = disasterPoint.getLONGITUDE();
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
                if (AMapUtil.isInstallByRead("com.baidu.BaiduMap")) {
                    AMapUtil.goToBaidu(this, name, lat, lon);
                }else{
                    ToastUtils.showShort(this,"您尚未安装百度地图");
                }
                break;
            case R.id.gaode:
                if (AMapUtil.isInstallByRead("com.autonavi.minimap")) {
                    AMapUtil.goToGaoDe(NavigationActivity.this, name, lat, lon, "1");
                }else{
                    ToastUtils.showShort(this,"您尚未安装高德地图");
                }

                break;
        }
    }


}
