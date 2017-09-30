package com.nandi.disastermanager.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.nandi.disastermanager.PictureAdapter;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.search.entity.PhotoPath;
import com.nandi.disastermanager.utils.AppUtils;
import com.nandi.disastermanager.utils.NoDoubleClickListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoActivity extends Activity {
    private static final String TAG = "PhotoActivity";
    private static final int TAKE_PHOTO = 1;
    @BindView(R.id.ll_take_photo)
    LinearLayout llTakePhoto;
    @BindView(R.id.ll_save)
    LinearLayout llSave;
    @BindView(R.id.ll_upload)
    LinearLayout llUpload;
    @BindView(R.id.rv_photo_list)
    RecyclerView rvPhotoList;
    @BindView(R.id.et_message)
    EditText etMessage;
    private File pictureFile;
    private Context context;
    private List<Bitmap> bitmapList=new ArrayList<>();
    private PictureAdapter picAdapter;
    private List<PhotoPath> photoPathList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        context=this;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);
        initViews();
        setListener();
    }

    private void setListener() {
        llTakePhoto.setOnClickListener(listener);
        llSave.setOnClickListener(listener);
        llUpload.setOnClickListener(listener);
        picAdapter.setOnItemViewClickListener(new PictureAdapter.OnItemViewClickListener() {
            @Override
            public void onPictureClick(int position) {
                Log.d(TAG,"点击了图片");
            }

            @Override
            public void onDeleteClick(int position) {
                Log.d(TAG,"点击了删除图片");

            }
        });
    }

    private void initViews() {
        picAdapter=new PictureAdapter(context,bitmapList);
        rvPhotoList.setLayoutManager(new GridLayoutManager(context, 5));
        rvPhotoList.setAdapter(picAdapter);
    }
    NoDoubleClickListener listener=new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            switch (view.getId()){
                case R.id.ll_take_photo:
                    takePhoto();
                    break;
                case R.id.ll_save:
                    break;
                case R.id.ll_upload:
                    break;
            }
        }
    };

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        pictureFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg");
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {  //针对Android7.0，需要通过FileProvider封装过的路径，提供给外部调用
            imageUri = FileProvider.getUriForFile(context, "com.nandi.disastermanager.fileprovider", pictureFile);//通过FileProvider创建一个content类型的Uri，进行封装
        } else { //7.0以下，如果直接拿到相机返回的intent值，拿到的则是拍照的原图大小，很容易发生OOM，所以我们同样将返回的地址，保存到指定路径，返回到Activity时，去指定路径获取，压缩图片
            imageUri = Uri.fromFile(pictureFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        Log.d(TAG, "图片保存路径：" + pictureFile.getAbsolutePath());
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==TAKE_PHOTO){
                showPhoto();
            }
        }
    }

    private void showPhoto() {
        Bitmap bitmap = AppUtils.getSmallBitmap(pictureFile.getAbsolutePath(), 50, 100);
        bitmapList.add(bitmap);
        picAdapter.notifyDataSetChanged();
        PhotoPath pathBean = new PhotoPath();
        pathBean.setPath(pictureFile.getAbsolutePath());
        photoPathList.add(pathBean);
        Log.d(TAG, "图片路径集合数据：" + photoPathList.toString());
    }
}
