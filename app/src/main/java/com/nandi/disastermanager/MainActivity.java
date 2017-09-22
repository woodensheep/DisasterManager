package com.nandi.disastermanager;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
import com.blankj.utilcode.util.TimeUtils;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
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
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISMapImageSublayer;
import com.esri.arcgisruntime.layers.ArcGISSceneLayer;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.loadable.LoadStatusChangedEvent;
import com.esri.arcgisruntime.loadable.LoadStatusChangedListener;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.ArcGISScene;
import com.esri.arcgisruntime.mapping.ArcGISTiledElevationSource;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.LayerList;
import com.esri.arcgisruntime.mapping.Surface;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Camera;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.DefaultSceneViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.LocationToScreenResult;
import com.esri.arcgisruntime.mapping.view.MapScaleChangedEvent;
import com.esri.arcgisruntime.mapping.view.MapScaleChangedListener;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.ViewpointChangedEvent;
import com.esri.arcgisruntime.mapping.view.ViewpointChangedListener;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.util.ListenableList;
import com.google.gson.Gson;
import com.nandi.disastermanager.dao.GreenDaoManager;
import com.nandi.disastermanager.entity.LocationInfo;
import com.nandi.disastermanager.http.ReplaceService;
import com.nandi.disastermanager.http.UpdataService;
import com.nandi.disastermanager.search.SearchActivity;
import com.nandi.disastermanager.search.entity.DisasterPoint;
import com.nandi.disastermanager.search.entity.StaticsInfo;
import com.nandi.disastermanager.utils.AppUtils;
import com.nandi.disastermanager.utils.Constant;
import com.nandi.disastermanager.utils.LogUtils;
import com.nandi.disastermanager.utils.SharedUtils;
import com.nandi.disastermanager.utils.SketchGraphicsOverlayEventListener;
import com.nandi.disastermanager.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
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
    @BindView(R.id.undoButton)
    ImageButton mUndoButton;
    @BindView(R.id.redoButton)
    ImageButton mRedoButton;
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
    private boolean llAreaState = true;
    private boolean llUtilState = false;
    ArcGISTiledLayer gzYingXiangLayer;
    ArcGISTiledLayer gzYingXiangLayerHigh;
    ArcGISTiledLayer gzDianZhiLayer;
    private ArcGISMap map;
    private LayerList layers;
    private GraphicsOverlay gzPointGraphicOverlay;
    private GraphicsOverlay meGraphicOverlay;
    private ListenableList<Graphic> gzGraphics;
    private List<Graphic> allGraphics = new ArrayList<>();

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
    private boolean downloadSuccess = false;
    private ArcGISTiledElevationSource gzElevationSource;
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
    private LocationListener locationListener;
    private double meLongitude = 0;
    private double meLatitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MyApplication.getActivities().add(this);
        context = this;
        id = (String) SharedUtils.getShare(context, Constant.AREA_ID, "");
        level = (String) SharedUtils.getShare(context, Constant.LEVEL, "");
        setLine();
        checkUpdate();
