package com.nandi.disastermanager.search;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nandi.disastermanager.R;
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
    @BindView(R.id.baiduHD)
    Button baiduHD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        if (AMapUtil.isInstallByRead("com.baidu.BaiduMap")) {
        }else{
            baidu.setText("百度地图（未安装）");
        }
        if (AMapUtil.isInstallByRead("com.baidu.BaiduMap.pad")) {
        }else{
            baidu.setText("百度地图HD（未安装）");
        }
        if (AMapUtil.isInstallByRead("com.autonavi.minimap")) {
        }else{
            baidu.setText("高德地图（未安装）");
        }
    }

    @OnClick({R.id.baidu, R.id.gaode,R.id.baiduHD})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.baidu:
                if (AMapUtil.isInstallByRead("com.baidu.BaiduMap")) {
                    AMapUtil.goToBaidu(this, "123", "106.03361100", "26.38500000");
                }else{
                    ToastUtils.showShort(this,"您尚未安装百度地图");
                }
                break;
            case R.id.gaode:
                if (AMapUtil.isInstallByRead("com.autonavi.minimap")) {
                    AMapUtil.goToGaoDe(NavigationActivity.this, "123", "106.03361100", "26.38500000", "1");
                }else{
                    ToastUtils.showShort(this,"您尚未安装高德地图");
                }

                break;
            case R.id.baiduHD:
                if (AMapUtil.isInstallByRead("com.baidu.BaiduMap.pad")) {
                    AMapUtil.goToBaiduHD(NavigationActivity.this, "123", "106.03361100", "26.38500000");
                }else{
                    ToastUtils.showShort(this,"您尚未安装百度地图HD");
                }
                break;
        }
    }


}
