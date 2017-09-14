package com.nandi.disastermanager.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nandi.disastermanager.MyApplication;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.search.entity.MonitorPhoto;
import com.nandi.disastermanager.ui.WaitingDialog;
import com.nandi.disastermanager.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MonitorPhotoActivity extends Activity {
    @BindView(R.id.date_show)
    RecyclerView dateShow;
    private MonitorPhotoAdapter monitorPhotoAdapter;
    private MonitorPhoto monitorPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_photo);
        ButterKnife.bind(this);
        MyApplication.getActivities().add(this);
        monitorPhoto = (MonitorPhoto) getIntent().getSerializableExtra("MONITOR_PHOTO");
        dateShow.setLayoutManager(new LinearLayoutManager(this));
        monitorPhotoAdapter = new MonitorPhotoAdapter(this, monitorPhoto);
        dateShow.setAdapter(monitorPhotoAdapter);
        setListener();
    }

    private void setListener() {
        monitorPhotoAdapter.setOnItemClickListener(new MonitorPhotoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = dateShow.getChildAdapterPosition(view);
                String url = "http://gzgdm.oss-cn-guizhou-gov.aliyuncs.com/" + monitorPhoto.getData().get(position).getUrl();
                Intent intent=new Intent(MonitorPhotoActivity.this,EnlargePhotoActivity.class);
                intent.putExtra("PHOTO_URL",url);
                startActivity(intent);
            }
        });
    }

}