//        bindAccount();//绑定推送账号，暂时不用
        initUtilData();
        loginPost((String) SharedUtils.getShare(context, Constant.USER_NAME, ""), (String) SharedUtils.getShare(context, Constant.PASSWORD, ""));
        gzDianZhiLayer = new ArcGISTiledLayer(getResources().getString(R.string.guizhou_dianzi_url));
        gzYingXiangLayer = new ArcGISTiledLayer(getResources().getString(R.string.guizhou_yingxiang_url));
        gzYingXiangLayerHigh = new ArcGISTiledLayer(getResources().getString(R.string.guizhou_yingxiang_url_1));
        map = new ArcGISMap();
        layers=map.getOperationalLayers();
        gzPointGraphicOverlay = new GraphicsOverlay();
        mGraphicsOverlay = new GraphicsOverlay();
        meGraphicOverlay = new GraphicsOverlay();
        mGraphics = mGraphicsOverlay.getGraphics();
        gzGraphics = gzPointGraphicOverlay.getGraphics();
        ListenableList<GraphicsOverlay> graphicsOverlays = mapView.getGraphicsOverlays();
        graphicsOverlays.add(mGraphicsOverlay);
        graphicsOverlays.add(gzPointGraphicOverlay);
        graphicsOverlays.add(meGraphicOverlay);
        mapView.setMap(map);
        layers.add(gzDianZhiLayer);
        Viewpoint viewpoint=new Viewpoint(26.713526, 106.759177, 1500000);
        map.setInitialViewpoint(viewpoint);
        mUndoButton.setClickable(false);
        mUndoButton.setEnabled(false);
        mRedoButton.setClickable(false);
        mRedoButton.setEnabled(false);
        mClearButton.setClickable(false);
        mClearButton.setEnabled(false);
        locationClient = new LocationClient(getApplicationContext());
        routeClient = new LocationClient(getApplicationContext());
        setListeners();
        if (!checkDownload()) {
            setStatics();
        } else {
            initStaData();
        }
        startUpdateService();
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
                    }
                });

    }

    /*开启下载*/
    private boolean checkDownload() {
        List<DisasterPoint> disasterPoints = GreenDaoManager.queryDisasterData();
        boolean isChangeUser = (boolean) SharedUtils.getShare(context, Constant.CHANGE_USER, false);
        Log.d(TAG, "是否更换账号" + isChangeUser);
        return disasterPoints.size() > 0 && isChangeUser;
    }

    private void setLine() {
        if ("1".equals(level)) {
            llRoute.setVisibility(View.VISIBLE);
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
                            if ("1".equals(aStatic)) {
                                showNoticeDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /*下载APK*/
    private void showNoticeDialog() {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("版本更新");
        builder.setMessage("发现新版本,是否立即下载？");
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent service = new Intent(MainActivity.this, UpdataService.class);
                startService(service);
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
        cloudPushService.bindAccount((String) SharedUtils.getShare(context, "loginname", ""), new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                LogUtils.d(TAG, "绑定账号成功！/" + s);
            }

            @Override
            public void onFailed(String s, String s1) {
                LogUtils.d(TAG, "绑定账号失败！/" + s);

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

    public void undoClick(View v) {
        undo();
    }

    public void redoClick(View v) {
        redo();
    }

    public void clearClick(View v) {
        clear();
    }

    private class MySketchGraphicsOverlayEventListener implements SketchGraphicsOverlayEventListener {

        @Override
        public void onUndoStateChanged(boolean undoEnabled) {
            // Set the undo button's enabled/disabled state based on the event boolean
            mUndoButton.setEnabled(undoEnabled);
            mUndoButton.setClickable(undoEnabled);
        }

        @Override
        public void onRedoStateChanged(boolean redoEnabled) {
            // Set the redo button's enabled/disabled state based on the event boolean
            mRedoButton.setEnabled(redoEnabled);
            mRedoButton.setClickable(redoEnabled);
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
        ivRoute.setOnClickListener(this);
        ivLocation.setOnClickListener(this);
        ivChangeMap.setOnClickListener(this);
        llEnlarge.setOnClickListener(this);
        llNarrow.setOnClickListener(this);
        llCompass.setOnClickListener(this);
        ivSearchMain.setOnClickListener(this);
        btnUtil.setOnClickListener(this);
        ivAreaBack.setOnClickListener(this);
        llUserMessage.setOnClickListener(this);
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
                final ListenableFuture<IdentifyGraphicsOverlayResult> meIdentify = mapView.identifyGraphicsOverlayAsync(meGraphicOverlay, screenPoint, 10.0, false);
                meIdentify.addDoneListener(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            IdentifyGraphicsOverlayResult identifyResult = meIdentify.get();
                            List<Graphic> graphics = identifyResult.getGraphics();
                            if (graphics.size() > 0) {
                                // TODO: 2017/9/22 查看周围隐患点
                            }
                        } catch (InterruptedException | ExecutionException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public void onLongPress(MotionEvent event) {
                // Long press finishes a drawing
                if (llUtil.getVisibility() == View.VISIBLE) {

                    finishDrawing();
                }
            }

            @Override
            public boolean onScroll(MotionEvent from, MotionEvent to, float distanceX, float distanceY) {
                if (llUtil.getVisibility() == View.VISIBLE) {
                    boolean callSuper = true;
                    if (mCurrentPoint != null) {
                        android.graphics.Point currentPoint = mapView.locationToScreen((Point) mCurrentPoint.getGeometry());
                        android.graphics.Point fromPoint = new android.graphics.Point((int) from.getX(), (int) from.getY());
                        int dx = currentPoint.x - fromPoint.x;
                        int dy = currentPoint.y - fromPoint.y;
                        int distance = (int) Math.sqrt((dx * dx) + (dy * dy));
                        if (distance < 20) {
                            callSuper = false;
                            android.graphics.Point toPoint = new android.graphics.Point((int) to.getX(), (int) to.getY());
                            Point oldGeometry = (Point) mCurrentPoint.getGeometry();
                            Point oldPointCopy = new Point(oldGeometry.getX(), oldGeometry.getY(), mapView.getSpatialReference());
                            Point newGeometry = mapView.screenToLocation(toPoint);
                            if (!mVertexDragStarted) {
                                if (mDrawingMode == DrawingMode.POINT) {
                                    queueUndoRedoItem(mUndoElementStack, new UndoRedoItem(UndoRedoItem.Event.MOVE_POINT, oldPointCopy));
                                } else {
                                    queueUndoRedoItem(mUndoElementStack, new UndoRedoItem(UndoRedoItem.Event.MOVE_POLYLINE_POINT,
                                            new UndoRedoItem.MovePolylinePointElement(mCurrentPoint, oldPointCopy, mIsMidpointSelected)));
                                }
                            }
                            if (mDrawingMode == DrawingMode.POLYLINE || mDrawingMode == DrawingMode.POLYGON) {
                                int graphicIndex = mGraphics.indexOf(mCurrentPoint);
                                int pointIndex;
                                if (mIsMidpointSelected && !mVertexDragStarted) {
                                    splitMidpoint(newGeometry);
                                } else {
                                    pointIndex = mCurrentPointCollection.indexOf(mCurrentPoint.getGeometry());
                                    mCurrentPointCollection.set(pointIndex, newGeometry);
                                    Graphic preMidpoint = (pointIndex == 0) ? null : mGraphics.get(graphicIndex - 1);
                                    if (preMidpoint != null) {
                                        Point preMidpointGeometry = getMidpoint(mCurrentPointCollection.get(pointIndex - 1), newGeometry);
                                        preMidpoint.setGeometry(preMidpointGeometry);
                                    }
                                    Graphic postMidpoint = (pointIndex == mCurrentPointCollection.size() - 1) ? null : mGraphics.get(graphicIndex + 1);
                                    if (postMidpoint != null) {
                                        Point postMidpointGeometry = getMidpoint(newGeometry, mCurrentPointCollection.get(pointIndex + 1));
                                        postMidpoint.setGeometry(postMidpointGeometry);
                                    }
                                    if (mDrawingMode == DrawingMode.POLYGON) {
                                        if (pointIndex == 0 || pointIndex == mCurrentPointCollection.size() - 2) {
                                            if (pointIndex == 0) {
                                                mCurrentPointCollection.set(mCurrentPointCollection.size() - 1, newGeometry);
                                            }
                                            updatePolygonMidpoint();
                                        }
                                        mCurrentPolygon.setGeometry(new Polygon(mCurrentPointCollection));
                                    }
                                    mCurrentLine.setGeometry(new Polyline(mCurrentPointCollection));
                                }
                            }
                            mVertexDragStarted = true;
                            mCurrentPoint.setGeometry(newGeometry);
                            clearStack(mRedoElementStack);
                        }
                    }
                    if (callSuper) {
                        super.onScroll(from, to, distanceX, distanceY);
                    }
                }
                return super.onScroll(from, to, distanceX, distanceY);
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

    private void showDisasterInfo(DisasterPoint disasterPoint) {
        // TODO: 2017/9/22 显示隐患点信息
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                List<DisasterPoint> disasterPoints = GreenDaoManager.queryDisasterData();
                if (disasterPoints.size() == 0) {
                    if (downloadSuccess) {
                        //// TODO: 2017/9/20
                    } else {
                        ToastUtils.showShort(context, "正在加载请稍候...");
                    }
                } else {
                    Intent intent = new Intent(context, SearchActivity.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("LEVEL", level);
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

    private void setLocation() {
        if (location == 0) {
            turnOnLocation();
        } else if (location == 1) {
            showClearNotice();
        }
    }

    private void showClearNotice() {
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("是否要清除定位及所有隐患点标记？")
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
                        meGraphicOverlay.getGraphics().clear();
                    }
                }).show();
    }

    private void turnOnLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("WGS84");
        option.setScanSpan(0);
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
            meLongitude = bdLocation.getLongitude();
            meLatitude = bdLocation.getLatitude();
            Log.d(TAG, "定位信息：" + meLongitude + "," + meLatitude);
            setLocationOverlay(meLongitude, meLatitude);
            ivLocation.setSelected(true);
            location = 1;
            locationClient.unRegisterLocationListener(locationListener);
            locationClient.stop();
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
            }
        });
    }

    private void setDisasterOverlay(double longitude, double latitude) {// FIXME: 2017/9/22 过滤符合条件的隐患点
        disasterOverlay.clear();
        List<DisasterPoint> disasterPoints = GreenDaoManager.queryDisasterData();
        int range = (int) SharedUtils.getShare(context, Constant.DISASTER_RANGE, 10 * 1000);
        for (DisasterPoint disasterPoint : disasterPoints) {
            double disasterLon = disasterPoint.getDisasterLon();
            double disasterLat = disasterPoint.getDisasterLat();
            double distance = AppUtils.getDistance(106.67564, 26.8720671618, disasterLon, disasterLat);
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

    private void setOverlay() {
        allGraphics.clear();
        final BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.huapo);
        final PictureMarkerSymbol pinStarBlueSymbol = new PictureMarkerSymbol(drawable);
        pinStarBlueSymbol.setHeight(40);
        pinStarBlueSymbol.setWidth(20);
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
//            double currentLon = bdLocation.getLongitude();
//            double currentLat = bdLocation.getLatitude();
//            String lon = (String) SharedUtils.getShare(context, Constant.SAVE_LON, "0");
//            String lat = (String) SharedUtils.getShare(context, Constant.SAVE_LAT, "0");
//            double lastLon = Double.parseDouble(lon);
//            double lastLat = Double.parseDouble(lat);
//            double distance = AppUtils.getDistance(currentLon, currentLat, lastLon, lastLat);

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
//            SharedUtils.putShare(context, Constant.SAVE_LON, bdLocation.getLongitude() + "");
//            SharedUtils.putShare(context, Constant.SAVE_LAT, bdLocation.getLatitude() + "");
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
                        uploadLocation(locationInfo);
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
                        uploadLocation(locationInfo);
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
            layers.clear();
            layers.add(gzYingXiangLayerHigh);
            layers.add(gzYingXiangLayer);
            Viewpoint viewpoint=new Viewpoint(26.713526, 106.759177, 1500000);
            mapView.setViewpointAsync(viewpoint,2);
            baseMap = 1;
            ivChangeMap.setSelected(true);
        } else if (baseMap == 1) {
            layers.clear();
            layers.add(gzDianZhiLayer);
            Viewpoint viewpoint=new Viewpoint(26.713526, 106.759177, 1500000);
            mapView.setViewpointAsync(viewpoint,2);
            baseMap = 0;
            ivChangeMap.setSelected(false);
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
        mapView.setViewpointScaleAsync(mapScale-100000);
    }

    private void setEnlarge() {
        double mapScale = mapView.getMapScale();
        mapView.setViewpointScaleAsync(mapScale+100000);
    }

    private void setUtilBack() {
        if (!llUtilState) {
            btnUtil.setSelected(true);
            llUtil.setVisibility(View.VISIBLE);
            llUtilState = true;
        } else {
            btnUtil.setSelected(false);
            clear();
            tvMeasureResult.setText("");
            llUtil.setVisibility(View.INVISIBLE);
            llUtilState = false;
        }
    }


    private void resetPosition() {
        Viewpoint viewpoint=new Viewpoint(26.713526, 106.759177, 1500000);
        mapView.setViewpointAsync(viewpoint,2);
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
     * Undo the last event that took place.
     */
    public void undo() {
        // Handle an undo event, popping an event from the undo stack and pushing a new event to the redo stack
        handleUndoRedoEvent(mUndoElementStack, mRedoElementStack);
    }

    /**
     * Redo the action previously undone with a call to undo().
     */
    public void redo() {
        // Handle an redo event, popping an event from the redo stack and pushing a new event to the undo stack
        handleUndoRedoEvent(mRedoElementStack, mUndoElementStack);
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
     * This method handles performing an undo or redo event. An event will be popped from the specified
     * stack and an opposite event type (to undo/redo that) will be pushed into the other stack.
     *
     * @param from the stack from which to pop an event
     * @param to   the stack in which to push the opposing event
     */
    @SuppressWarnings("unchecked")
    private void handleUndoRedoEvent(Stack<UndoRedoItem> from, Stack<UndoRedoItem> to) {
        // index is used in a couple places so define it here
        int index, pointIndex;
        List<Graphic> graphics;
        if (!from.isEmpty()) {
            UndoRedoItem item = from.pop();
            // If this was the last event in the stock, notify the listener to disable the corresponding button
            if (from.isEmpty()) {
                if (from == mUndoElementStack) {
                    // disable to selected drawing mode
                    mListener.onDrawingFinished();
                    mListener.onUndoStateChanged(false);
                } else {
                    mListener.onRedoStateChanged(false);
                }
            }
            // Check whether the graphics list was empty before we process the event
            boolean graphicsWasEmpty = mGraphics.isEmpty();
            switch (item.getEvent()) {
                // If the event was adding a graphic, then the action taken here is to remove the graphic
                case ADD_POINT:
                    // Get the graphic[s] previously added and remove them from the graphics list
                    graphics = (List<Graphic>) item.getElement();
                    mGraphics.removeAll(graphics);
                    // Queue a new event indicating that we've removed the graphic[s]
                    queueUndoRedoItem(to, new UndoRedoItem(UndoRedoItem.Event.REMOVE_POINT, graphics));
                    mIsMidpointSelected = false;
                    mIsPolylineStarted = false;
                    mCurrentPoint = null;
                    mCurrentPointCollection = new PointCollection(mapView.getSpatialReference());
                    break;
                // If the event was removing a graphic, then the action taken here is to add it back
                case REMOVE_POINT:
                    // Readd the graphic[s] previously removed.
                    graphics = (List<Graphic>) item.getElement();
                    mGraphics.addAll(graphics);
                    // Queue a new event indicating that we've added the graphic[s]
                    queueUndoRedoItem(to, new UndoRedoItem(UndoRedoItem.Event.ADD_POINT, graphics));
                    break;
                // If the event was adding a polyline point, the action taken here is to remove the last point added
                case ADD_POLYLINE_POINT:
                    // Get the index of the current point (which will be the one most recently added)
                    pointIndex = (mDrawingMode == DrawingMode.POLYGON) ?
                            mCurrentPointCollection.size() - 2 : mCurrentPointCollection.size() - 1;
                    // Remove it from the point collection and update the current line (and polygon if applicable)
                    Point p = mCurrentPointCollection.remove(pointIndex);
                    mCurrentLine.setGeometry(new Polyline(mCurrentPointCollection));
                    if (mDrawingMode == DrawingMode.POLYGON) {
                        mCurrentPolygon.setGeometry(new Polygon(mCurrentPointCollection));
                    }
                    // Undoing an add point always removes the final point
                    index = mGraphics.size() - 1;
                    // Remove the point, and remove the midpoint before it
                    mGraphics.remove(index--);
                    mGraphics.remove(index--);
                    // If we're drawing a polygon, we also need to update the final midpoint position
                    if (mDrawingMode == DrawingMode.POLYGON) {
                        updatePolygonMidpoint();
                        // If we are down to only 1 point (size will be 2 because 1st and final point are duplicates)
                        // Then we want to remove the final midpoint
                        if (mCurrentPointCollection.size() == 2) {
                            mGraphics.remove(index--);
                            mCurrentPoint = mGraphics.get(index);
                        } else {
                            // Otherwise just set the point before the final midpoint as current point
                            mCurrentPoint = mGraphics.get(index - 1);
                        }
                    } else {
                        // If we're drawing a polyline then the current point will be the final point (which is where
                        // index will now be pointing)
                        mCurrentPoint = mGraphics.get(index);
                    }
                    // Change the symbol to the placement symbol
                    mCurrentPoint.setSymbol(mPointPlacementSymbol);
                    // Queue a new event indicating that we've removed a polyline point
                    queueUndoRedoItem(to, new UndoRedoItem(UndoRedoItem.Event.REMOVE_POLYLINE_POINT, p));
                    break;
                // If the event was moving a polyline point, the action taken here is to move it back
                case MOVE_POLYLINE_POINT:
                    // Get the corresponding MovePolylinePointElement
                    UndoRedoItem.MovePolylinePointElement element = (UndoRedoItem.MovePolylinePointElement) item.getElement();
                    // Queue a new event indicating a polyline point move with the necessary information
                    queueUndoRedoItem(to, new UndoRedoItem(UndoRedoItem.Event.MOVE_POLYLINE_POINT,
                            new UndoRedoItem.MovePolylinePointElement(mCurrentPoint, (Point) mCurrentPoint.getGeometry(), element.isMidpoint())));
                    // Get the old Graphic of the point that was moved
                    Graphic oldGraphic = element.getGraphic();
                    // Get the previous point position
                    Point oldPoint = element.getPoint();
                    // Find the index of the moved point. Since we have complete control over how we're adding the undo elements,
                    // we can safely assume here that oldGraphic.getGeometry() is a Point. However, proper practice (here and other
                    // places) would be to check that the geometry is an instanceof Point before casting.
                    pointIndex = mCurrentPointCollection.indexOf(oldGraphic.getGeometry());
                    // Find the index of the moved graphic
                    index = mGraphics.indexOf(oldGraphic);
                    // Set the current working point's symbol to a placed vertex symbol before switching
                    mCurrentPoint.setSymbol(mPolylineVertexSymbol);
                    // Change our current working point to the old moved graphic
                    mCurrentPoint = mGraphics.get(index);
                    // Set it's symbol to the placement symbol
                    mCurrentPoint.setSymbol(mPointPlacementSymbol);
                    // If the element is/was a midpoint, we need to handle adding/removing surrounding midpoints
                    if (element.isMidpoint()) {
                        Point newGeometry = oldPoint;
                        // If this is an undo
                        if (from == mUndoElementStack) {
                            // Go back to having a midpoint selected
                            mIsMidpointSelected = true;
                            // Remove the current point from the point collection (since it's going back to being a midpoint)
                            mCurrentPointCollection.remove(pointIndex);
                            // Remove the midpoint before this point. Since this shifts the index, the index will now be
                            // for the midpoint after our point
                            mGraphics.remove(index - 1);
                            // So remove that index and then decrement to get the index back at our graphic
                            mGraphics.remove(index--);
                            // Our point will now be a midpoint so get the midpoint between the points before and after it and set it
                            Point endPoint = (mDrawingMode == DrawingMode.POLYGON && index == mGraphics.size() - 1) ?
                                    mCurrentPointCollection.get(mCurrentPointCollection.size() - 1) : (Point) mGraphics.get(index + 1).getGeometry();
                            newGeometry = getMidpoint((Point) mGraphics.get(index - 1).getGeometry(), endPoint);
                        } else {
                            // If it's a redo, then we need to make a new vertex point and add new midpoints before and after it
                            splitMidpoint(newGeometry);
                        }
                        // Finally set the current point's position
                        mCurrentPoint.setGeometry(newGeometry);
                    } else {
                        // If it wasn't a midpoint, then change the point's position within the point collection and update the
                        // graphic's geometry
                        mCurrentPointCollection.set(pointIndex, oldPoint);
                        mCurrentPoint.setGeometry(oldPoint);
                        // If this isn't the first point, adjust the midpoint's position before it
                        if (pointIndex != 0) {
                            Point preMidpoint = getMidpoint(mCurrentPointCollection.get(pointIndex - 1), oldPoint);
                            mGraphics.get(index - 1).setGeometry(preMidpoint);
                        }
                        // If this isn't the last point, adjust the midpoints position after it
                        if (pointIndex != mCurrentPointCollection.size() - 1) {
                            Point postMidpoint = getMidpoint(oldPoint, mCurrentPointCollection.get(pointIndex + 1));
                            mGraphics.get(index + 1).setGeometry(postMidpoint);
                        }
                    }
                    if (mDrawingMode == DrawingMode.POLYGON) {
                        // If we're moving the first point of a polygon, we need to replicate that change
                        // in the final point as well and update the final midpoint
                        if (pointIndex == 0) {
                            mCurrentPointCollection.set(mCurrentPointCollection.size() - 1, oldPoint);
                            updatePolygonMidpoint();
                        }
                        // In either case, update the polygon's geometry
                        mCurrentPolygon.setGeometry(new Polygon(mCurrentPointCollection));
                    }
                    // Update the line's geometry
                    mCurrentLine.setGeometry(new Polyline(mCurrentPointCollection));
                    break;
                // If the event was removing a polyline point, the action taken here is to add it back
                case REMOVE_POLYLINE_POINT:
                    // Get the point that was removed, and add it back to the point collection
                    Point point = (Point) item.getElement();
                    if (mDrawingMode == DrawingMode.POLYGON) {
                        // If adding back to a polygon, remove the final midpoint so it can be readded
                        if (mCurrentPointCollection.size() > 2) {
                            mGraphics.remove(mGraphics.size() - 1);
                        }
                        // Add it at the second to last position
                        mCurrentPointCollection.add(mCurrentPointCollection.size() - 1, point);
                    } else {
                        // If just a line, add it in the final position
                        mCurrentPointCollection.add(point);
                    }
                    addPolylinePoint(point);
                    // Queue a new event indicating that we've added a polyline point
                    to.add(new UndoRedoItem(UndoRedoItem.Event.ADD_POLYLINE_POINT, null));
                    break;
                // If the event was finishing a polyline, the action taken here is to remove the whole polyline
                case ADD_POLYLINE:
                    // Create a new graphics list and add to it all the pieces of the polyline, so we can add it back with a redo
                    graphics = new ArrayList<>();
                    index = mGraphics.size() - 1;
                    // Add all of the points of the polyline
                    while (index > 0 && !(mGraphics.get(index).getGeometry() instanceof Polyline)) {
                        graphics.add(0, mGraphics.remove(index--));
                    }
                    // Add the polyline itself
                    graphics.add(0, mGraphics.remove(index--));
                    // If removing a polygon, also add the polygon
                    if (index > -1 && mGraphics.get(index).getGeometry() instanceof Polygon) {
                        graphics.add(0, mGraphics.remove(index));
                    }
                    // Queue a new event indicating that we've removed a polyline
                    queueUndoRedoItem(to, new UndoRedoItem(UndoRedoItem.Event.REMOVE_POLYLINE, graphics));
                    break;
                // If the event was removing a polyline, the action taken here is to add it back
                case REMOVE_POLYLINE:
                    // Get the graphics that were previously removed
                    graphics = (List<Graphic>) item.getElement();
                    // Add them all to the list of graphics
                    mGraphics.addAll(graphics);
                    // Queue a new event indicating that we've added a polyline
                    queueUndoRedoItem(to, new UndoRedoItem(UndoRedoItem.Event.ADD_POLYLINE, null));
                    break;
                // If the event was moving a point, the action taken here is to move it back
                case MOVE_POINT:
                    if (mCurrentPoint != null) {
                        // Queue a new event indicating that we moved the point, with its current geometry before we change it
                        queueUndoRedoItem(to, new UndoRedoItem(UndoRedoItem.Event.MOVE_POINT, mCurrentPoint.getGeometry()));
                        // Set the geometry back
                        mCurrentPoint.setGeometry((Geometry) item.getElement());
                    }
                    break;
                // If the event was erasing all graphics, the action taken here is to put them all back
                case ERASE_GRAPHICS:
                    // Add all the graphics back
                    mGraphics.addAll((List<Graphic>) item.getElement());
                    // Queue a new event indicating that we've replaced all the graphics
                    queueUndoRedoItem(to, new UndoRedoItem(UndoRedoItem.Event.REPLACE_GRAPHICS, null));
                    break;
                // If the event was replacing all the graphics, the action taken here is to clear them all
                case REPLACE_GRAPHICS:
                    // Queue a new event indicating that we've erased the graphics
                    queueUndoRedoItem(to, new UndoRedoItem(UndoRedoItem.Event.ERASE_GRAPHICS, copyGraphics()));
                    // Erase all graphics
                    mGraphics.clear();
                    break;
            }
            boolean graphicsIsEmpty = mGraphics.isEmpty();
            // If the graphic list was previously empty and now it's not, notify the listener to enable
            // the clear button
            if (graphicsWasEmpty && !graphicsIsEmpty) {
                mListener.onClearStateChanged(true);
                // If previously non empty and now it is, notify the listener to disable the clear button
            } else if (!graphicsWasEmpty && graphicsIsEmpty) {
                mListener.onDrawingFinished();
                mListener.onClearStateChanged(false);
            }
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
}
