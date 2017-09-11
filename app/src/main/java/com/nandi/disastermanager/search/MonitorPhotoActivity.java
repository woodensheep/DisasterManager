package com.nandi.disastermanager.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
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
    private Context mContext;
    private MonitorPhoto monitorPhoto;
    private MonitorPhotoAdapter monitorPhotoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_photo);
        ButterKnife.bind(this);
        mContext = this;
        monitorPhoto = new MonitorPhoto();
        String id = getIntent().getStringExtra("ID");
        String time = getIntent().getStringExtra("Time");
        Log.i("TAG", id+time);
        monitorListRequest(id, time);
    }

    private void monitorListRequest(String id,String time) {

        OkHttpUtils.get().url(getString(R.string.base_gz_url)+"/detection/findPhoto/"+id+"/"+time)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        monitorPhoto = gson.fromJson(response, MonitorPhoto.class);
                        if (monitorPhoto.getMeta().isSuccess()){
                            Log.i("qingsong", response);
                            dateShow.setLayoutManager(new LinearLayoutManager(mContext));
                            monitorPhotoAdapter = new MonitorPhotoAdapter(mContext, monitorPhoto);
                            dateShow.setAdapter(monitorPhotoAdapter);
                        }else {
                            ToastUtils.showShort(mContext,monitorPhoto.getMeta().getMessage());
                        }

                    }
                });

    }

}
