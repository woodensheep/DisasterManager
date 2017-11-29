package com.nandi.disastermanager;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.TileCache;
import com.esri.arcgisruntime.geometry.AreaUnit;
import com.esri.arcgisruntime.geometry.AreaUnitId;
import com.esri.arcgisruntime.geometry.GeodeticCurveType;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.LinearUnit;
import com.esri.arcgisruntime.geometry.LinearUnitId;
import com.esri.arcgisruntime.geometry.PartCollection;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.PolygonBuilder;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.ArcGISTiledElevationSource;
import com.esri.arcgisruntime.mapping.LayerList;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Callout;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.MapScaleChangedEvent;
import com.esri.arcgisruntime.mapping.view.MapScaleChangedListener;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.util.ListenableList;
import com.google.gson.Gson;
import com.nandi.disastermanager.dao.GreenDaoManager;
import com.nandi.disastermanager.entity.Gps;
import com.nandi.disastermanager.entity.LocationInfo;
import com.nandi.disastermanager.http.ReplaceService;
import com.nandi.disastermanager.search.DetailDataActivity;
import com.nandi.disastermanager.search.MonitorListActivity;
import com.nandi.disastermanager.search.NavigationActivity;
import com.nandi.disastermanager.search.PhotoActivity;
import com.nandi.disastermanager.search.SearchActivity;
import com.nandi.disastermanager.search.entity.DisasterPoint;
import com.nandi.disastermanager.search.entity.StaticsInfo;
import com.nandi.disastermanager.ui.WaitingDialog;
import com.nandi.disastermanager.utils.AppUtils;
import com.nandi.disastermanager.utils.Constant;
import com.nandi.disastermanager.utils.DownloadUtils;
import com.nandi.disastermanager.utils.NoDoubleClickListener;
import com.nandi.disastermanager.utils.SharedUtils;
import com.nandi.disastermanager.utils.SketchGraphicsOverlayEventListener;
import com.nandi.disastermanager.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String LOCATION_POINT = "locationPoint";
    public static final String DISASTER_POINT = "disasterPoint";
    @BindView(R.id.iv_area_back)
    ImageView ivAreaBack;
    @BindView(R.id.ll_area)
    LinearLayout llArea;
    @BindView(R.id.iv_search_main)
    ImageView ivSearchMain;
    @BindView(R.id.ll_control)
    LinearLayout llControl;
    @BindView(R.id.btn_util)
    Button btnUtil;
    @BindView(R.id.pointButton)
    ImageButton mPointButton;
    @BindView(R.id.polylineButton)
    ImageButton mPolylineButton;
    @BindView(R.id.polygonButton)
    ImageButton mPolygonButton;
    @BindView(R.id.clearButton)
    ImageButton mClearButton;
    @BindView(R.id.ll_util)
    LinearLayout llUtil;
    @BindView(R.id.tv_measure_result)
    TextView tvMeasureResult;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.ll_enlarge)
    LinearLayout llEnlarge;
    @BindView(R.id.ll_compass)
    LinearLayout llCompass;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.ll_narrow)
    LinearLayout llNarrow;
    @BindView(R.id.map_control)
    LinearLayout mapControl;
    @BindView(R.id.tv_scale)
    TextView tvScale;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.iv_change_map)
    Button ivChangeMap;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    @BindView(R.id.ll_userMessage)
    LinearLayout llUserMessage;
    @BindView(R.id.tv_disaster_number)
    TextView tvDisasterNumber;
    @BindView(R.id.tv_huapo_number)
    TextView tvHuapoNumber;
    @BindView(R.id.tv_bengta_number)
    TextView tvBengtaNumber;
    @BindView(R.id.tv_taxian_number)
    TextView tvTaxianNumber;
    @BindView(R.id.tv_nishiliu_number)
    TextView tvNishiliuNumber;
    @BindView(R.id.tv_diliefeng_number)
    TextView tvDiliefengNumber;
    @BindView(R.id.tv_xiepo_number)
    TextView tvXiepoNumber;
    @BindView(R.id.tv_teda_number)
    TextView tvTedaNumber;
    @BindView(R.id.tv_daxing_number)
    TextView tvDaxingNumber;
    @BindView(R.id.tv_zhong_number)
    TextView tvZhongNumber;
    @BindView(R.id.tv_xioxing_number)
    TextView tvXioxingNumber;
    @BindView(R.id.iv_route)
    ImageView ivRoute;
    @BindView(R.id.ll_route)
    LinearLayout llRoute;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.ll_notice)
    LinearLayout llNotice;
    @BindView(R.id.ll_photo)
    LinearLayout llPhoto;
    private boolean llAreaState = true;
    private boolean llUtilState = false;
    ArcGISTiledLayer gzYingXiangLayer;
    ArcGISTiledLayer gzYingXiangLayerHigh;
    ArcGISTiledLayer gzDianZhiLayer;
    ArcGISTiledLayer localeDianZhiLayer;
    private ArcGISMap map;
    private LayerList layers;
    private GraphicsOverlay gzPointGraphicOverlay;
    private GraphicsOverlay meGraphicOverlay;
    private GraphicsOverlay pointGraphicOverlay;
    private GraphicsOverlay editGraphicOverlay;
    private GraphicsOverlay editPointGraphicOverlay;
    private ListenableList<Graphic> gzGraphics;
    private ListenableList<Graphic> editGzGraphics;
    private List<Graphic> allGraphics = new ArrayList<>();
    private List<Graphic> editAllGraphics = new ArrayList<>();

    private Context context;
    private Graphic mCurrentPoint;
    private Graphic mCurrentLine;
    private Graphic mCurrentPolygon;
    private PointCollection mCurrentPointCollection;
    private DrawingMode mDrawingMode = DrawingMode.NONE;
    private boolean mIsPolylineStarted = false;
    private boolean mIsMidpointSelected = false;
    private Stack<UndoRedoItem> mUndoElementStack = new Stack<>();
    private Stack<UndoRedoItem> mRedoElementStack = new Stack<>();
    private boolean mVertexDragStarted = false;
    private double detailarea;
    private String detailhttp = "";
    public CloudPushService cloudPushService;
    private String id;
    private String level;
    private String userName;
    private String password;
    private GraphicsOverlay mGraphicsOverlay;
    private List<Graphic> mGraphics;
    private SketchGraphicsOverlayEventListener mListener;
    private SimpleMarkerSymbol mPointPlacementSymbol;
    private SimpleMarkerSymbol mPointPlacedSymbol;
    private SimpleMarkerSymbol mPolylineVertexSymbol;
    private SimpleLineSymbol mPolylinePlacementSymbol;
    private SimpleLineSymbol mPolylinePlacedSymbol;
    private SimpleMarkerSymbol mPolylineMidpointSymbol;
    private SimpleFillSymbol mPolygonFillSymbol;
    private int baseMap = 0;//0代表电子地图，1代表影像图
    private int location = 0;
    private int route = 0;
    private LocationClient locationClient = null;
    private LocationClient routeClient = null;
    private List<DisasterPoint> disasterOverlay = new ArrayList<>();
    private List<DisasterPoint> editDisasterOverlay = new ArrayList<>();
    private LocationListener locationListener;
    private double meLongitude = 0;
    private double meLatitude = 0;
    private Callout callout;
    private AlertDialog alertDialog;
    private File file;
    private String editLongitude;
    private String editLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MyApplication.getActivities().add(this);
        context = this;
        id = (String) SharedUtils.getShare(context, Constant.AREA_ID, "");
        level = (String) SharedUtils.getShare(context, Constant.LEVEL, "");
        userName = (String) SharedUtils.getShare(context, Constant.USER_NAME, "");
        password = (String) SharedUtils.getShare(context, Constant.PASSWORD, "");
        setLine();
        bindAccount();
        initUtilData();
        if (NetworkUtils.isConnected()) {
            loginPost(userName, password);
        } else {
            initStaData();
        }
        gzDianZhiLayer = new ArcGISTiledLayer(getResources().getString(R.string.guizhou_dianzi_url));
        gzYingXiangLayer = new ArcGISTiledLayer(getResources().getString(R.string.guizhou_yingxiang_url));
        gzYingXiangLayerHigh = new ArcGISTiledLayer(getResources().getString(R.string.guizhou_yingxiang_url_1));
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "guizhou.tpk");
        if (file.exists()) {
            TileCache tileCache = new TileCache(file.getAbsolutePath());
            localeDianZhiLayer = new ArcGISTiledLayer(tileCache);
        } else {
            ToastUtils.showShort(context, "请在设置页面下载离线地图包");
        }
        initMap();
        mClearButton.setClickable(false);
        mClearButton.setEnabled(false);
        locationClient = new LocationClient(getApplicationContext());
        routeClient = new LocationClient(getApplicationContext());
        setListeners();
        startUpdateService();
        turnOnLocation();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(LOCATION_POINT);
        registerReceiver(connectionReceiver, intentFilter);
        alertNotice();
    }

    private void alertNotice() {
        if (NetworkUtils.isConnected()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(context, NoticeActivity.class));
                }
            }, 2000);
        } else {
            ToastUtils.showShort(context, "当前无网络");
        }
    }

    private void initMap() {
        mapView.setAttributionTextVisible(false);
        map = new ArcGISMap();//添加底图
        layers = map.getOperationalLayers();
        mapView.setMap(map);
        gzPointGraphicOverlay = new GraphicsOverlay();
        mGraphicsOverlay = new GraphicsOverlay();
        meGraphicOverlay = new GraphicsOverlay();
        pointGraphicOverlay = new GraphicsOverlay();
        editGraphicOverlay = new GraphicsOverlay();
        editPointGraphicOverlay=new GraphicsOverlay();
        mGraphics = mGraphicsOverlay.getGraphics();
        gzGraphics = gzPointGraphicOverlay.getGraphics();
        editGzGraphics=editPointGraphicOverlay.getGraphics();
        ListenableList<GraphicsOverlay> graphicsOverlays = mapView.getGraphicsOverlays();
        graphicsOverlays.add(mGraphicsOverlay);
        graphicsOverlays.add(gzPointGraphicOverlay);
        graphicsOverlays.add(meGraphicOverlay);
        graphicsOverlays.add(pointGraphicOverlay);
        graphicsOverlays.add(editGraphicOverlay);
        graphicsOverlays.add(editPointGraphicOverlay);

        //初始显示的地图
        if (NetworkUtils.isConnected()) {
            layers.add(gzDianZhiLayer);
        } else {
            if (file.exists()) {
                layers.add(localeDianZhiLayer);
            } else {
                ToastUtils.showShort(context, "当前无网络，请下载离线地图包");
            }
        }
        Viewpoint viewpoint = new Viewpoint(26.713526, 106.759177, 1500000);
        map.setInitialViewpoint(viewpoint);
        callout = mapView.getCallout();
    }

    /**
     * 登录请求
     */
    private void loginPost(String userNumber, String password) {
        OkHttpUtils.get().url(getString(R.string.base_gz_url) + "/appdocking/login/" + userNumber + "/" + password + "/2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(final String response, int id) {
                        checkUpdate();
                        setStatics();
                    }
                });

    }

    private boolean checkDownload() {
        List<DisasterPoint> disasterPoints = GreenDaoManager.queryDisasterData();
        boolean isChangeUser = (boolean) SharedUtils.getShare(context, Constant.CHANGE_USER, false);
        Log.d(TAG, "是否更换账号" + isChangeUser);
        return disasterPoints.size() > 0 && isChangeUser;
    }

    private void setLine() {
        if ("1".equals(level)) {
            llRoute.setVisibility(View.VISIBLE);
            llPhoto.setVisibility(View.VISIBLE);
        }
    }

    /*获取统计信息*/
    private void setStatics() {
        OkHttpUtils.get().url(getResources().getString(R.string.base_gz_url) + "appdocking/countDisaterPoint/" + id + "/" + level)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showShort(context, "统计信息获取失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        StaticsInfo staticsInfo = gson.fromJson(response, StaticsInfo.class);
                        tvDisasterNumber.setText(staticsInfo.getData().getZs().get(0).getCount() + "");
                        String huapo = "0", taxian = "0", nishiliu = "0", dilie = "0", xiepo = "0", bengta = "0", teda = "0",
                                da = "", zhong = "", xiao = "";
                        List<StaticsInfo.DataBean.ZhzlBean> zhzl = staticsInfo.getData().getZhzl();
                        for (StaticsInfo.DataBean.ZhzlBean zhzlBean : zhzl) {
                            switch (zhzlBean.getXqdj()) {
                                case "01":
                                    huapo = String.valueOf(zhzlBean.getCount());
                                    break;
                                case "02":
                                    taxian = String.valueOf(zhzlBean.getCount());
                                    break;
                                case "03":
                                    nishiliu = String.valueOf(zhzlBean.getCount());
                                    break;
                                case "05":
                                    dilie = String.valueOf(zhzlBean.getCount());
                                    break;
                                case "06":
                                    xiepo = String.valueOf(zhzlBean.getCount());
                                    break;
                                case "07":
                                    bengta = String.valueOf(zhzlBean.getCount());
                                    break;
                            }
                        }
                        List<StaticsInfo.DataBean.DjBean> dj = staticsInfo.getData().getDj();
                        for (StaticsInfo.DataBean.DjBean djBean : dj) {
                            switch (djBean.getXqdj()) {
                                case "特大型":
                                    teda = String.valueOf(djBean.getCount());
                                    break;
                                case "大型":
                                    da = String.valueOf(djBean.getCount());
                                    break;
                                case "中型":
                                    zhong = String.valueOf(djBean.getCount());
                                    break;
                                case "小型":
                                    xiao = String.valueOf(djBean.getCount());
                                    break;
                            }
                        }
                        tvHuapoNumber.setText(huapo);
                        tvTaxianNumber.setText(taxian);
                        tvNishiliuNumber.setText(nishiliu);
                        tvDiliefengNumber.setText(dilie);
                        tvXiepoNumber.setText(xiepo);
                        tvBengtaNumber.setText(bengta);
                        tvTedaNumber.setText(teda);
                        tvDaxingNumber.setText(da);
                        tvZhongNumber.setText(zhong);
                        tvXioxingNumber.setText(xiao);
                    }
                });
    }

    /*展示统计信息*/
    private void initStaData() {
        List<DisasterPoint> disasterPoints = GreenDaoManager.queryDisasterData();
        if (disasterPoints.size() > 0) {
            List<DisasterPoint> huapo = GreenDaoManager.queryDisasterByType("滑坡");
            List<DisasterPoint> taxian = GreenDaoManager.queryDisasterByType("地面塌陷");
            List<DisasterPoint> nishiliu = GreenDaoManager.queryDisasterByType("泥石流");
            List<DisasterPoint> diliefeng = GreenDaoManager.queryDisasterByType("地裂缝");
            List<DisasterPoint> xiepo = GreenDaoManager.queryDisasterByType("不稳定斜坡");
            List<DisasterPoint> bengta = GreenDaoManager.queryDisasterByType("崩塌");
            List<DisasterPoint> teda = GreenDaoManager.queryDisasterByLevel("特大型");
            List<DisasterPoint> da = GreenDaoManager.queryDisasterByLevel("大型");
            List<DisasterPoint> zhong = GreenDaoManager.queryDisasterByLevel("中型");
            List<DisasterPoint> xiao = GreenDaoManager.queryDisasterByLevel("小型");
            tvDisasterNumber.setText(disasterPoints.size() + "");
            tvHuapoNumber.setText(huapo.size() + "");
            tvTaxianNumber.setText(taxian.size() + "");
            tvNishiliuNumber.setText(nishiliu.size() + "");
            tvDiliefengNumber.setText(diliefeng.size() + "");
            tvXiepoNumber.setText(xiepo.size() + "");
            tvBengtaNumber.setText(bengta.size() + "");
            tvTedaNumber.setText(teda.size() + "");
            tvDaxingNumber.setText(da.size() + "");
            tvZhongNumber.setText(zhong.size() + "");
            tvXioxingNumber.setText(xiao.size() + "");
        }
    }

    /**
     * 开启服务
     */
    private void startUpdateService() {
        Intent intent = new Intent(this, ReplaceService.class);
        startService(intent);
    }

    private void checkUpdate() {
        OkHttpUtils.get().url("http://202.98.195.125:8082/gzcmdback/findNewVersionNumber.do")
                .addParams("version", AppUtils.getVerCode(this))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG, "是否有更新：" + response);
                        try {
                            JSONObject object = new JSONObject(response);
                            String aStatic = object.optString("static");
                            String remark = object.optString("remark");
                            if (TextUtils.isEmpty(remark)) {
                                remark = "发现新版本，是否立即更新？";
                            }
                            if ("1".equals(aStatic)) {
                                showNoticeDialog(remark);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /*下载APK*/
    private void showNoticeDialog(String msg) {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("版本更新");
        builder.setMessage(msg);
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new DownloadUtils(context).downloadAPK("http://202.98.195.125:8082/gzcmdback/downloadApk.do", "app-release" + AppUtils.getVerCode(context) + ".apk");
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(dialog);
            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
            Field mButtonPositive = mAlertController.getClass().getDeclaredField("mButtonPositive");
            Field mButtonNegative = mAlertController.getClass().getDeclaredField("mButtonNegative");
            mMessage.setAccessible(true);
            mButtonPositive.setAccessible(true);
            mButtonNegative.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mAlertController);
            Button positiveButton = (Button) mButtonPositive.get(mAlertController);
            Button negativeButton = (Button) mButtonNegative.get(mAlertController);
            mMessageView.setTextSize(20);
            positiveButton.setTextSize(25);
            negativeButton.setTextSize(25);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    private void bindAccount() {
        cloudPushService = PushServiceFactory.getCloudPushService();
        String level = (String) SharedUtils.getShare(context, Constant.LEVEL, "");
        Log.e(TAG, "绑定的tag：" + level);
        cloudPushService.turnOnPushChannel(new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Log.d(TAG, "推送通道打开成功！");
            }

            @Override
            public void onFailed(String s, String s1) {
                Log.d(TAG, "推送通道打开失败！");
            }
        });
        cloudPushService.bindAccount(level, new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Log.d(TAG, "绑定账号成功");
            }

            @Override
            public void onFailed(String s, String s1) {
                Log.d(TAG, "绑定账号失败");
            }
        });
    }

    private void initUtilData() {
        mListener = new MySketchGraphicsOverlayEventListener();
        SimpleLineSymbol blackOutline =
                new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.rgb(0, 0, 0), 1);
        SimpleLineSymbol whiteOutline =
                new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.rgb(255, 255, 255), 1);

        mPointPlacementSymbol =
                new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 7);
        mPointPlacementSymbol.setOutline(blackOutline);
        mPointPlacedSymbol =
                new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.BLUE, 7);
        mPointPlacedSymbol.setOutline(blackOutline);
        mPolylineVertexSymbol =
                new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.SQUARE, Color.BLUE, 5);
        mPolylineVertexSymbol.setOutline(blackOutline);
        mPolylinePlacementSymbol =
                new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 5);
        mPolylinePlacedSymbol =
                new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 5);
        mPolylineMidpointSymbol =
                new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.YELLOW, 5);
        mPolylineMidpointSymbol.setOutline(blackOutline);
        mPolygonFillSymbol =
                new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, 0x00ffffff, null);
    }


    public void pointClick(View v) {
        if (!v.isSelected()) {
            v.setSelected(true);
            setDrawingMode(DrawingMode.POINT);
            mPolylineButton.setEnabled(false);
            mPolygonButton.setEnabled(false);
        } else {
            setDrawingMode(DrawingMode.NONE);
        }
    }

    public void polylineClick(View v) {
        if (!v.isSelected()) {
            v.setSelected(true);
            setDrawingMode(DrawingMode.POLYLINE);
            mPointButton.setEnabled(false);
            mPolygonButton.setEnabled(false);
        } else {
            setDrawingMode(DrawingMode.NONE);
        }
    }

    public void polygonClick(View v) {
        if (!v.isSelected()) {
            v.setSelected(true);
            setDrawingMode(DrawingMode.POLYGON);
            mPointButton.setEnabled(false);
            mPolylineButton.setEnabled(false);
        } else {
            setDrawingMode(DrawingMode.NONE);
        }
    }

    public void clearClick(View v) {
        clear();
    }

    public void editLocation(View view) {
        // TODO: 2017/11/14
        View customView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_location, null);
        final EditText etLon = (EditText) customView.findViewById(R.id.et_longitude);
        final EditText etLat = (EditText) customView.findViewById(R.id.et_latitude);
        Button btnLocation = (Button) customView.findViewById(R.id.btn_location);
        final AlertDialog show = new AlertDialog.Builder(context)
                .setView(customView)
                .show();
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editLongitude = etLon.getText().toString().trim();
                editLatitude = etLat.getText().toString().trim();
                setEditLocationOverlay(Double.parseDouble(editLongitude), Double.parseDouble(editLatitude));
                show.dismiss();
            }
        });
    }

    private void setEditLocationOverlay(final double lon, final double lat) {
        pointGraphicOverlay.getGraphics().clear();
        final BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.ecit_point);
        final PictureMarkerSymbol pinStarBlueSymbol = new PictureMarkerSymbol(drawable);
        pinStarBlueSymbol.setHeight(50);
        pinStarBlueSymbol.setWidth(35);
        pinStarBlueSymbol.loadAsync();
        pinStarBlueSymbol.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                Point point = new Point(lon, lat, SpatialReferences.getWgs84());
                Graphic graphic = new Graphic(point, pinStarBlueSymbol);
                editGraphicOverlay.getGraphics().add(graphic);
                Viewpoint v = new Viewpoint(lat, lon, mapView.getMapScale());
                mapView.setViewpointAsync(v, 2);
            }
        });
    }

    private class MySketchGraphicsOverlayEventListener implements SketchGraphicsOverlayEventListener {

        @Override
        public void onUndoStateChanged(boolean undoEnabled) {
            // Set the undo button's enabled/disabled state based on the event boolean
        }

        @Override
        public void onRedoStateChanged(boolean redoEnabled) {
            // Set the redo button's enabled/disabled state based on the event boolean
        }

        @Override
        public void onClearStateChanged(boolean clearEnabled) {
            // Set the clear button's enabled/disabled state based on the event boolean
            mClearButton.setEnabled(clearEnabled);
            mClearButton.setClickable(clearEnabled);
        }

        @Override
        public void onDrawingFinished() {
            // Reset the selected state of the drawing buttons when a drawing is finished
            mPointButton.setSelected(false);
            mPolylineButton.setSelected(false);
            mPolygonButton.setSelected(false);
            mPolygonButton.setEnabled(true);
            mPolylineButton.setEnabled(true);
            mPointButton.setEnabled(true);
        }
    }


    private void setListeners() {
        llPhoto.setOnClickListener(noDoubleClickListener);
        llNotice.setOnClickListener(noDoubleClickListener);
        ivRoute.setOnClickListener(noDoubleClickListener);
        ivLocation.setOnClickListener(noDoubleClickListener);
        ivChangeMap.setOnClickListener(noDoubleClickListener);
        llEnlarge.setOnClickListener(noDoubleClickListener);
        llNarrow.setOnClickListener(noDoubleClickListener);
        llCompass.setOnClickListener(noDoubleClickListener);
        ivSearchMain.setOnClickListener(noDoubleClickListener);
        btnUtil.setOnClickListener(noDoubleClickListener);
        ivAreaBack.setOnClickListener(noDoubleClickListener);
        llUserMessage.setOnClickListener(noDoubleClickListener);
        mapView.setOnTouchListener(new DefaultMapViewOnTouchListener(context, mapView) {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                final android.graphics.Point screenPoint = new android.graphics.Point((int) e.getX(), (int) e.getY());
                final ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphic = mapView.identifyGraphicsOverlayAsync(mGraphicsOverlay, screenPoint, 10.0, false);
                identifyGraphic.addDoneListener(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            IdentifyGraphicsOverlayResult identifyResult = identifyGraphic.get();
                            List<Graphic> graphic = identifyResult.getGraphics();
                            if (!graphic.isEmpty() && mDrawingMode == DrawingMode.NONE) {
                                Geometry geometry = graphic.get(0).getGeometry();
                                if (geometry instanceof Polyline) {
                                    double length = GeometryEngine.lengthGeodetic(geometry, new LinearUnit(LinearUnitId.KILOMETERS), GeodeticCurveType.GREAT_ELLIPTIC);
                                    BigDecimal b = new BigDecimal(length);
                                    double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                    tvMeasureResult.setText("长度为:" + f1 + "千米");
                                } else if (geometry instanceof Polygon) {
                                    detailarea = Math.abs(GeometryEngine.areaGeodetic(geometry, new AreaUnit(AreaUnitId.SQUARE_KILOMETERS), GeodeticCurveType.GREAT_ELLIPTIC));
                                    BigDecimal b = new BigDecimal(detailarea);
                                    double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                    tvMeasureResult.setText("长度为:" + f1 + "千米");
                                    tvMeasureResult.setText("面积为:" + f1 + "平方千米");
                                    PolygonBuilder builder = new PolygonBuilder((Polygon) geometry);
                                    PartCollection parts = builder.getParts();
                                    Iterator<Point> iterator = parts.getPartsAsPoints().iterator();
                                    detailhttp = "";
                                    while (iterator.hasNext()) {
                                        Point next = iterator.next();
                                        Log.d(TAG, "x:" + next.getX() + "--y:" + next.getY() + "\n");
                                        detailhttp = detailhttp + next.getX() + "," + next.getY() + "@";
                                    }

                                }
                            }
                            if (graphic.isEmpty()) {
                                if (!graphic.isEmpty() && !(graphic.get(0).getGeometry() instanceof Polygon)) {
                                    if (mDrawingMode == DrawingMode.POLYLINE || mDrawingMode == DrawingMode.POLYGON) {
                                        Graphic g = graphic.get(0);
                                        if (mCurrentPoint != null && !mCurrentPoint.equals(g)) {
                                            if (mIsMidpointSelected && !mVertexDragStarted) {
                                                mCurrentPoint.setSymbol(mPolylineMidpointSymbol);
                                            } else {
                                                mCurrentPoint.setSymbol(mPolylineVertexSymbol);
                                            }
                                            mIsMidpointSelected = (g.getSymbol().equals(mPolylineMidpointSymbol));
                                            mVertexDragStarted = false;
                                            mCurrentPoint = g;
                                            mCurrentPoint.setSymbol(mPointPlacementSymbol);
                                        }
                                    }
                                } else {
                                    boolean graphicsWasEmpty = mGraphics.isEmpty();
                                    Point point = mapView.screenToLocation(screenPoint);
                                    if (mDrawingMode == DrawingMode.POINT) {
                                        if (mCurrentPoint == null) {
                                            mCurrentPoint = new Graphic(point, mPointPlacementSymbol);
                                            mGraphics.add(mCurrentPoint);
                                            List<Graphic> graphics = new ArrayList<>();
                                            graphics.add(mCurrentPoint);
                                            queueUndoRedoItem(mUndoElementStack, new UndoRedoItem(UndoRedoItem.Event.ADD_POINT, graphics));
                                        } else {
                                            queueUndoRedoItem(mUndoElementStack, new UndoRedoItem(UndoRedoItem.Event.MOVE_POINT, mCurrentPoint.getGeometry()));
                                            mCurrentPoint.setGeometry(point);
                                        }
                                    } else if (mDrawingMode == DrawingMode.POLYLINE || mDrawingMode == DrawingMode.POLYGON) {
                                        mIsMidpointSelected = false;
                                        if (!mIsPolylineStarted) {
                                            mCurrentPointCollection.add(point);
                                            if (mDrawingMode == DrawingMode.POLYGON) {
                                                mCurrentPointCollection.add(point);
                                            }
                                        } else {
                                            if (mDrawingMode == DrawingMode.POLYGON) {
                                                if (mCurrentPointCollection.size() > 2) {
                                                    mGraphics.remove(mGraphics.size() - 1);
                                                }
                                                mCurrentPointCollection.add(mCurrentPointCollection.size() - 1, point);
                                            } else {
                                                mCurrentPointCollection.add(point);
                                            }
                                        }
                                        if (!mIsPolylineStarted) {
                                            mCurrentLine = new Graphic(new Polyline(mCurrentPointCollection), mPolylinePlacementSymbol);
                                            mCurrentPoint = new Graphic(point, mPointPlacementSymbol);
                                            List<Graphic> graphics = new ArrayList<>();
                                            if (mDrawingMode == DrawingMode.POLYGON) {
                                                mCurrentPolygon = new Graphic(new Polygon(mCurrentPointCollection), mPolygonFillSymbol);
                                                mGraphics.add(mCurrentPolygon);
                                                graphics.add(mCurrentPolygon);
                                            }
                                            mGraphics.add(mCurrentLine);
                                            mGraphics.add(mCurrentPoint);
                                            graphics.add(mCurrentLine);
                                            graphics.add(mCurrentPoint);
                                            queueUndoRedoItem(mUndoElementStack, new UndoRedoItem(UndoRedoItem.Event.ADD_POINT, graphics));
                                            mIsPolylineStarted = true;
                                        } else {
                                            addPolylinePoint(point);
                                            queueUndoRedoItem(mUndoElementStack, new UndoRedoItem(UndoRedoItem.Event.ADD_POLYLINE_POINT, null));
                                        }
                                    }
                                    boolean graphicsIsEmpty = mGraphics.isEmpty();
                                    if (graphicsWasEmpty && !graphicsIsEmpty) {
                                        mListener.onClearStateChanged(true);
                                    } else if (!graphicsWasEmpty && graphicsIsEmpty) {
                                        mListener.onClearStateChanged(false);
                                    }
                                    clearStack(mRedoElementStack);
                                }
                            }
                        } catch (InterruptedException | ExecutionException ie) {
                            ie.printStackTrace();
                        }
                    }
                });
                final ListenableFuture<IdentifyGraphicsOverlayResult> disasterIdentify = mapView.identifyGraphicsOverlayAsync(gzPointGraphicOverlay, screenPoint, 10.0, false);
                disasterIdentify.addDoneListener(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            IdentifyGraphicsOverlayResult identifyResult = disasterIdentify.get();
                            List<Graphic> graphic = identifyResult.getGraphics();
                            if (graphic.size() > 0) {
                                int zIndex = graphic.get(0).getZIndex();
                                for (DisasterPoint disasterPoint : disasterOverlay) {
                                    if (disasterPoint.getId() == zIndex) {
                                        showDisasterInfo(disasterPoint);
                                    }
                                }
                            }
                        } catch (InterruptedException | ExecutionException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                final ListenableFuture<IdentifyGraphicsOverlayResult> meIdentify = mapView.identifyGraphicsOverlayAsync(meGraphicOverlay, screenPoint, 10.0, false, 1);
                meIdentify.addDoneListener(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            IdentifyGraphicsOverlayResult identifyResult = meIdentify.get();
                            List<Graphic> graphics = identifyResult.getGraphics();
                            if (graphics.size() > 0) {
                                View view = LayoutInflater.from(context).inflate(R.layout.callout_view, null);
                                TextView tvLon = (TextView) view.findViewById(R.id.tv_lon);
                                TextView tvLat = (TextView) view.findViewById(R.id.tv_lat);
                                ImageView ivClose = (ImageView) view.findViewById(R.id.iv_close);
                                tvLon.setText(meLongitude + "");
                                tvLat.setText(meLatitude + "");
                                Point point = mapView.screenToLocation(screenPoint);
                                callout.setLocation(point);
                                callout.setContent(view);
                                callout.show();
                                ivClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        callout.dismiss();
                                    }
                                });
                            }
                        } catch (InterruptedException | ExecutionException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                final ListenableFuture<IdentifyGraphicsOverlayResult> pointLocationIdentify = mapView.identifyGraphicsOverlayAsync(pointGraphicOverlay, screenPoint, 10.0, false, 1);
                pointLocationIdentify.addDoneListener(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            IdentifyGraphicsOverlayResult identifyResult = pointLocationIdentify.get();
                            List<Graphic> graphic = identifyResult.getGraphics();
                            if (graphic.size() > 0) {
                                showDisasterInfo(locationPoint);
                            }
                        } catch (InterruptedException | ExecutionException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                final ListenableFuture<IdentifyGraphicsOverlayResult> editLocationIdentify = mapView.identifyGraphicsOverlayAsync(editGraphicOverlay, screenPoint, 10.0, false, 1);
                editLocationIdentify.addDoneListener(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            IdentifyGraphicsOverlayResult identifyResult = editLocationIdentify.get();
                            List<Graphic> graphic = identifyResult.getGraphics();
                            if (graphic.size() > 0) {
                                View view = LayoutInflater.from(context).inflate(R.layout.edit_callout_view, null);
                                TextView tvLon = (TextView) view.findViewById(R.id.tv_lon);
                                TextView tvLat = (TextView) view.findViewById(R.id.tv_lat);
                                TextView tvSearch = (TextView) view.findViewById(R.id.tv_search);
                                ImageView ivClose = (ImageView) view.findViewById(R.id.iv_close);
                                tvLon.setText(editLongitude);
                                tvLat.setText(editLatitude);
                                Point point = mapView.screenToLocation(screenPoint);
                                callout.setLocation(point);
                                callout.setContent(view);
                                callout.show();
                                ivClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        callout.dismiss();
                                    }
                                });
                                tvSearch.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        callout.dismiss();
                                        setEditDisasterOverlay(Double.parseDouble(editLongitude),Double.parseDouble(editLatitude));

                                    }
                                });
                            }
                        } catch (InterruptedException | ExecutionException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                final ListenableFuture<IdentifyGraphicsOverlayResult> editDisasterIdentify = mapView.identifyGraphicsOverlayAsync(editPointGraphicOverlay, screenPoint, 10.0, false);
                editDisasterIdentify.addDoneListener(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            IdentifyGraphicsOverlayResult identifyResult = editDisasterIdentify.get();
                            List<Graphic> graphic = identifyResult.getGraphics();
                            if (graphic.size() > 0) {
                                int zIndex = graphic.get(0).getZIndex();
                                for (DisasterPoint disasterPoint : editDisasterOverlay) {
                                    if (disasterPoint.getId() == zIndex) {
                                        showDisasterInfo(disasterPoint);
                                    }
                                }
                            }
                        } catch (InterruptedException | ExecutionException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                return true;
            }

            @Override
            public void onLongPress(MotionEvent event) {
                // Long press finishes a drawing
                if (llUtil.getVisibility() == View.VISIBLE) {

                    finishDrawing();
                }
            }


            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                if (llUtil.getVisibility() == View.VISIBLE) {
                    mVertexDragStarted = false;
                }
                return true;
            }
        });
        mapView.addMapScaleChangedListener(new MapScaleChangedListener() {
            @Override
            public void mapScaleChanged(MapScaleChangedEvent mapScaleChangedEvent) {
                int mapScale = (int) mapScaleChangedEvent.getSource().getMapScale();
                tvScale.setText("(1  :  " + mapScale + ")");
            }
        });
    }

    private void showDisasterInfo(final DisasterPoint disasterPoint) {
        final View dialog = showPointDataDialog(disasterPoint);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialog);
        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.show();
    }

    @NonNull
    private View showPointDataDialog(final DisasterPoint disasterPoint) {
        LayoutInflater inflater = getLayoutInflater();
        final View dialog = inflater.inflate(R.layout.dialog_point_data, null);
        TextView tvMonitorName = (TextView) dialog.findViewById(R.id.tv_monitor_name);
        TextView tvMonitorNum = (TextView) dialog.findViewById(R.id.tv_monitor_num);
        TextView tvMonitorGrade = (TextView) dialog.findViewById(R.id.tv_monitor_grade);
        TextView tvMonitorFactor = (TextView) dialog.findViewById(R.id.tv_monitor_factor);
        TextView tvDetails = (TextView) dialog.findViewById(R.id.tv_details);
        TextView tvMonitorPoint = (TextView) dialog.findViewById(R.id.tv_monitor_point);
        TextView tvNavigation = (TextView) dialog.findViewById(R.id.tv_navigation);
        ImageView ivClose = (ImageView) dialog.findViewById(R.id.iv_close);

        tvMonitorName.setText(disasterPoint.getDisasterName());
        tvMonitorNum.setText(disasterPoint.getDisasterNum());
        tvMonitorGrade.setText(disasterPoint.getDisasterGrade());
        tvMonitorFactor.setText(disasterPoint.getMajorIncentives());
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        tvDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailDataActivity.class);
                intent.putExtra("id", disasterPoint.getId());
                startActivity(intent);
            }
        });
        tvMonitorPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MonitorListActivity.class);
                intent.putExtra("id", disasterPoint.getId());
                startActivity(intent);
            }
        });
        tvNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                intent.putExtra("id", disasterPoint.getId());
                startActivity(intent);
            }
        });
        return dialog;
    }

    NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View v) {
            switch (v.getId()) {
                case R.id.ll_photo:
                    startActivity(new Intent(context, PhotoActivity.class));
                    break;
                case R.id.ll_notice:
                    Intent intent1 = new Intent(context, NoticeActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.iv_location:
                    setLocation();
                    break;
                case R.id.iv_route:
                    setRoute();
                    break;
                case R.id.iv_change_map:
                    changeBaseMap();
                    break;
                case R.id.ll_enlarge:
                    setEnlarge();
                    break;
                case R.id.ll_narrow:
                    setNarrow();
                    break;
                case R.id.iv_area_back:
                    setAreaBack();
                    break;
                case R.id.iv_search_main:
                    if (pointGraphicOverlay.getGraphics().size() > 0) {
                        pointGraphicOverlay.getGraphics().clear();
                    }
                    List<DisasterPoint> disasterPoints = GreenDaoManager.queryDisasterData();
                    if (disasterPoints.size() == 0) {
                        ToastUtils.showShort(context, "正在加载请稍候...");
                    } else {
                        WaitingDialog.createLoadingDialog(context, "正在加载");
                        Intent intent = new Intent(context, SearchActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.ll_userMessage:
                    Intent intent = new Intent(context, SettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ll_compass:
                    resetPosition();
                    break;
                case R.id.btn_util:
                    setUtilBack();
                    break;
            }
        }
    };

    private void setLocation() {
        if (location == 0) {
            setDisasterOverlay(meLongitude, meLatitude);
        } else if (location == 1) {
            showClearNotice();
        }
    }

    private void showClearNotice() {
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("是否要清除所有隐患点标记？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("清除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ivLocation.setSelected(false);
                        location = 0;
                        gzGraphics.clear();
                    }
                }).show();
    }

    private void turnOnLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("WGS84");
        option.setScanSpan(1000);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setIgnoreKillProcess(false);
        locationClient.setLocOption(option);
        locationListener = new LocationListener();
        locationClient.registerLocationListener(locationListener);
        locationClient.start();
    }

    private class LocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            int locType = bdLocation.getLocType();
            if (locType == BDLocation.TypeOffLineLocation || locType == BDLocation.TypeGpsLocation || locType == BDLocation.TypeNetWorkLocation) {
                double lon = bdLocation.getLongitude();
                double lat = bdLocation.getLatitude();
                Gps gps = AppUtils.gcj_To_Gps84(lat, lon);
                meLatitude = gps.getWgLat();
                meLongitude = gps.getWgLon();
                setLocationOverlay(meLongitude, meLatitude);
                locationClient.unRegisterLocationListener(locationListener);
                locationClient.stop();
            }
        }

        @Override
        public void onLocDiagnosticMessage(int locType, int diagnosticType, String diagnosticMessage) {
            if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_BETTER_OPEN_GPS) {
                ToastUtils.showShort(context, "请打开GPS");
            } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_BETTER_OPEN_WIFI) {
                ToastUtils.showShort(context, "建议打开WIFI提高定位经度");
            }
        }
    }

    private void setLocationOverlay(final double longitude, final double latitude) {
        meGraphicOverlay.getGraphics().clear();
        final BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.me_location);
        final PictureMarkerSymbol pinStarBlueSymbol = new PictureMarkerSymbol(drawable);
        pinStarBlueSymbol.setHeight(50);
        pinStarBlueSymbol.setWidth(30);
        pinStarBlueSymbol.loadAsync();
        pinStarBlueSymbol.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                Point point = new Point(longitude, latitude, SpatialReferences.getWgs84());
                Graphic graphic = new Graphic(point, pinStarBlueSymbol);
                meGraphicOverlay.getGraphics().add(graphic);
                Viewpoint v = new Viewpoint(latitude, longitude, mapView.getMapScale());
                mapView.setViewpointAsync(v, 2);
                WaitingDialog.closeDialog();
            }
        });
    }

    private void setDisasterOverlay(double longitude, double latitude) {
        disasterOverlay.clear();
        List<DisasterPoint> disasterPoints = GreenDaoManager.queryDisasterData();
        int range = (int) SharedUtils.getShare(context, Constant.DISASTER_RANGE, 10 * 1000);
        for (DisasterPoint disasterPoint : disasterPoints) {
            double disasterLon = disasterPoint.getDisasterLon();
            double disasterLat = disasterPoint.getDisasterLat();
            double distance = AppUtils.getDistance(longitude, latitude, disasterLon, disasterLat);
            if (distance < range) {
                disasterOverlay.add(disasterPoint);
            }
        }
        if (disasterOverlay.size() > 0) {
            setOverlay();
            Log.d(TAG, "开始打点 周围隐患点个数：" + disasterOverlay.size());
        } else {
            ToastUtils.showShort(context, "当前位置周围没有隐患点");
        }
    }
    private void setEditDisasterOverlay(double longitude, double latitude) {
        editDisasterOverlay.clear();
        List<DisasterPoint> disasterPoints = GreenDaoManager.queryDisasterData();
        int range = (int) SharedUtils.getShare(context, Constant.DISASTER_RANGE, 10 * 1000);
        for (DisasterPoint disasterPoint : disasterPoints) {
            double disasterLon = disasterPoint.getDisasterLon();
            double disasterLat = disasterPoint.getDisasterLat();
            double distance = AppUtils.getDistance(longitude, latitude, disasterLon, disasterLat);
            if (distance < range) {
                editDisasterOverlay.add(disasterPoint);
            }
        }
        if (editDisasterOverlay.size() > 0) {
            setEditOverlay();
            Log.d(TAG, "开始打点 周围隐患点个数：" + editDisasterOverlay.size());
        } else {
            ToastUtils.showShort(context, "当前位置周围没有隐患点");
        }
    }
    private void setOverlay() {
        allGraphics.clear();
        final BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.huapo);
        final PictureMarkerSymbol pinStarBlueSymbol = new PictureMarkerSymbol(drawable);
        pinStarBlueSymbol.setHeight(50);
        pinStarBlueSymbol.setWidth(35);
        pinStarBlueSymbol.loadAsync();
        pinStarBlueSymbol.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                for (DisasterPoint disasterPoint : disasterOverlay) {
                    Point point = new Point(disasterPoint.getDisasterLon(), disasterPoint.getDisasterLat(), SpatialReferences.getWgs84());
                    Graphic graphic = new Graphic(point, pinStarBlueSymbol);
                    graphic.setZIndex(disasterPoint.getId().intValue());
                    allGraphics.add(graphic);
                }
                Log.d(TAG, "所有图标个数：" + allGraphics.size());
                gzGraphics.addAll(allGraphics);
                ivLocation.setSelected(true);
                location = 1;
            }
        });
    }
    private void setEditOverlay() {
        editAllGraphics.clear();
        final BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.huapo);
        final PictureMarkerSymbol pinStarBlueSymbol = new PictureMarkerSymbol(drawable);
        pinStarBlueSymbol.setHeight(50);
        pinStarBlueSymbol.setWidth(35);
        pinStarBlueSymbol.loadAsync();
        pinStarBlueSymbol.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                for (DisasterPoint disasterPoint : editDisasterOverlay) {
                    Point point = new Point(disasterPoint.getDisasterLon(), disasterPoint.getDisasterLat(), SpatialReferences.getWgs84());
                    Graphic graphic = new Graphic(point, pinStarBlueSymbol);
                    graphic.setZIndex(disasterPoint.getId().intValue());
                    editAllGraphics.add(graphic);
                }
                Log.d(TAG, "所有图标个数：" + editAllGraphics.size());
                editGzGraphics.addAll(editAllGraphics);
            }
        });
    }

    private void setRoute() {
        LocationInfo locationInfo = GreenDaoManager.queryLocation();
        if (route == 0) {
            if (locationInfo != null) {
                showLocationNotice(locationInfo);
            } else {
                startLocation();
                ToastUtils.showShort(context, "定位信息开始采集");
                ivRoute.setSelected(true);
                route = 1;
            }
        } else if (route == 1) {
            showUploadNotice(locationInfo);
        }
    }

    private void startLocation() {
        GreenDaoManager.deleteAllLocation();
        LocationInfo locationInfo = new LocationInfo();
        locationInfo.setStartTime(TimeUtils.millis2String(new Date().getTime()));
        locationInfo.setUserName((String) SharedUtils.getShare(context, Constant.USER_NAME, ""));
        GreenDaoManager.insertLocation(locationInfo);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        int span = 10 * 1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        routeClient.setLocOption(option);
        routeClient.registerLocationListener(new RouteListener());
        routeClient.start();
    }

    private class RouteListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            int locType = bdLocation.getLocType();
            if (locType == BDLocation.TypeOffLineLocation || locType == BDLocation.TypeGpsLocation || locType == BDLocation.TypeNetWorkLocation) {
                String longitude = String.valueOf(bdLocation.getLongitude());
                String latitude = String.valueOf(bdLocation.getLatitude());
                Log.d(TAG, "jd:" + longitude + "/wd:" + latitude);
                LocationInfo locationInfo = GreenDaoManager.queryLocation();
                String userName = locationInfo.getUserName();
                String startTime = locationInfo.getStartTime();
                Long id = locationInfo.getId();
                String lonAndLat = locationInfo.getLonAndLat();
                LocationInfo l = new LocationInfo();
                l.setId(id);
                l.setUserName(userName);
                l.setStartTime(startTime);
                l.setEndTime(TimeUtils.millis2String(new Date().getTime()));
                if (lonAndLat == null || "".equals(lonAndLat)) {
                    l.setLonAndLat(longitude + "," + latitude);
                } else {
                    l.setLonAndLat(lonAndLat + "|" + longitude + "," + latitude);
                }
                GreenDaoManager.updateLocation(l);
            }
        }

        @Override
        public void onLocDiagnosticMessage(int locType, int diagnosticType, String diagnosticMessage) {
            if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_BETTER_OPEN_GPS) {
                ToastUtils.showShort(context, "请打开GPS");
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_BETTER_OPEN_WIFI) {
                ToastUtils.showShort(context, "建议打开WIFI提高定位经度");
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

            }
        }
    }

    private void showUploadNotice(final LocationInfo locationInfo) {
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("是否停止定位并上传定位信息？")
                .setPositiveButton("上传信息", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        routeClient.stop();
                        ivRoute.setSelected(false);
                        route = 0;
                        loginPost(locationInfo);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setNeutralButton("删除信息", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GreenDaoManager.deleteAllLocation();
                        routeClient.stop();
                        ivRoute.setSelected(false);
                        route = 0;
                    }
                }).show();
    }

    private void showLocationNotice(final LocationInfo locationInfo) {
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("检测到上次有定位信息未上传，是否现在上传？")
                .setPositiveButton("上传信息", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loginPost(locationInfo);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setNeutralButton("删除信息", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GreenDaoManager.deleteAllLocation();
                    }
                }).show();
    }

    private void loginPost(final LocationInfo locationInfo) {
        OkHttpUtils.get().url(getString(R.string.base_gz_url) + "/appdocking/login/" + userName + "/" + password + "/2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showShort(context, "当前无网络");
                    }

                    @Override
                    public void onResponse(final String response, int id) {
                        uploadLocation(locationInfo);
                    }
                });

    }

    private void uploadLocation(LocationInfo locationInfo) {
        if (locationInfo != null) {
            String userName = locationInfo.getUserName();
            String start = locationInfo.getStartTime();
            String end = locationInfo.getEndTime();
            String location = locationInfo.getLonAndLat();
            OkHttpUtils.get().url(getResources().getString(R.string.base_gz_url) + "appdocking/saveTrajectory/" + userName + "/" + location + "/" + start + "/" + end)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtils.showShort(context, "定位信息上传失败，点击按钮重新上传");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String data = object.getString("data");
                                ToastUtils.showShort(context, data);
                                GreenDaoManager.deleteAllLocation();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    private void changeBaseMap() {
        if (baseMap == 0) {
            if (NetworkUtils.isConnected()) {
                layers.clear();
                layers.add(gzYingXiangLayerHigh);
                layers.add(gzYingXiangLayer);
                baseMap = 1;
                ivChangeMap.setSelected(true);
            } else {
                ToastUtils.showShort(context, "当前无网络，无法切换影像图");
            }
        } else if (baseMap == 1) {
            if (NetworkUtils.isConnected()) {
                layers.clear();
                layers.add(gzDianZhiLayer);
                baseMap = 0;
                ivChangeMap.setSelected(false);
            } else {
                if (file.exists()) {
                    layers.clear();
                    layers.add(localeDianZhiLayer);
                    Viewpoint viewpoint = new Viewpoint(26.713526, 106.759177, 1500000);
                    mapView.setViewpointAsync(viewpoint, 2);
                    baseMap = 0;
                    ivChangeMap.setSelected(false);
                } else {
                    ToastUtils.showShort(context, "没有找到离线地图包");
                }
            }
        }
    }

    private void setAreaBack() {
        if (llAreaState) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(llArea, "x", 0, -(llArea.getWidth() - 50));
            animator.setDuration(1000);
            animator.start();
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(ivAreaBack, "rotation", 0, 180);
            animator1.setDuration(100);
            animator1.start();
            llAreaState = false;
        } else {
            ObjectAnimator animator = ObjectAnimator.ofFloat(llArea, "x", -(llArea.getWidth() - 50), 0);
            animator.setDuration(1000);
            animator.start();
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(ivAreaBack, "rotation", 180, 0);
            animator1.setDuration(100);
            animator1.start();
            llAreaState = true;
        }
    }


    private void setNarrow() {
        double mapScale = mapView.getMapScale();
        if (mapScale > 200000) {
            mapView.setViewpointScaleAsync(mapScale + 100000);
        } else if (200000 >= mapScale && mapScale > 100000) {
            mapView.setViewpointScaleAsync(mapScale + 50000);
        } else if (mapScale <= 100000 && mapScale > 50000) {
            mapView.setViewpointScaleAsync(mapScale + 25000);
        } else if (mapScale <= 50000 && mapScale > 25000) {
            mapView.setViewpointScaleAsync(mapScale + 10000);
        } else if (mapScale <= 25000 && mapScale >= 70) {
            mapView.setViewpointScaleAsync(mapScale + 1000);
        }
    }

    private void setEnlarge() {
        double mapScale = mapView.getMapScale();
        if (mapScale > 200000) {
            mapView.setViewpointScaleAsync(mapScale - 100000);
        } else if (200000 >= mapScale && mapScale > 100000) {
            mapView.setViewpointScaleAsync(mapScale - 50000);
        } else if (mapScale <= 100000 && mapScale > 50000) {
            mapView.setViewpointScaleAsync(mapScale - 25000);
        } else if (mapScale <= 50000 && mapScale > 25000) {
            mapView.setViewpointScaleAsync(mapScale - 10000);
        } else if (mapScale <= 25000 && mapScale > 1500) {
            mapView.setViewpointScaleAsync(mapScale - 1000);
        }
    }

    private void setUtilBack() {
        if (!llUtilState) {
            btnUtil.setSelected(true);
            llUtil.setVisibility(View.VISIBLE);
            llUtilState = true;
        } else {
            editGzGraphics.clear();
            editGraphicOverlay.getGraphics().clear();
            btnUtil.setSelected(false);
            clear();
            tvMeasureResult.setText("");
            llUtil.setVisibility(View.INVISIBLE);
            llUtilState = false;
        }
    }


    private void resetPosition() {
        turnOnLocation();
        WaitingDialog.createLoadingDialog(context, "正在获取位置");
    }


    public void setDrawingMode(DrawingMode drawingMode) {
        // If we try to start a new drawing before finishing our last, finish the current one
        if (mDrawingMode != DrawingMode.NONE) {
            finishDrawing();
        }
        mDrawingMode = drawingMode;
        // If the drawing mode is polyline or polygon, set the current point collection to an empty collection
        if (mDrawingMode == DrawingMode.POLYLINE || mDrawingMode == DrawingMode.POLYGON) {
            mCurrentPointCollection = new PointCollection(mapView.getSpatialReference());
        }
    }

    /**
     * Convenience method for queueing an undo or a redo event. In addition to queueing the
     * event, it will also notify the listener to enable the undo or redo button if the stack
     * was previously empty.
     *
     * @param stack the stack to which the event should be added
     * @param item  the UndoRedoItem to queue
     */
    private void queueUndoRedoItem(Stack<UndoRedoItem> stack, UndoRedoItem item) {
        // If the stack is currently empty, we should notify the listener to enable to button
        if (stack.isEmpty()) {
            // If it's the undo stack, fire the undo state changed listener
            if (stack == mUndoElementStack) {
                mListener.onUndoStateChanged(true);
                // Otherwise fire the redo state changed listener
            } else {
                mListener.onRedoStateChanged(true);
            }
        }
        // Finally, push the item to the stack
        stack.push(item);
    }

    /**
     * Convenience method for clearing the undo or redo event stack. Additionally notifies
     * the listener to disable the corresponding button.
     *
     * @param stack the stack to clear
     */
    private void clearStack(Stack<UndoRedoItem> stack) {
        stack.clear();
        // Notify the listener based on which stack was cleared
        if (stack == mUndoElementStack) {
            mListener.onUndoStateChanged(false);
        } else {
            mListener.onRedoStateChanged(false);
        }
    }


    /**
     * Clear all of the graphics on the SketchGraphicsOverlay and reset the current drawing state.
     */
    public void clear() {
        // Before clearing, finish any drawing that may currently be in progress
        finishDrawing();
        if (!mGraphics.isEmpty()) {
            queueUndoRedoItem(mUndoElementStack, new UndoRedoItem(UndoRedoItem.Event.ERASE_GRAPHICS, copyGraphics()));
            mGraphics.clear();
        }
        mDrawingMode = DrawingMode.NONE;
        mIsPolylineStarted = false;
        mCurrentPoint = null;
        mCurrentLine = null;
        mCurrentPolygon = null;
        mCurrentPointCollection = null;
        mListener.onClearStateChanged(false);
    }

    /**
     * Creates a copy of the current graphics in the SketchGraphicsOverlay. This is used to replace graphics
     * after they have been cleared.
     *
     * @return a copy of the current graphics list
     */
    private List<Graphic> copyGraphics() {
        List<Graphic> graphicsCopy = new ArrayList<>();
        for (int i = 0; i < mGraphics.size(); ++i) {
            graphicsCopy.add(mGraphics.get(i));
        }
        return graphicsCopy;
    }

    /**
     * Helper method to get the midpoint of two points
     *
     * @param a the first point
     * @param b the second point
     * @return the midpoint of the two points
     */
    private Point getMidpoint(Point a, Point b) {
        double midX = (a.getX() + b.getX()) / 2.0;
        double midY = (a.getY() + b.getY()) / 2.0;
        return new Point(midX, midY, mapView.getSpatialReference());
    }

    /**
     * Splits a line segment on the midpoint, adding a new vertex where the midpoint
     * had been and adding new midpoints before and after the new vertex.
     *
     * @param newGeometry the position of the new vertex
     */
    private void splitMidpoint(Point newGeometry) {
        // get the index of the current working graphic
        int graphicIndex = mGraphics.indexOf(mCurrentPoint);
        int pointIndex;
        // If we're drawing a polygon and splitting the final midpoint then the index in which
        // to insert the new point will be second to last
        if (mDrawingMode == DrawingMode.POLYGON && graphicIndex == mGraphics.size() - 1) {
            pointIndex = mCurrentPointCollection.size() - 1;
        } else {
            // If it's not a polygon or not the final midpoint, get the index in the point collection of
            // the point following the midpoint so the new vertex can be added before it
            Point pointAfterMidpoint = (Point) mGraphics.get(graphicIndex + 1).getGeometry();
            // Since the midpoints aren't in the point collection, get the index of the point after it
            pointIndex = mCurrentPointCollection.indexOf(pointAfterMidpoint);
        }
        // Add a new point at this index with the midpoint's new geometry
        mCurrentPointCollection.add(pointIndex, newGeometry);
        // Find the locations of the new midpoints (before and after the just added vertex point)
        Point newPreMidpoint = getMidpoint(mCurrentPointCollection.get(pointIndex - 1), newGeometry);
        Point newPostMidpoint = getMidpoint(newGeometry, mCurrentPointCollection.get(pointIndex + 1));
        // The graphic index is current pointing at the old midpoint, so add the pre-midpoint here
        // which will shift the index. Increment the counter so it points at the old midpoint again
        mGraphics.add(graphicIndex++, new Graphic(newPreMidpoint, mPolylineMidpointSymbol));
        // Add the post-midpoint at the index after the old midpoint
        mGraphics.add(graphicIndex + 1, new Graphic(newPostMidpoint, mPolylineMidpointSymbol));
        // Now that we've split and added a new vertex, the selected point is no longer a midpoint
        mIsMidpointSelected = false;
    }

    /**
     * Helper method to add a point to the polyline/polygon. Handles the work of
     * changing the working points symbol and updating the polyline/polygon geometry.
     *
     * @param point the point to add
     */
    private void addPolylinePoint(Point point) {
        Point midPoint = getMidpoint((Point) mCurrentPoint.getGeometry(), point);
        mCurrentPoint.setSymbol(mPolylineVertexSymbol);
        mCurrentLine.setGeometry(new Polyline(mCurrentPointCollection));
        mGraphics.add(new Graphic(midPoint, mPolylineMidpointSymbol));
        mCurrentPoint = new Graphic(point, mPointPlacementSymbol);
        mGraphics.add(mCurrentPoint);
        if (mDrawingMode == DrawingMode.POLYGON) {
            mCurrentPolygon.setGeometry(new Polygon(mCurrentPointCollection));
            Point polygonMidpoint = getMidpoint((Point) mCurrentPoint.getGeometry(), mCurrentPointCollection.get(0));
            mGraphics.add(new Graphic(polygonMidpoint, mPolylineMidpointSymbol));
        }
    }

    /**
     * Helper method to update the final midpoint of a polygon.
     */
    private void updatePolygonMidpoint() {
        // There will only be a final midpoint if there are at least 3 points
        if (mCurrentPointCollection.size() > 2) {
            // Get the final midpoint graphic and update its geometry with the midpoint of the final and first points
            Graphic postMidpoint = mGraphics.get(mGraphics.size() - 1);
            Point postMidpointGeometry = getMidpoint(mCurrentPointCollection.get(mCurrentPointCollection.size() - 2), mCurrentPointCollection.get(0));
            postMidpoint.setGeometry(postMidpointGeometry);
        }
    }

    /**
     * Finishes the current drawing by finalizing the working graphic[s], resetting the drawing state, and notifying
     * the listener that the drawing has finished.
     */
    private void finishDrawing() {
        // If current point is null then there is no drawing to finish
        if (mCurrentPoint != null) {
            switch (mDrawingMode) {
                case POINT:
                    // If we're drawing a point, set the symbol to the placed symbol and reset the current point
                    mCurrentPoint.setSymbol(mPointPlacedSymbol);
                    mCurrentPoint = null;
                    if (!mUndoElementStack.isEmpty()) {
                        // Remove any of the move graphic undo events. Once placed, undo should just remove the point
                        while (mUndoElementStack.peek().getEvent() == UndoRedoItem.Event.MOVE_POINT) {
                            mUndoElementStack.pop();
                        }
                    }
                    break;
                case POLYGON:
                    // If we're drawing a polygon, logic is similar to finishing a polyline, but additionally need
                    // to remove the final midpoint
                    if (mGraphics.size() > 0) {
                        mGraphics.remove(mGraphics.size() - 1);
                    }
                case POLYLINE:
                    // Set the current point to the placed vertex symbol and set the line to the placed line symbol
                    mCurrentPoint.setSymbol(mPolylineVertexSymbol);
                    mCurrentLine.setSymbol(mPolylinePlacedSymbol);
                    // The second to last graphic is the final midpoint, and we need to remove all midpoints
                    int index = 0;
                    if (mGraphics.size() > 1) {
                        index = mGraphics.size() - 2;
                    }
                    // Pop events until all the add/move polyline point events are gone (once placed, we only want to remove
                    // a polyline on undo). The final popped event will be an ADD_GRAPHIC event, which will be replaced
                    // further down by an ADD_POLYLINE event
                    if (!(mUndoElementStack.isEmpty())) {
                        UndoRedoItem.Event event;
                        do {
                            event = mUndoElementStack.pop().getEvent();
                        }
                        while (event == UndoRedoItem.Event.ADD_POLYLINE_POINT || event == UndoRedoItem.Event.MOVE_POLYLINE_POINT);

                        while (index > 0 && mGraphics.get(index).getSymbol().equals(mPolylineMidpointSymbol)) {
                            // For each add event, remove the midpoint and decrement the index
                            mGraphics.remove(index);
                            index -= 2;
                        }
                        // Push a new event indicating that we've finished a POLYLINE
                        mUndoElementStack.add(new UndoRedoItem(UndoRedoItem.Event.ADD_POLYLINE, null));
                    }
                    // Reset the boolean and working graphics
                    mIsPolylineStarted = false;
                    mCurrentPoint = null;
                    mCurrentLine = null;
                    mCurrentPolygon = null;
                    mCurrentPointCollection = null;
                    mIsMidpointSelected = false;
                    break;
            }
        }
        // Reset drawing mode and empty the redo stack
        mDrawingMode = DrawingMode.NONE;
        clearStack(mRedoElementStack);
        mListener.onDrawingFinished();
    }

    /**
     * Represents the different possible drawing modes the SketchGraphicsOverlay can be in
     */
    public enum DrawingMode {
        POINT,
        POLYLINE,
        POLYGON,
        NONE
    }

    /**
     * Represents a single action that can be undone/redone in the sketching stack
     */
    public static class UndoRedoItem {

        // Each item has an event type and optionally an object to use in undoing/redoing the action
        private Event mEvent;
        private Object mElement;

        /**
         * Creates a new UndoRedoItem with the specified event type and optional object.
         *
         * @param event   the type of event that occured
         * @param element optionally an object to help undo/redo the action
         */
        public UndoRedoItem(Event event, Object element) {
            mEvent = event;
            mElement = element;
        }

        /**
         * Gets the type of the event.
         *
         * @return the type of the event
         */
        public Event getEvent() {
            return mEvent;
        }

        /**
         * Gets the object with which to undo/redo the action (depending on the event type,
         * may be null).
         *
         * @return the object with which to undo/redo the action, or null if there is none
         */
        public Object getElement() {
            return mElement;
        }

        /**
         * Indicates different types of events that can occur.
         */
        public enum Event {
            ADD_POINT,
            MOVE_POINT,
            REMOVE_POINT,
            ADD_POLYLINE_POINT,
            MOVE_POLYLINE_POINT,
            REMOVE_POLYLINE_POINT,
            ADD_POLYLINE,
            REMOVE_POLYLINE,
            ERASE_GRAPHICS,
            REPLACE_GRAPHICS
        }

        /**
         * Represents the specific action of moving a polyline point, which additionally needs
         * to indicate if the point moved was a midpoint.
         */
        public static class MovePolylinePointElement {
            Graphic mGraphic;
            Point mPoint;
            boolean mIsMidpoint;

            /**
             * Instantiates a new MovePolylinePointElement.
             *
             * @param graphic    the graphic of the moved point
             * @param point      the position of the moved point
             * @param isMidpoint true if the moved point was a midpoint
             */
            public MovePolylinePointElement(Graphic graphic, Point point, boolean isMidpoint) {
                mGraphic = graphic;
                mPoint = point;
                mIsMidpoint = isMidpoint;
            }

            /**
             * Gets the graphic of the moved point.
             *
             * @return the graphic of the moved point
             */
            public Graphic getGraphic() {
                return mGraphic;
            }

            /**
             * Gets the position of the moved point (note this is required because the Point
             * returned by graphic.getGeometry() will have changed by reference).
             *
             * @return the position of the moved point
             */
            public Point getPoint() {
                return mPoint;
            }

            /**
             * Checks if the moved point was a midpoint.
             *
             * @return true if the moved point was a midpoint
             */
            public boolean isMidpoint() {
                return mIsMidpoint;
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        mapView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connectionReceiver);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(context)
                .setTitle("退出程序")
                .setMessage("确定要退出吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (Activity activity : MyApplication.getActivities()) {
                            if (!activity.isFinishing()) {
                                activity.finish();
                            }

                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    private DisasterPoint locationPoint;
    BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                    Log.d("cp", "断网了");
                    if (file.exists()) {
                        layers.clear();
                        layers.add(localeDianZhiLayer);
                    } else {
                        ToastUtils.showShort(context, "网络断开，离线地图未找到");
                    }
                } else {
                    Log.d("cp", "有网了");
                    if (!layers.contains(gzDianZhiLayer)) {
                        layers.clear();
                        layers.add(gzDianZhiLayer);
                    }
                }
            } else if (LOCATION_POINT.equals(intent.getAction())) {
                locationPoint = (DisasterPoint) intent.getSerializableExtra(DISASTER_POINT);
                setLocationPointOverlay(locationPoint);
            }
        }
    };

    private void setLocationPointOverlay(final DisasterPoint disasterPoint) {
        pointGraphicOverlay.getGraphics().clear();
        final BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.location_point);
        final PictureMarkerSymbol pinStarBlueSymbol = new PictureMarkerSymbol(drawable);
        pinStarBlueSymbol.setHeight(50);
        pinStarBlueSymbol.setWidth(35);
        pinStarBlueSymbol.loadAsync();
        pinStarBlueSymbol.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                Point point = new Point(disasterPoint.getDisasterLon(), disasterPoint.getDisasterLat(), SpatialReferences.getWgs84());
                Graphic graphic = new Graphic(point, pinStarBlueSymbol);
                pointGraphicOverlay.getGraphics().add(graphic);
                Viewpoint v = new Viewpoint(disasterPoint.getDisasterLat(), disasterPoint.getDisasterLon(), mapView.getMapScale());
                mapView.setViewpointAsync(v, 2);
            }
        });
    }
}
