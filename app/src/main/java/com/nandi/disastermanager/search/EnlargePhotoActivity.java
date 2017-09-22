package com.nandi.disastermanager.search;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.nandi.disastermanager.MyApplication;
import com.nandi.disastermanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnlargePhotoActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarge_photo);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);
        MyApplication.getActivities().add(this);
        String photo_url = getIntent().getStringExtra("PHOTO_URL");
        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        Glide.with(this)
                .load(photo_url)
                .placeholder(R.mipmap.downloading)
                .thumbnail(0.1f)
                .error(R.mipmap.download_pass)
                .into(photoView);
    }
}
