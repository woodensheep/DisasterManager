package com.nandi.disastermanager.search;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.nandi.disastermanager.PictureAdapter;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.dao.GreenDaoManager;
import com.nandi.disastermanager.search.entity.PhotoPath;
import com.nandi.disastermanager.ui.WaitingDialog;
import com.nandi.disastermanager.utils.AppUtils;
import com.nandi.disastermanager.utils.Constant;
import com.nandi.disastermanager.utils.NoDoubleClickListener;
import com.nandi.disastermanager.utils.SharedUtils;
import com.nandi.disastermanager.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class PhotoActivity extends AppCompatActivity {
    private static final String TAG = "PhotoActivity";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private final OkHttpClient client = new OkHttpClient();
    private static final int TAKE_PHOTO = 1;
    private static final int PICK_PHOTO = 2;
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
    private PictureAdapter picAdapter;
    private List<PhotoPath> photoPathList = new ArrayList<>();
    private boolean isSave = true;//是否保存
    private int number;
    private String userName;
    private String password;
    private RequestCall build;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        context = this;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        userName = (String) SharedUtils.getShare(context, Constant.USER_NAME, "");
        password = (String) SharedUtils.getShare(context, Constant.PASSWORD, "");
        getWindow().setAttributes(lp);
        initProgress();
        initViews();
        setListener();
    }

    private void initProgress() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.img);
        TextView textView = (TextView) view.findViewById(R.id.tipTextView);
        textView.setText("正在上传...");
        RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(1000);
        animation.setRepeatCount(-1);
        imageView.startAnimation(animation);
        loadingDialog = new Dialog(context, R.style.MyDialogStyle);// 创建自定义样式dialog
        loadingDialog.setCancelable(true); // 是否可以按“返回键”消失
        loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
        loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
    }

    private void setListener() {
        llTakePhoto.setOnClickListener(listener);
        llSave.setOnClickListener(listener);
        llUpload.setOnClickListener(listener);
        picAdapter.setOnItemViewClickListener(new PictureAdapter.OnItemViewClickListener() {
            @Override
            public void onPictureClick(int position) {
                enlargePhoto(position);
            }

            @Override
            public void onDeleteClick(int position) {
                photoPathList.remove(position);
                picAdapter.notifyDataSetChanged();
                isSave = false;
            }
        });
        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isSave = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (build != null) {
                    build.cancel();
                }
            }
        });
    }

    private void enlargePhoto(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_enlarge_photo, null);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.photo_view);
        photoView.setImageBitmap(AppUtils.getSmallBitmap(photoPathList.get(position).getPath(), 1280, 720));
        new AlertDialog.Builder(context, R.style.Transparent)
                .setView(view)
                .show();
    }

    private void initViews() {
        List<PhotoPath> photoPaths = GreenDaoManager.queryPhotoList();
        if (photoPaths != null && photoPaths.size() > 0) {
            photoPathList.addAll(photoPaths);
        }
        picAdapter = new PictureAdapter(context, photoPathList);
        rvPhotoList.setLayoutManager(new GridLayoutManager(context, 5));
        rvPhotoList.setAdapter(picAdapter);
        etMessage.setText((String) SharedUtils.getShare(context, Constant.SAVE_NOTE, ""));
    }

    NoDoubleClickListener listener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            switch (view.getId()) {
                case R.id.ll_take_photo:
                    if (photoPathList.size() < 10) {
                        choosePhoto();
                    } else {
                        ToastUtils.showShort(context, "最多只能添加十张照片");
                    }
                    break;
                case R.id.ll_save:
                    save();
                    break;
                case R.id.ll_upload:
                    upload();
                    break;
            }
        }
    };

    private void choosePhoto() {
        View customView= LayoutInflater.from(context).inflate(R.layout.dialog_photo_pick,null);
        Button btnTakePhoto= (Button) customView.findViewById(R.id.btn_take_photo);
        Button btnPickPhoto= (Button) customView.findViewById(R.id.btn_pick_photo);
        final AlertDialog show = new AlertDialog.Builder(context)
                .setView(customView)
                .show();
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
                show.dismiss();
            }
        });
        btnPickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,PICK_PHOTO);
                show.dismiss();
            }
        });
    }

    //权限申请
    private void checkPermission() {
        Log.i(TAG, "赋予权限");
        final String permission = Manifest.permission.CAMERA;  //相机权限

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {  //先判断是否被赋予权限，没有则申请权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
                    ) {  //给出权限申请说明
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            } else { //直接申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1); //申请权限，可同时申请多个权限，并根据用户是否赋予权限进行判断
            }
        } else {  //赋予过权限，则直接调用相机拍照
            takePhoto();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {  //申请权限的返回值
            case 1:
                int length = grantResults.length;
                final boolean isGranted = length >= 1 && PackageManager.PERMISSION_GRANTED == grantResults[length - 1];
                if (isGranted) {  //如果用户赋予权限，则调用相机
                    Log.i(TAG, "开始拍照");
                    takePhoto();
                } else { //未赋予权限，则做出对应提示
                    checkPermission();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void upload() {// TODO: 2017/10/9
        loadingDialog.show();
        OkHttpUtils.get().url(getResources().getString(R.string.base_gz_url) + "appdocking/login/" + userName + "/" + password + "/2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showShort(context, "网络连接失败！");
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(final String response, int id) {
                        String msg = etMessage.getText().toString().trim();
                        PostFormBuilder builder = OkHttpUtils.post().url(getResources().getString(R.string.base_gz_url) + "uploading/add");
                        for (PhotoPath photoPath : photoPathList) {
                            builder.addFile("file", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg", new File(photoPath.getPath()));
                        }
                        builder.addParams("message", msg);
                        builder.addParams("user", userName);
                        build = builder.build();
                        build.execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                if ("Canceled".equals(e.getMessage())) {
                                    ToastUtils.showShort(context, "取消上传！");
                                } else {
                                    ToastUtils.showShort(context, "网络连接失败！");
                                }
                                loadingDialog.dismiss();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                if ("success".equals(response)) {
                                    ToastUtils.showShort(context, "上传成功！");
                                    loadingDialog.dismiss();
                                    GreenDaoManager.deleteAllPhoto();
                                    SharedUtils.removeShare(context, Constant.SAVE_NOTE);
                                    finish();
                                } else {
                                    ToastUtils.showShort(context, "上传失败！");
                                }
                            }
                        });
                    }
                });
    }

    private void save() {
        GreenDaoManager.deleteAllPhoto();
        for (PhotoPath photoPath : photoPathList) {
            GreenDaoManager.insertPhoto(photoPath);
        }
        SharedUtils.putShare(context, Constant.SAVE_NOTE, etMessage.getText().toString().trim());
        ToastUtils.showShort(context, "保存成功");
        isSave = true;
    }

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
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PHOTO) {
                showPhoto();
            }else if (requestCode==PICK_PHOTO){
                Uri uri = data.getData();
                if (uri != null) {
                    String[] proj = { MediaStore.Images.Media.DATA };
                    // 好像是android多媒体数据库的封装接口，具体的看Android文档
                    Cursor cursor = managedQuery(uri, proj, null, null, null);
                    // 按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    // 最后根据索引值获取图片路径
                    String path = cursor.getString(column_index);
                    System.out.println("照片路径："+path);
                    PhotoPath photoPath=new PhotoPath();
                    photoPath.setPath(path);
                    photoPathList.add(photoPath);
                    picAdapter.notifyDataSetChanged();
                    isSave=false;
                }
            }
        }
    }

    private void showPhoto() {
        PhotoPath pathBean = new PhotoPath();
        pathBean.setPath(pictureFile.getAbsolutePath());
        photoPathList.add(pathBean);
        picAdapter.notifyDataSetChanged();
        isSave = false;
        Log.d(TAG, "图片路径集合数据：" + photoPathList.toString());
    }

    @Override
    public void onBackPressed() {
        if (isSave) {
            finish();
        } else {
            new AlertDialog.Builder(context)
                    .setTitle("提示")
                    .setMessage("检查到本次操作没有保存，是否要退出？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).show();
        }
    }
}
