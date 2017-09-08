package com.nandi.disastermanager;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
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
import com.esri.arcgisruntime.layers.ArcGISSceneLayer;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.loadable.LoadStatusChangedEvent;
import com.esri.arcgisruntime.loadable.LoadStatusChangedListener;
import com.esri.arcgisruntime.mapping.ArcGISScene;
import com.esri.arcgisruntime.mapping.ArcGISTiledElevationSource;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.ElevationSource;
import com.esri.arcgisruntime.mapping.LayerList;
import com.esri.arcgisruntime.mapping.Surface;
import com.esri.arcgisruntime.mapping.view.Camera;
import com.esri.arcgisruntime.mapping.view.DefaultSceneViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.LocationToScreenResult;
import com.esri.arcgisruntime.mapping.view.SceneView;
import com.esri.arcgisruntime.mapping.view.ViewpointChangedEvent;
import com.esri.arcgisruntime.mapping.view.ViewpointChangedListener;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.UniqueValueRenderer;
import com.esri.arcgisruntime.util.ListenableList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nandi.disastermanager.adapter.RcPersonAdapter;
import com.nandi.disastermanager.adapter.RcPhotoAdapter;
import com.nandi.disastermanager.adapter.RcSearchPersonAdapter;
import com.nandi.disastermanager.adapter.RcSearchPlaceAdapter;
import com.nandi.disastermanager.entity.DetailBaseInfo;
import com.nandi.disastermanager.entity.DetailDisCard;
import com.nandi.disastermanager.entity.DetailHeCard;
import com.nandi.disastermanager.entity.DetailPersonInfo;
import com.nandi.disastermanager.entity.DetailPhoto;
import com.nandi.disastermanager.entity.DetailPnInfo;
import com.nandi.disastermanager.entity.DisasterByStateInfo;
import com.nandi.disastermanager.entity.DisasterDetailInfo;
import com.nandi.disastermanager.entity.DisasterPoint;
import com.nandi.disastermanager.entity.EquipmentLocation;
import com.nandi.disastermanager.entity.EquipmentType1;
import com.nandi.disastermanager.entity.EquipmentType11;
import com.nandi.disastermanager.entity.EquipmentType14;
import com.nandi.disastermanager.entity.EquipmentType19;
import com.nandi.disastermanager.entity.EquipmentType20;
import com.nandi.disastermanager.entity.EquipmentType22;
import com.nandi.disastermanager.entity.EquipmentType23;
import com.nandi.disastermanager.entity.EquipmentType25;
import com.nandi.disastermanager.entity.EquipmentType26;
import com.nandi.disastermanager.entity.EquipmentType3;
import com.nandi.disastermanager.entity.KuangxuanInfo;
import com.nandi.disastermanager.entity.PersonDHInfo;
import com.nandi.disastermanager.entity.PersonFZInfo;
import com.nandi.disastermanager.entity.PersonInfo;
import com.nandi.disastermanager.entity.PersonLocation;
import com.nandi.disastermanager.entity.PersonType;
import com.nandi.disastermanager.entity.PersonZSInfo;
import com.nandi.disastermanager.entity.SearchPerson;
import com.nandi.disastermanager.entity.SearchPlace;
import com.nandi.disastermanager.entity.SingleEquipment;
import com.nandi.disastermanager.entity.TabDisasterInfo;
import com.nandi.disastermanager.entity.personLocationInfo;
import com.nandi.disastermanager.http.EquipmentOkhttp;
import com.nandi.disastermanager.ui.CircleBar;
import com.nandi.disastermanager.ui.MyRadioGroup;
import com.nandi.disastermanager.ui.WaitingDialog;
import com.nandi.disastermanager.utils.DateTimePickUtil;
import com.nandi.disastermanager.utils.PermissionUtils;
import com.nandi.disastermanager.utils.SketchGraphicsOverlayEventListener;
import com.nandi.disastermanager.videocall.helloanychat.VideoCallActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "MainActivity";
    @BindView(R.id.iv_area_back)
    ImageView ivAreaBack;
    @BindView(R.id.ll_area)
    LinearLayout llArea;
    @BindView(R.id.iv_data_back)
    ImageView ivDataBack;
    @BindView(R.id.iv_dangerpoint_more)
    ImageView ivDangerpointMore;
    @BindView(R.id.rg_rainfall)
    RadioGroup rgRainfall;
    @BindView(R.id.iv_staff_more)
    ImageView ivStaffMore;
    @BindView(R.id.rg_dangerpoint)
    RadioGroup rgDangerpoint;
    @BindView(R.id.iv_equipment_more)
    ImageView ivEquipmentMore;
    @BindView(R.id.rg_staff)
    RadioGroup rgStaff;
    @BindView(R.id.iv_rainfall_more)
    ImageView ivRainfallMore;
    @BindView(R.id.rg_equipment)
    RadioGroup rgEquipment;
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.ll_dangerpoint)
    LinearLayout llDangerpoint;
    @BindView(R.id.ll_staff)
    LinearLayout llStaff;
    @BindView(R.id.ll_equipment)
    LinearLayout llEquipment;
    @BindView(R.id.ll_rainfall)
    LinearLayout llRainfall;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.sceneView)
    SceneView sceneView;
    @BindView(R.id.rb_ssyl)
    RadioButton rbSsyl;
    @BindView(R.id.rb_yldzx)
    RadioButton rbYldzx;
    @BindView(R.id.rb_disaster_point)
    RadioButton rbDisasterPoint;
    @BindView(R.id.rb_qcqf_person)
    RadioButton rbQcqfPerson;
    @BindView(R.id.rb_zs_person)
    RadioButton rbZsPerson;
    @BindView(R.id.rb_pq_person)
    RadioButton rbPqPerson;
    @BindView(R.id.rb_dhz_person)
    RadioButton rbDhzPerson;
    @BindView(R.id.iv_qxsy)
    ImageView ivQxsy;
    @BindView(R.id.rb_jinqiao)
    RadioButton rbJinqiao;
    @BindView(R.id.rb_shilin)
    RadioButton rbShilin;
    @BindView(R.id.rg_qxsy)
    RadioGroup rgQxsy;
    @BindView(R.id.ll_qxsy)
    LinearLayout llQxsy;
    @BindView(R.id.rb_state_point_0)
    RadioButton rbStatePoint0;
    @BindView(R.id.rb_state_point_2)
    RadioButton rbStatePoint2;
    @BindView(R.id.rb_state_point_3)
    RadioButton rbStatePoint3;
    @BindView(R.id.rb_state_point_1)
    RadioButton rbStatePoint1;
    @BindView(R.id.rb_equipment_jiance)
    RadioButton rbEquipmentJiance;
    @BindView(R.id.rb_equipment_laba)
    RadioButton rbEquipmentLaba;
    @BindView(R.id.rb_equipment_yingji)
    RadioButton rbEquipmentYingji;
    @BindView(R.id.rb_equipment_fengsu)
    RadioButton rbEquipmentFengsu;
    @BindView(R.id.iv_luopan)
    ImageView ivLuopan;
    @BindView(R.id.iv_xz_more)
    ImageView ivXzMore;
    @BindView(R.id.ll_xingzheng)
    LinearLayout llXingzheng;
    @BindView(R.id.rb_qxyj)
    RadioButton rbQxyj;
    @BindView(R.id.rb_qxyb)
    RadioButton rbQxyb;
    @BindView(R.id.rg_xingzheng)
    RadioGroup rgXingzheng;
    @BindView(R.id.pie_chart)
    CircleBar pieChart;
    @BindView(R.id.iv_search_main)
    ImageView ivSearchMain;

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
    @BindView(R.id.tv_measure_result)
    TextView tvMeasureResult;
    @BindView(R.id.ll_util)
    LinearLayout llUtil;
    @BindView(R.id.btn_util)
    Button btnUtil;
    @BindView(R.id.tv_disaster_number)
    TextView tvDisasterNumber;

    @BindView(R.id.tv_equipment_number)
    TextView tvEquipmentNumber;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.tv_zhushou_number)
    TextView tvZhushouNumber;
    @BindView(R.id.tv_jiance_number)
    TextView tvJianceNumber;
    @BindView(R.id.tv_pianqu_number)
    TextView tvPianquNumber;
    @BindView(R.id.tv_dhz_number)
    TextView tvDhzNumber;
    @BindView(R.id.tv_qcqf_number)
    TextView tvQcqfNumber;
    @BindView(R.id.btn_util_detail)
    Button btnUtilDetail;
    @BindView(R.id.iv_enlarge)
    ImageButton ivEnlarge;
    @BindView(R.id.iv_narrow)
    ImageButton ivNarrow;
    @BindView(R.id.tv_scale)
    TextView tvScale;

    private boolean llAreaState = false;
    private boolean llDataState = false;
    private boolean llUtilState = false;
    private int llMoreState = -1;
    private int llMoreStateBefore = -1;
    private View view;

    ArcGISMapImageLayer lowImageLayer;
    ArcGISMapImageLayer highImageLayer;
    ArcGISMapImageLayer vectorLayer;
    ArcGISMapImageLayer dengZXLayer;
    ArcGISMapImageLayer dianziLayer;
    ArcGISMapImageLayer ssYLLayer;
    ArcGISMapImageLayer xingZhengLayer;
    FeatureLayer xzFeatureLayer;
    ArcGISMapImageLayer chongqingLayer;
    List<ArcGISSceneLayer> jinQiaoLayers = new ArrayList<>();
    List<ArcGISSceneLayer> shiLinLayers = new ArrayList<>();
    private ArcGISScene scene;
    private ArcGISTiledElevationSource elevationSource;
    private LayerList layers;
    private Surface.ElevationSourceList elevationSources;
    private List<PersonLocation> qcPersons = new ArrayList<>();
    private GraphicsOverlay graphicsOverlay;//灾害点图标
    private GraphicsOverlay personGraphicsOverlay;
    private GraphicsOverlay localGraphicsOverlay;
    private GraphicsOverlay equipmentGraphicOverlay;
    private GraphicsOverlay mGraphicsOverlay;
    private GraphicsOverlay personLocationGraphicOverlay;
    private GraphicsOverlay searchPlaceGraphicOverlay;
    private GraphicsOverlay searchPersonGraphicOverlay;
    private List<Graphic> allGraphics = new ArrayList<>();//所有的灾害点图标
    private List<Graphic> otherGraphics = new ArrayList<>();//已消耗灾害点图标
    private List<Graphic> allHuaPOGraphics = new ArrayList<>();
    private List<Graphic> allNiSHILiuGraphics = new ArrayList<>();
    private List<Graphic> allWeiYanGraphics = new ArrayList<>();
    private List<Graphic> allXiePoGraphics = new ArrayList<>();
    private List<Graphic> allTanTaGraphics = new ArrayList<>();
    private List<Graphic> allLieFengGraphics = new ArrayList<>();
    private List<Graphic> allTaAnGraphics = new ArrayList<>();

    private List<Graphic> otherHuaPOGraphics = new ArrayList<>();
    private List<Graphic> otherNiSHILiuGraphics = new ArrayList<>();
    private List<Graphic> otherWeiYanGraphics = new ArrayList<>();
    private List<Graphic> otherXiePoGraphics = new ArrayList<>();
    private List<Graphic> otherTanTaGraphics = new ArrayList<>();
    private List<Graphic> otherLieFengGraphics = new ArrayList<>();
    private List<Graphic> otherTaAnGraphics = new ArrayList<>();

    private List<Graphic> qcGraphics = new ArrayList<>();
    private List<Graphic> jianceGraphics = new ArrayList<>();
    private List<Graphic> weatherGraphics = new ArrayList<>();
    private ListenableList<Graphic> disasterGraphics;//所有灾害点图标集合
    private ListenableList<Graphic> personGraphics;
    private ListenableList<Graphic> localGraphics;
    private ListenableList<Graphic> equipmentGraphics;
    private ListenableList<Graphic> weathersGraphics;
    private ListenableList<Graphic> personLocationGraphics;
    private ListenableList<Graphic> searchPlaceGraphics;
    private ListenableList<Graphic> searchPersonGraphics;
    private RadioGroup rg;
    private Dialog waitingDialog;
    private DisasterDetailInfo disasterDetailInfo;
    private ServiceFeatureTable table;

    private TabDisasterInfo mTabDisasterInfo;
    private String areaCode;//万盛id
    private DisasterByStateInfo mDisasterByStateInfo;
    private DisasterByStateInfo mDisasterByStateInfo0;
    private DisasterByStateInfo mDisasterByStateInfo1;
    private DisasterByStateInfo mDisasterByStateInfo2;
    private DisasterByStateInfo mDisasterByStateInfo3;
    private Context context;
    private String mDisasterType = "-1";
    private DetailPersonInfo mDetailPersonInfo;
    private DetailBaseInfo mDetailBaseInfo;
    private DetailPhoto mDetailPhoto;
    private DetailDisCard mDetailDisCard;
    private DetailHeCard mDetailHeCard;
    private DetailPnInfo mDetailPnInfo;
    private String mDisNo;
    private Map<String, String> maps = new HashMap<>();
    private List<PersonType> mPersonTypes = new ArrayList<>();
    private LinearLayout llSwitchInfo;
    private List<Graphic> mGraphics;
    private SimpleMarkerSymbol mPointPlacementSymbol;
    private SimpleMarkerSymbol mPointPlacedSymbol;
    private SimpleMarkerSymbol mPolylineVertexSymbol;
    private SimpleLineSymbol mPolylinePlacementSymbol;
    private SimpleLineSymbol mPolylinePlacedSymbol;
    private SimpleMarkerSymbol mPolylineMidpointSymbol;
    private SimpleFillSymbol mPolygonFillSymbol;
    private SketchGraphicsOverlayEventListener mListener;
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
    private List<String> personList = new ArrayList<>();
    private List<String> personImei = new ArrayList<>();
    private ArrayAdapter personAdapter;
    private String personType = "-1";
    private AlertDialog locationDialog;
    private List<personLocationInfo.DataBean> personLocationData;
    private double detailarea;
    private String detailhttp = "";
    private SearchPlace.DataBean searchPlaceBean;
    private SearchPerson.DataBean searchPersonBean;
    private AlertDialog searchDialog;
    /**
     * 点击的设备的id
     **/
    private int equipmentzIndex;
    /**
     * 所有设备信息
     **/
    private EquipmentLocation equipmentLocation;
    /**
     * ALL_EQUIPMENT:请求所有设备后
     * SINGLE_EQUIPMENT:点击单个设备后
     * SINGLE_EQUIPMENT_DETAIL:点击单个设备的详细数据后
     */
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case EquipmentOkhttp.ALL_EQUIPMENT:
                    equipmentLocation = (EquipmentLocation) msg.obj;
                    Log.d("limeng", equipmentLocation.getData().size() + "");
                    addEquipment();
                    break;
                case EquipmentOkhttp.SINGLE_EQUIPMENT:
                    SingleEquipment singleEquipment = (SingleEquipment) msg.obj;
                    showEquipmentInfo(singleEquipment);
                    break;
                case EquipmentOkhttp.SINGLE_EQUIPMENT_DETAIL:
                    String s = (String) msg.obj;
                    showEquipmentDetail(s, msg.arg1);
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        areaCode = "500110";
        initUtilData();
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionUtils.requestMultiPermissions(MainActivity.this, mPermissionGrant);
        }
        chongqingLayer = new ArcGISMapImageLayer(getResources().getString(R.string.chongqing_url));
        dianziLayer = new ArcGISMapImageLayer(getResources().getString(R.string.dianziditu_url));
        lowImageLayer = new ArcGISMapImageLayer(getResources().getString(R.string.image_layer_13_url));
        highImageLayer = new ArcGISMapImageLayer(getResources().getString(R.string.image_layer_13_19_url));
        vectorLayer = new ArcGISMapImageLayer(getResources().getString(R.string.shiliangtu_url));
        dengZXLayer = new ArcGISMapImageLayer(getResources().getString(R.string.yuliang_url));
        ssYLLayer = new ArcGISMapImageLayer(getResources().getString(R.string.ssyl_url));
        elevationSource = new ArcGISTiledElevationSource(
                getResources().getString(R.string.elevation_image_service));
        table = new ServiceFeatureTable(getResources().getString(R.string.xingzheng_image_url) + "/0");
        xzFeatureLayer = new FeatureLayer(table);
        xingZhengLayer = new ArcGISMapImageLayer(getResources().getString(R.string.xingzheng_image_url));
        for (int i = 1; i <= 8; i++) {
            ArcGISSceneLayer sceneLayer = new ArcGISSceneLayer("http://183.230.182.149:6081/arcgis/rest/services/Hosted/jingqiao_spk" + i + "/SceneServer/layers/0");
            jinQiaoLayers.add(sceneLayer);
        }
        for (int i = 1; i <= 6; i++) {
            ArcGISSceneLayer sceneLayer = new ArcGISSceneLayer("http://183.230.182.149:6081/arcgis/rest/services/Hosted/shilin_spk" + i + "/SceneServer/layers/0/");
            shiLinLayers.add(sceneLayer);
        }
        scene = new ArcGISScene();
        layers = scene.getOperationalLayers();
        graphicsOverlay = new GraphicsOverlay();
        personGraphicsOverlay = new GraphicsOverlay();
        localGraphicsOverlay = new GraphicsOverlay();
        equipmentGraphicOverlay = new GraphicsOverlay();
        GraphicsOverlay weatherGraphicOverlay = new GraphicsOverlay();
        mGraphicsOverlay = new GraphicsOverlay();
        personLocationGraphicOverlay = new GraphicsOverlay();
        searchPlaceGraphicOverlay = new GraphicsOverlay();
        searchPersonGraphicOverlay = new GraphicsOverlay();
        disasterGraphics = graphicsOverlay.getGraphics();
        mGraphics = mGraphicsOverlay.getGraphics();
        personGraphics = personGraphicsOverlay.getGraphics();
        localGraphics = localGraphicsOverlay.getGraphics();
        equipmentGraphics = equipmentGraphicOverlay.getGraphics();
        weathersGraphics = weatherGraphicOverlay.getGraphics();
        personLocationGraphics = personLocationGraphicOverlay.getGraphics();
        searchPlaceGraphics = searchPlaceGraphicOverlay.getGraphics();
        searchPersonGraphics = searchPersonGraphicOverlay.getGraphics();
        ListenableList<GraphicsOverlay> graphicsOverlays = sceneView.getGraphicsOverlays();
        graphicsOverlays.add(mGraphicsOverlay);
        graphicsOverlays.add(graphicsOverlay);
        graphicsOverlays.add(personGraphicsOverlay);
        graphicsOverlays.add(localGraphicsOverlay);
        graphicsOverlays.add(equipmentGraphicOverlay);
        graphicsOverlays.add(weatherGraphicOverlay);
        graphicsOverlays.add(personLocationGraphicOverlay);
        graphicsOverlays.add(searchPlaceGraphicOverlay);
        graphicsOverlays.add(searchPersonGraphicOverlay);
        elevationSources = scene.getBaseSurface().getElevationSources();
        scene.setBasemap(Basemap.createImagery());
        sceneView.setScene(scene);
        mUndoButton.setClickable(false);
        mUndoButton.setEnabled(false);
        mRedoButton.setClickable(false);
        mRedoButton.setEnabled(false);
        mClearButton.setClickable(false);
        mClearButton.setEnabled(false);
        setListeners();
        setlogin("", "");
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_CAMERA:
                    break;
                case PermissionUtils.CODE_RECORD_AUDIO:
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }

    private void setPieChartData(String text, String desText) {
        pieChart.setVisibility(View.VISIBLE);
        pieChart.setText(text);
        pieChart.setDesText(desText);
        pieChart.setSweepAngle(Integer.parseInt(text) * 180 / 100);
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
            clearAllGraphics();
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


    private void initStaData() {
        waitingDialog = WaitingDialog.createLoadingDialog(this, "正在请求中...");
        OkHttpUtils.get().url(getResources().getString(R.string.base_http) + "statistic/totalNum/3/" + areaCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        WaitingDialog.closeDialog(waitingDialog);
                        Toast.makeText(getApplicationContext(), "统计信息获取失败，请检查网路！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        WaitingDialog.closeDialog(waitingDialog);
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONObject oj = object.getJSONObject("data");
                            tvDisasterNumber.setText(oj.getString("disasterNum"));
                            tvEquipmentNumber.setText(oj.getString("deviceNum"));
                            tvZhushouNumber.setText(oj.getString("areaProfessorNum"));
                            tvJianceNumber.setText(oj.getString("projectNum"));
                            tvPianquNumber.setText(oj.getString("areaAdminNum"));
                            tvDhzNumber.setText(oj.getString("dihuanzhanNum"));
                            tvQcqfNumber.setText(oj.getString("humanNum"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void setListeners() {
        ivEnlarge.setOnClickListener(this);
        ivNarrow.setOnClickListener(this);
        btnUtilDetail.setOnClickListener(this);
        ivLocation.setOnClickListener(this);
        ivSearchMain.setOnClickListener(this);
        btnUtil.setOnClickListener(this);
        llXingzheng.setOnClickListener(this);
        ivLuopan.setOnClickListener(this);
        ivAreaBack.setOnClickListener(this);
        ivDataBack.setOnClickListener(this);
        llRainfall.setOnClickListener(this);
        llDangerpoint.setOnClickListener(this);
        llStaff.setOnClickListener(this);
        llEquipment.setOnClickListener(this);
        llQxsy.setOnClickListener(this);
        rbSsyl.setOnCheckedChangeListener(this);
        rbYldzx.setOnCheckedChangeListener(this);
        rbDisasterPoint.setOnCheckedChangeListener(this);
        rbStatePoint0.setOnCheckedChangeListener(this);
        rbStatePoint2.setOnCheckedChangeListener(this);
        rbStatePoint3.setOnCheckedChangeListener(this);
        rbStatePoint1.setOnCheckedChangeListener(this);
        rbQcqfPerson.setOnCheckedChangeListener(this);
        rbDhzPerson.setOnCheckedChangeListener(this);
        rbZsPerson.setOnCheckedChangeListener(this);
        rbPqPerson.setOnCheckedChangeListener(this);
        rbJinqiao.setOnCheckedChangeListener(this);
        rbShilin.setOnCheckedChangeListener(this);
        rbEquipmentFengsu.setOnCheckedChangeListener(this);
        rbEquipmentJiance.setOnCheckedChangeListener(this);
        rbEquipmentLaba.setOnCheckedChangeListener(this);
        rbEquipmentYingji.setOnCheckedChangeListener(this);
        rbQxyj.setOnCheckedChangeListener(this);
        rbQxyb.setOnCheckedChangeListener(this);
        sceneView.setOnTouchListener(new DefaultSceneViewOnTouchListener(sceneView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                final android.graphics.Point screenPoint = new android.graphics.Point((int) e.getX(), (int) e.getY());
                final ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphic = sceneView.identifyGraphicsOverlayAsync(mGraphicsOverlay, screenPoint, 10.0, false);
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
                                    tvMeasureResult.setText("长度为:" + length + "千米");
                                } else if (geometry instanceof Polygon) {
                                    detailarea = Math.abs(GeometryEngine.areaGeodetic(geometry, new AreaUnit(AreaUnitId.SQUARE_KILOMETERS), GeodeticCurveType.GREAT_ELLIPTIC));
                                    //tvMeasureResult.setText("面积为:" + area + "平方千米");
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
                                Point point = sceneView.screenToLocationAsync(screenPoint).get();
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
                if (!mPolylineButton.isSelected() && !mPolygonButton.isSelected() && !mPointButton.isSelected()) {

                    ListenableFuture<Point> pointListenableFuture = sceneView.screenToLocationAsync(screenPoint);
                    if (layers.contains(xingZhengLayer)) {
                        try {
                            Point point = pointListenableFuture.get();
                            QueryParameters query = new QueryParameters();
                            query.setGeometry(point);
                            ListenableFuture<FeatureQueryResult> future = table.queryFeaturesAsync(query);
                            FeatureQueryResult result = future.get();
                            Iterator<Feature> iterator = result.iterator();
                            while (iterator.hasNext()) {
                                Feature feature = iterator.next();
                                Log.e(TAG, "----------" + feature.getAttributes().get("name"));
                                String name = (String) feature.getAttributes().get("name");
                                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_township_statistics, null);
                                TextView tvTownshipName = (TextView) view.findViewById(R.id.tv_township_name);
                                tvTownshipName.setText(name);
                                new AlertDialog.Builder(MainActivity.this)
                                        .setView(view)
                                        .show();
                            }
                        } catch (InterruptedException | ExecutionException e1) {
                            e1.printStackTrace();
                        }
                    }
                    final ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphic1 = sceneView.identifyGraphicsOverlayAsync(graphicsOverlay, screenPoint, 10.0, false, 2);
                    final ListenableFuture<IdentifyGraphicsOverlayResult> personIdentifyGraphic = sceneView.identifyGraphicsOverlayAsync(personGraphicsOverlay, screenPoint, 10.0, false, 2);
                    final ListenableFuture<IdentifyGraphicsOverlayResult> equipmentIdentifyGraphic = sceneView.identifyGraphicsOverlayAsync(equipmentGraphicOverlay, screenPoint, 10.0, false, 2);
                    final ListenableFuture<IdentifyGraphicsOverlayResult> locationIdentifyGraphic = sceneView.identifyGraphicsOverlayAsync(personLocationGraphicOverlay, screenPoint, 10.0, false, 2);
                    final ListenableFuture<IdentifyGraphicsOverlayResult> searchPlaceIdentifyGraphic = sceneView.identifyGraphicsOverlayAsync(searchPlaceGraphicOverlay, screenPoint, 10.0, false, 2);
                    final ListenableFuture<IdentifyGraphicsOverlayResult> searchPersonIdentifyGraphic = sceneView.identifyGraphicsOverlayAsync(searchPersonGraphicOverlay, screenPoint, 10.0, false, 2);
                    identifyGraphic1.addDoneListener(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                IdentifyGraphicsOverlayResult identifyGraphicsOverlayResult = identifyGraphic1.get();
                                if (identifyGraphicsOverlayResult.getGraphics().size() > 0) {
                                    int zIndex = identifyGraphicsOverlayResult.getGraphics().get(0).getZIndex();
                                    showInfo(zIndex);
                                }
                            } catch (InterruptedException | ExecutionException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                    personIdentifyGraphic.addDoneListener(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                IdentifyGraphicsOverlayResult identifyGraphicsOverlayResult = personIdentifyGraphic.get();
                                if (identifyGraphicsOverlayResult.getGraphics().size() > 0) {
                                    int zIndex = identifyGraphicsOverlayResult.getGraphics().get(0).getZIndex();
                                    showPersonInfo(mPersonTypes.get(zIndex).getId(), mPersonTypes.get(zIndex).getType());
                                }
                            } catch (InterruptedException | ExecutionException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                    equipmentIdentifyGraphic.addDoneListener(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                IdentifyGraphicsOverlayResult identifyGraphicsOverlayResult = equipmentIdentifyGraphic.get();
                                if (identifyGraphicsOverlayResult.getGraphics().size() > 0) {
                                    int zIndex = identifyGraphicsOverlayResult.getGraphics().get(0).getZIndex();
                                    equipmentzIndex = zIndex;
                                    //设备点击
                                    EquipmentOkhttp.getSingleEquipment(context, zIndex, handler);
                                }
                            } catch (InterruptedException | ExecutionException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                    locationIdentifyGraphic.addDoneListener(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                IdentifyGraphicsOverlayResult identifyGraphicsOverlayResult = locationIdentifyGraphic.get();
                                if (identifyGraphicsOverlayResult.getGraphics().size() > 0) {
                                    int i = identifyGraphicsOverlayResult.getGraphics().get(0).getZIndex();
                                    String s = String.valueOf(i);
                                    String id = s.substring(0, s.length() - 1);
                                    String type = String.valueOf(s.charAt(s.length() - 1));
                                    Log.d(TAG, "zIndex:" + i);
                                    if (i != 0) {
                                        showPersonLocation(Integer.valueOf(id), type);
                                    }
                                }
                            } catch (InterruptedException | ExecutionException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                    searchPersonIdentifyGraphic.addDoneListener(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                IdentifyGraphicsOverlayResult identifyGraphicsOverlayResult = searchPersonIdentifyGraphic.get();
                                if (identifyGraphicsOverlayResult.getGraphics().size() > 0 && searchPersonBean != null) {
                                    showSearchPersonInfo();
                                }
                            } catch (InterruptedException | ExecutionException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                    searchPlaceIdentifyGraphic.addDoneListener(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                IdentifyGraphicsOverlayResult identifyGraphicsOverlayResult = searchPlaceIdentifyGraphic.get();
                                if (identifyGraphicsOverlayResult.getGraphics().size() > 0 && searchPlaceBean != null) {
                                    showSearchPlaceInfo();
                                }
                            } catch (InterruptedException | ExecutionException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                }


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
                        LocationToScreenResult locationToScreenResult = sceneView.locationToScreen((Point) mCurrentPoint.getGeometry());
                        android.graphics.Point currentPoint = locationToScreenResult.getScreenPoint();
                        android.graphics.Point fromPoint = new android.graphics.Point((int) from.getX(), (int) from.getY());
                        int dx = currentPoint.x - fromPoint.x;
                        int dy = currentPoint.y - fromPoint.y;
                        int distance = (int) Math.sqrt((dx * dx) + (dy * dy));
                        if (distance < 20) {
                            callSuper = false;
                            android.graphics.Point toPoint = new android.graphics.Point((int) to.getX(), (int) to.getY());
                            Point oldGeometry = (Point) mCurrentPoint.getGeometry();
                            Point oldPointCopy = new Point(oldGeometry.getX(), oldGeometry.getY(), sceneView.getSpatialReference());
                            Point newGeometry = null;
                            try {
                                newGeometry = sceneView.screenToLocationAsync(toPoint).get();
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
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
        scene.addLoadStatusChangedListener(new LoadStatusChangedListener() {
            @Override
            public void loadStatusChanged(LoadStatusChangedEvent loadStatusChangedEvent) {
                String name = loadStatusChangedEvent.getNewLoadStatus().name();
                if ("LOADED".equals(name)) {
                    layers.add(xingZhengLayer);
                    Camera camera = new Camera(28.769167, 106.910399, 50000.0, 0, 20, 0.0);
                    sceneView.setViewpointCamera(camera);
                }
            }
        });
        sceneView.addViewpointChangedListener(new ViewpointChangedListener() {
            @Override
            public void viewpointChanged(ViewpointChangedEvent viewpointChangedEvent) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
                            int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
                            android.graphics.Point startPoint = new android.graphics.Point(0, 0);
                            android.graphics.Point endPoint = new android.graphics.Point(widthPixels, heightPixels);
                            Point start = sceneView.screenToLocationAsync(startPoint).get();
                            Point end = sceneView.screenToLocationAsync(endPoint).get();
                            PointCollection collection = new PointCollection(SpatialReferences.getWgs84());
                            collection.add(start);
                            collection.add(end);
                            Polyline polyline = new Polyline(collection);
                            double realLength = GeometryEngine.lengthGeodetic(polyline, new LinearUnit(LinearUnitId.METERS), GeodeticCurveType.GREAT_ELLIPTIC) * 100;
                            double screenLength = getScreenSizeOfDevice() * 2.54;
                            Log.d(TAG, "实际长度：" + realLength + "\n" + "屏幕尺寸：" + screenLength);
                            final int scale = (int) (Math.ceil((realLength / screenLength) / 1000) * 1000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvScale.setText("(1  :  " + scale + ")");
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });
    }

    private double getScreenSizeOfDevice() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        double x = Math.pow(width, 2);
        double y = Math.pow(height, 2);
        double diagonal = Math.sqrt(x + y);

        int dens = dm.densityDpi;
        double screenInches = diagonal / (double) dens;
        Log.d(TAG, "The screenInches " + screenInches);
        return screenInches;
    }

    private void showSearchPersonInfo() {
        new AlertDialog.Builder(context)
                .setMessage("姓名：" + searchPersonBean.getName() + "\n" +
                        "电话：" + searchPersonBean.getPhone() + "\n" +
                        "东经：" + searchPersonBean.getLon() + "\n" +
                        "北纬：" + searchPersonBean.getLat()
                )
                .show();
    }

    private void showSearchPlaceInfo() {
        new AlertDialog.Builder(context)
                .setMessage("名称：" + searchPlaceBean.getName() + "\n" +
                        "东经：" + searchPlaceBean.getLon() + "\n" +
                        "北纬：" + searchPlaceBean.getLat()
                )
                .show();
    }

    private void showPersonLocation(int zIndex, String type) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_location_person_info, null);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvMobile = (TextView) view.findViewById(R.id.tv_mobile);
        TextView tvLon = (TextView) view.findViewById(R.id.tv_Longitude);
        TextView tvLat = (TextView) view.findViewById(R.id.tv_latitude);
        TextView tvTime = (TextView) view.findViewById(R.id.tv_time);
        Button btnCall = (Button) view.findViewById(R.id.btn_call);
        LinearLayout llCall = (LinearLayout) view.findViewById(R.id.ll_shipin);
        if ("0".equals(type)) {
            llCall.setVisibility(View.VISIBLE);
        } else {
            llCall.setVisibility(View.GONE);
        }
        for (final personLocationInfo.DataBean dataBean : personLocationData) {
            if (zIndex == dataBean.getId()) {
                tvName.setText(dataBean.getName());
                tvMobile.setText(dataBean.getTel());
                tvLon.setText(dataBean.getLon() + "");
                tvLat.setText(dataBean.getLat() + "");
                tvTime.setText(dataBean.getTime());
                btnCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO 视频通话
                        Intent intent = new Intent(MainActivity.this, VideoCallActivity.class);
                        intent.putExtra("PHONE_NUMBER", dataBean.getTel());
                        startActivity(intent);
                    }
                });
                new AlertDialog.Builder(context)
                        .setView(view)
                        .show();
            }

        }
    }

    private void setOkhttpKuangxuan(String http, final double area) {
        if (TextUtils.isEmpty(http)) {
            Toast.makeText(context, "请先点击要显示的区域！", Toast.LENGTH_SHORT).show();
            return;
        }
        waitingDialog = WaitingDialog.createLoadingDialog(context, "正在请求中...");
        OkHttpUtils.get().url(getResources().getString(R.string.base_http) + getResources().getString(R.string.kuangxuan) + http + "/" + areaCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                        WaitingDialog.closeDialog(waitingDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        WaitingDialog.closeDialog(waitingDialog);
                        Gson gson = new Gson();
                        KuangxuanInfo mKuangxuanInfo = null;
                        try {
                            mKuangxuanInfo = gson.fromJson(response, KuangxuanInfo.class);
                        } catch (Exception e) {

                        }
                        if (null == mKuangxuanInfo) {
                            Toast.makeText(context, "框选框内无数据点", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        KuangxuanInfo.DataBean data = mKuangxuanInfo.getData();
                        if (data == null) {
                            return;
                        }
                        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_kuangxuan_statistics, null);
                        ((TextView) view.findViewById(R.id.tv_kuangxuan_1)).setText(data.getDisaster().size() + "");
                        ((TextView) view.findViewById(R.id.tv_kuangxuan_2)).setText(data.getHavaOver().size() + "");
                        ((TextView) view.findViewById(R.id.tv_kuangxuan_3)).setText(data.getHavaMoved().size() + "");
                        ((TextView) view.findViewById(R.id.tv_kuangxuan_4)).setText(data.getHavaKilled().size() + "");
                        ((TextView) view.findViewById(R.id.tv_kuangxuan_5)).setText(data.getHuman().size() + "");
                        ((TextView) view.findViewById(R.id.tv_kuangxuan_6)).setText(data.getAreaAdmin().size() + "");
                        ((TextView) view.findViewById(R.id.tv_kuangxuan_7)).setText(data.getAreaProfessor().size() + "");
                        ((TextView) view.findViewById(R.id.tv_kuangxuan_8)).setText(data.getDihuanzhan().size() + "");
                        ((TextView) view.findViewById(R.id.tv_kuangxuan_9)).setText(String.format("%.4f", area) + "");

                        List<TabDisasterInfo.DataBean> datas = new ArrayList<TabDisasterInfo.DataBean>();
                        TabDisasterInfo.DataBean dataBean;
                        for (int i = 0; i < data.getDisaster().size(); i++) {
                            dataBean = new TabDisasterInfo.DataBean();
                            dataBean.setId(data.getDisaster().get(i).getId());
                            dataBean.setDisLat(data.getDisaster().get(i).getDisLat());
                            dataBean.setDisLon(data.getDisaster().get(i).getDisLon());
                            dataBean.setDisNo(data.getDisaster().get(i).getDisNo());
                            dataBean.setDisType(data.getDisaster().get(i).getDisType());
                            datas.add(dataBean);
                        }
                        mTabDisasterInfo = new TabDisasterInfo();
                        mTabDisasterInfo.setData(datas);
                        Log.d("limeng", "mTabDisasterInfo.size" + mTabDisasterInfo.getData().size());
                        setOverlay();
                        PersonLocation personLocation;
                        mPersonTypes.clear();
                        qcPersons.clear();
                        for (int i = 0; i < data.getHuman().size(); i++) {
                            personLocation = new PersonLocation();
                            personLocation.setId(data.getHuman().get(i).getDis_id());
                            personLocation.setLat(data.getHuman().get(i).getDis_lat() + "");
                            personLocation.setLon(data.getHuman().get(i).getDis_lon() + "");
                            personLocation.setType("1");
                            qcPersons.add(personLocation);
                        }
                        for (int i = 0; i < data.getAreaProfessor().size(); i++) {
                            personLocation = new PersonLocation();
                            personLocation.setId(data.getAreaProfessor().get(i).getId());
                            personLocation.setLat(data.getAreaProfessor().get(i).getLat() + "");
                            personLocation.setLon(data.getAreaProfessor().get(i).getLng() + "");
                            personLocation.setType("2");
                            qcPersons.add(personLocation);
                        }
                        for (int i = 0; i < data.getAreaAdmin().size(); i++) {
                            personLocation = new PersonLocation();
                            personLocation.setId(data.getAreaAdmin().get(i).getId());
                            personLocation.setLat(data.getAreaAdmin().get(i).getLat() + "");
                            personLocation.setLon(data.getAreaAdmin().get(i).getLon() + "");
                            personLocation.setType("3");
                            qcPersons.add(personLocation);
                        }
                        for (int i = 0; i < data.getDihuanzhan().size(); i++) {
                            personLocation = new PersonLocation();
                            personLocation.setId(data.getDihuanzhan().get(i).getId());
                            personLocation.setLat(data.getDihuanzhan().get(i).getLat() + "");
                            personLocation.setLon(data.getDihuanzhan().get(i).getLng() + "");
                            personLocation.setType("4");
                            qcPersons.add(personLocation);
                        }
                        setPersonGraphic();
                        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                                .setView(view)
                                .show();
                        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();//获取dialog信息
                        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        dialog.getWindow().setAttributes(params);//设置大小
                    }
                });
    }


    private void showDialogPic() {
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_pic, null);
        final AlertDialog ss = new AlertDialog.Builder(MainActivity.this).setView(view).create();
        ss.show();
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = ss.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        lp.height = (int) (display.getHeight()); //设置宽度
        ss.getWindow().setAttributes(lp);
    }

    /**
     * 测试设备信息
     */
    private void showEquipmentInfo(final SingleEquipment singleEquipment) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_shebi, null);
        final LinearLayout llSheBeiInfo1 = (LinearLayout) view.findViewById(R.id.ll_shebei_info_1);
        final LinearLayout llSheBeiInfo2 = (LinearLayout) view.findViewById(R.id.ll_shebei_info_2);
        final LinearLayout llSheBeiInfo3 = (LinearLayout) view.findViewById(R.id.ll_shebei_info_3);
        ((TextView) view.findViewById(R.id.tv_monitor_name)).setText(singleEquipment.getData().getMonitor_name());
        ((TextView) view.findViewById(R.id.tv_static_name)).setText(singleEquipment.getData().getStatic_name());
        ((TextView) view.findViewById(R.id.tv_device_name)).setText(singleEquipment.getData().getDevice_name());
        ((TextView) view.findViewById(R.id.tv_hardware_version)).setText(singleEquipment.getData().getHardware_version());
        ((TextView) view.findViewById(R.id.tv_soft_version)).setText(singleEquipment.getData().getSoft_version());
        ((TextView) view.findViewById(R.id.tv_register_date)).setText(singleEquipment.getData().getRegister_date().replace("T", "\t"));
        ((TextView) view.findViewById(R.id.tv_state)).setText("1".equals(singleEquipment.getData().getState()) ? "正常工作" : "离线");

        ((TextView) view.findViewById(R.id.tv_project_name)).setText(singleEquipment.getData().getProject_name());
        ((TextView) view.findViewById(R.id.tv_start_date)).setText(singleEquipment.getData().getStart_date().replace("T", "\t"));
        ((TextView) view.findViewById(R.id.tv_project_add)).setText(singleEquipment.getData().getProject_add());
        ((TextView) view.findViewById(R.id.tv_longitude)).setText(singleEquipment.getData().getLongitude() + "");
        ((TextView) view.findViewById(R.id.tv_latitude)).setText(singleEquipment.getData().getLatitude() + "");
        ((TextView) view.findViewById(R.id.tv_project_man)).setText(singleEquipment.getData().getProject_man());
        ImageView ivPic = (ImageView) view.findViewById(R.id.iv_pic);
        ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPic();
            }
        });


        TextView tvCheckToDetail = (TextView) view.findViewById(R.id.tv_check_to_detail);
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.rg_disaster_info);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_check_1:
                        llSheBeiInfo1.setVisibility(View.VISIBLE);
                        llSheBeiInfo2.setVisibility(View.GONE);
                        llSheBeiInfo3.setVisibility(View.GONE);
                        break;
                    case R.id.rbtn_check_2:
                        llSheBeiInfo1.setVisibility(View.GONE);
                        llSheBeiInfo2.setVisibility(View.VISIBLE);
                        llSheBeiInfo3.setVisibility(View.GONE);
                        break;
                    case R.id.rbtn_check_3:
                        llSheBeiInfo1.setVisibility(View.GONE);
                        llSheBeiInfo2.setVisibility(View.GONE);
                        llSheBeiInfo3.setVisibility(View.VISIBLE);
                        break;
                }
            }

        });


        tvCheckToDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deviceNo;
                switch (singleEquipment.getData().getMonitor_type()) {
                    case 1:
                        deviceNo = singleEquipment.getData().getTable_name();
                        break;
                    case 3:
                        deviceNo = equipmentzIndex + "";
                        break;
                    case 22:
                        deviceNo = equipmentzIndex + "";
                        break;
                    default:
                        deviceNo = singleEquipment.getData().getDevice_no();
                        break;
                }
                EquipmentOkhttp.getSingleEquipmentDetail(context, deviceNo, singleEquipment.getData().getMonitor_type() + "", handler);
            }
        });
        AlertDialog dialog1 = new AlertDialog.Builder(MainActivity.this)
                .setView(view)
                .show();
    }

    /**
     * 显示设备曲线信息
     *
     * @param response 信息实体
     * @param type     设备type
     */
    private void showEquipmentDetail(String response, int type) {
        Gson gson = new Gson();
        switch (type) {
            case 1:
                EquipmentType1 equipmentType1 = gson.fromJson(response, EquipmentType1.class);
                EquipmentOkhttp.getType1lines(context, equipmentType1);
                break;
            case 3:
                EquipmentType3 equipmentType3 = gson.fromJson(response, EquipmentType3.class);
                EquipmentOkhttp.getType3lines(context, equipmentType3);
                break;
            case 11:
                EquipmentType11 equipmentType11 = gson.fromJson(response, EquipmentType11.class);
                EquipmentOkhttp.getType11lines(context, equipmentType11);
                break;
            case 14:
                EquipmentType14 equipmentType14 = gson.fromJson(response, EquipmentType14.class);
                EquipmentOkhttp.getType14lines(context, equipmentType14);
                break;
            case 19:
                EquipmentType19 equipmentType19 = gson.fromJson(response, EquipmentType19.class);
                EquipmentOkhttp.getType19lines(context, equipmentType19);
                break;
            case 20:
                EquipmentType20 equipmentType20 = gson.fromJson(response, EquipmentType20.class);
                EquipmentOkhttp.getType20lines(context, equipmentType20);
                break;
            case 22:
                EquipmentType22 equipmentType22 = gson.fromJson(response, EquipmentType22.class);
                EquipmentOkhttp.getType22lines(context, equipmentType22);
                break;
            case 23:
                EquipmentType23 equipmentType23 = gson.fromJson(response, EquipmentType23.class);
                EquipmentOkhttp.getType23lines(context, equipmentType23);
                break;
            case 25:
                EquipmentType25 equipmentType25 = gson.fromJson(response, EquipmentType25.class);
                EquipmentOkhttp.getType25lines(context, equipmentType25);
                break;
            case 26:
                EquipmentType26 equipmentType26 = gson.fromJson(response, EquipmentType26.class);
                EquipmentOkhttp.getType26lines(context, equipmentType26);
                break;
        }


    }


    private void showPersonInfo(int zIndex, final String type) {
        String http = "";
        switch (type) {
            case "1":
                http = getResources().getString(R.string.qcqf_man_id) + zIndex;
                break;
            case "2":
                http = getResources().getString(R.string.zs_man_id) + zIndex;
                break;
            case "3":
                http = getResources().getString(R.string.fzr_man_id) + zIndex;
                break;
            case "4":
                http = getResources().getString(R.string.dh_man_id) + areaCode;
                break;
        }
        waitingDialog = WaitingDialog.createLoadingDialog(this, "正在请求中...");
        OkHttpUtils.get().url(getResources().getString(R.string.base_http) + http)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        WaitingDialog.closeDialog(waitingDialog);
                        Toast.makeText(getApplicationContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        WaitingDialog.closeDialog(waitingDialog);
                        switch (type) {
                            case "1":
                                Gson gson = new Gson();
                                PersonInfo personInfos = gson.fromJson(response, PersonInfo.class);
                                if (personInfos.getData() == null) {
                                    return;
                                }
                                PersonInfo.DataBean personInfo = personInfos.getData();
                                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_person_info, null);
                                ((TextView) view.findViewById(R.id.tv_0_person_name)).append(personInfo.getName() == null ? "" : personInfo.getName());
                                ((TextView) view.findViewById(R.id.tv_0_person_work)).append(personInfo.getWork() == null ? "" : personInfo.getWork());
                                String polics = "";
                                switch (personInfo.getPolics() == null ? -1 : Integer.parseInt(personInfo.getPolics())) {
                                    case 1:
                                        polics = "中共党员";
                                        break;
                                    case 2:
                                        polics = "中共预备党员";
                                        break;
                                    case 3:
                                        polics = "共青团员";
                                        break;
                                    case 4:
                                        polics = "群众";
                                        break;
                                    case 5:
                                        polics = "民革党员";
                                        break;
                                    case 6:
                                        polics = "民盟盟员";
                                        break;
                                    case 7:
                                        polics = "民建会员";
                                        break;
                                }
                                ((TextView) view.findViewById(R.id.tv_0_person_polics)).append(polics);
                                ((TextView) view.findViewById(R.id.tv_0_person_nation)).append(personInfo.getNation() == null ? "" : personInfo.getNation());
                                ((TextView) view.findViewById(R.id.tv_0_person_address)).append(personInfo.getAddress() == null ? "" : personInfo.getAddress());
                                TextView tvOnline = ((TextView) view.findViewById(R.id.tv_0_person_is));
                                if (personInfo.getOnlineStatus() == null | personInfo.getOnlineStatus() == "0") {
                                    tvOnline.append("不在线");
                                    tvOnline.setTextColor(Color.RED);
                                } else {
                                    tvOnline.append("在线");
                                    tvOnline.setTextColor(Color.GREEN);
                                }
                                ((TextView) view.findViewById(R.id.tv_0_person_ismonitor)).append(personInfo.getIsMonitor() == 1 ? "监测负责人" : "监测人");
                                ((TextView) view.findViewById(R.id.tv_0_person_brithday)).append(personInfo.getBirthday() == null ? "" : personInfo.getBirthday());
                                ((TextView) view.findViewById(R.id.tv_0_person_realmobile)).append(personInfo.getRealMobile() == null ? "" : personInfo.getRealMobile());
                                ((TextView) view.findViewById(R.id.tv_0_person_mobile)).append(personInfo.getMobile() == null ? "" : personInfo.getMobile());
                                new AlertDialog.Builder(MainActivity.this)
                                        .setView(view)
                                        .show();
                                //http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=7&path=wansheng/jinqiaozhen/zhuyunhua.jpg
                                Glide.with(MainActivity.this)
                                        .load("http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=7&path=" + personInfo.getHeadUrl())
                                        .placeholder(R.mipmap.downloading)
                                        .thumbnail(0.1f)
                                        .error(R.mipmap.download_pass)
                                        .into((ImageView) view.findViewById(R.id.dialog_image));
                                break;
                            case "2":
                                Gson gson2 = new Gson();
                                PersonZSInfo mPersonZSInfo = gson2.fromJson(response, PersonZSInfo.class);
                                if (mPersonZSInfo.getData() == null) {
                                    return;
                                }
                                PersonZSInfo.DataBean mPersonZSInfoData = mPersonZSInfo.getData();
                                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_person_info1, null);
                                ((TextView) view.findViewById(R.id.tv_1_person_name)).append(mPersonZSInfoData.getDisname());
                                ((TextView) view.findViewById(R.id.tv_1_person_address)).append(mPersonZSInfoData.getDisarea());
                                ((TextView) view.findViewById(R.id.tv_1_person_mobile)).append(mPersonZSInfoData.getPhone());
                                TextView tvOnline1 = (TextView) view.findViewById(R.id.tv_1_person_is);
                                if (mPersonZSInfoData.getOnlineStatus() == null | mPersonZSInfoData.getOnlineStatus() == "0") {
                                    tvOnline1.append("不在线");
                                    tvOnline1.setTextColor(Color.RED);
                                } else {
                                    tvOnline1.append("在线");
                                    tvOnline1.setTextColor(Color.GREEN);
                                }
                                //http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=2&path=wansheng/baowei.jpg
                                Glide.with(MainActivity.this)
                                        .load("http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=2&path=" + mPersonZSInfoData.getDispicture())
                                        .placeholder(R.mipmap.downloading)
                                        .thumbnail(0.1f)
                                        .error(R.mipmap.download_pass)
                                        .into((ImageView) view.findViewById(R.id.dialog_image));
                                new AlertDialog.Builder(MainActivity.this)
                                        .setView(view)
                                        .show();
                                break;
                            case "3":
                                Gson gson3 = new Gson();
                                PersonFZInfo mPersonFZInfo = gson3.fromJson(response, PersonFZInfo.class);
                                if (mPersonFZInfo.getData() == null) {
                                    return;
                                }
                                PersonFZInfo.DataBean mPersonFZInfoData = mPersonFZInfo.getData().get(0);
                                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_person_info2, null);
                                ((TextView) view.findViewById(R.id.tv_2_person_name)).append(mPersonFZInfoData.getAdmin_name());
                                ((TextView) view.findViewById(R.id.tv_2_person_address)).append(mPersonFZInfoData.getArea_location());
                                ((TextView) view.findViewById(R.id.tv_2_person_mobile)).append(mPersonFZInfoData.getReal_mobile());
                                ((TextView) view.findViewById(R.id.tv_2_person_tel)).append(mPersonFZInfoData.getTelephone());
                                ((TextView) view.findViewById(R.id.tv_2_person_job)).append("片区负责人");
                                TextView tvOnline2 = ((TextView) view.findViewById(R.id.tv_2_person_is));
                                if (mPersonFZInfoData.getOnlineStatus() == null | mPersonFZInfoData.getOnlineStatus() == "0") {
                                    tvOnline2.append("不在线");
                                    tvOnline2.setTextColor(Color.RED);
                                } else {
                                    tvOnline2.append("在线");
                                    tvOnline2.setTextColor(Color.GREEN);
                                }
                                //http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=1&path=wansheng/huoyuenan.jpg
                                Glide.with(MainActivity.this)
                                        .load("http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=1&path=" + mPersonFZInfoData.getAdmin_pic())
                                        .placeholder(R.mipmap.downloading)
                                        .thumbnail(0.1f)
                                        .error(R.mipmap.download_pass)
                                        .into((ImageView) view.findViewById(R.id.dialog_image));
                                new AlertDialog.Builder(MainActivity.this)
                                        .setView(view)
                                        .show();
                                break;
                            case "4":
                                Gson gson4 = new Gson();
                                PersonDHInfo mPersonDHInfo = gson4.fromJson(response, PersonDHInfo.class);
                                if (mPersonDHInfo.getData() == null) {
                                    return;
                                }
                                PersonDHInfo.DataBean mPersonDHInfoData = mPersonDHInfo.getData().get(0);
                                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_person_info3, null);
                                ((TextView) view.findViewById(R.id.tv_3_person_name)).append(mPersonDHInfoData.getName());
                                ((TextView) view.findViewById(R.id.tv_3_person_mobile)).append(mPersonDHInfoData.getIphone());
                                ((TextView) view.findViewById(R.id.tv_3_person_job)).append(mPersonDHInfoData.getJob());
                                ((TextView) view.findViewById(R.id.tv_3_person_danwei)).append(mPersonDHInfoData.getLocation());
                                ((TextView) view.findViewById(R.id.tv_3_person_manage_area)).append(mPersonDHInfoData.getArea_name());
                                TextView tvOnline3 = ((TextView) view.findViewById(R.id.tv_3_person_is));
                                if (mPersonDHInfoData.getOnlineStatus() == null | mPersonDHInfoData.getOnlineStatus() == "0") {
                                    tvOnline3.append("不在线");
                                    tvOnline3.setTextColor(Color.RED);
                                } else {
                                    tvOnline3.append("在线");
                                    tvOnline3.setTextColor(Color.GREEN);
                                }
                                //http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=4&path=wansheng/mulianqing.jpg
                                Glide.with(MainActivity.this)
                                        .load("http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=4&path=" + mPersonDHInfoData.getUrl())
                                        .placeholder(R.mipmap.downloading)
                                        .thumbnail(0.1f)
                                        .error(R.mipmap.download_pass)
                                        .into((ImageView) view.findViewById(R.id.dialog_image));
                                new AlertDialog.Builder(MainActivity.this)
                                        .setView(view)
                                        .show();
                                break;
                        }

                    }
                });
    }

    private void showInfo(int zIndex) {
        setDialogViewDatas(mDisasterType, zIndex);

    }


    /**
     * type:-1灾害点
     * 0已销号
     * 2已治理灾害点
     * 3已搬迁灾害点
     * 1库岸调查
     *
     * @param type
     */
    private void setDialogViewDatas(String type, final int id) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_disasterinfo, null);
        final LinearLayout llBaseInfo = (LinearLayout) view.findViewById(R.id.ll_base_info);
        rg = (RadioGroup) view.findViewById(R.id.rg_disaster_info);
        final List<String> mList = new ArrayList<>();
        mList.add(R.mipmap.t5001101000840101_1 + "");
        mList.add(R.mipmap.t5001101000840101_2 + "");
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.rg_disaster_info);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_base_info:
                        llBaseInfo.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rbtn_check_detail:
                        llBaseInfo.setVisibility(View.VISIBLE);
                        showDialogDetails(id + "");
                        //setOkhttpDetails(id+"",getResources().getString(R.string.four_person_info),1);
                        break;
                }
            }

        });

        String mDisName = "";
        String mDisType = "";
        String mDisNo = "";
        String mAreaId = "";
        String mDisLocation = "";
        String mDisCause = "";
        String mImperilMan = "";
        String mImperilFamilies = "";
        String mMainObject = "";
        if ("-1".equals(type)) {
            for (int i = 0; i < mTabDisasterInfo.getData().size(); i++) {
                if (mTabDisasterInfo.getData().get(i).getId() == id) {
                    mDisName = mTabDisasterInfo.getData().get(i).getDisName();
                    mDisType = mTabDisasterInfo.getData().get(i).getDisType() + "";
                    mDisNo = mTabDisasterInfo.getData().get(i).getDisNo();
                    mAreaId = mTabDisasterInfo.getData().get(i).getAreaId() + "";
                    mDisLocation = mTabDisasterInfo.getData().get(i).getDisLocation();
                    mDisCause = mTabDisasterInfo.getData().get(i).getDisCause();
                    mImperilMan = mTabDisasterInfo.getData().get(i).getImperilMan() + "";
                    mImperilFamilies = mTabDisasterInfo.getData().get(i).getImperilFamilies() + "";
                    mMainObject = mTabDisasterInfo.getData().get(i).getMainObject() + "";
                }
            }
        } else {

            for (int i = 0; i < mDisasterByStateInfo.getData().size(); i++) {
                if (mDisasterByStateInfo.getData().get(i).getId() == id) {
                    mDisName = mDisasterByStateInfo.getData().get(i).getDis_name();
                    mDisType = mDisasterByStateInfo.getData().get(i).getDis_type() + "";
                    mDisNo = mDisasterByStateInfo.getData().get(i).getDis_no();
                    mAreaId = mDisasterByStateInfo.getData().get(i).getArea_id() + "";
                    mDisLocation = mDisasterByStateInfo.getData().get(i).getDis_location();
                    mDisCause = mDisasterByStateInfo.getData().get(i).getDis_cause();
                    mImperilMan = mDisasterByStateInfo.getData().get(i).getImperil_man() + "";
                    mImperilFamilies = mDisasterByStateInfo.getData().get(i).getImperil_families() + "";
                    mMainObject = mDisasterByStateInfo.getData().get(i).getMain_object() + "";
                }
            }
        }
        switch (mDisType) {
            case "1":
                mDisType = "滑坡";
                break;
            case "2":
                mDisType = "泥石流";
                break;
            case "3":
                mDisType = "危岩";
                break;
            case "4":
                mDisType = "不稳定斜坡";
                break;
            case "5":
                mDisType = "地面坍塌";
                break;
            case "6":
                mDisType = "地裂缝";
                break;
            case "7":
                mDisType = "库岸";
                break;
        }
        TextView tvDisName = (TextView) view.findViewById(R.id.tv_dis_name);
        TextView tvDisType = (TextView) view.findViewById(R.id.tv_dis_type);
        TextView tvDisNo = (TextView) view.findViewById(R.id.tv_dis_no);
        TextView tvAreaId = (TextView) view.findViewById(R.id.tv_area_id);
        TextView tvDisLocation = (TextView) view.findViewById(R.id.tv_dis_location);
        TextView tvDisCause = (TextView) view.findViewById(R.id.tv_dis_cause);
        TextView tvImperilMan = (TextView) view.findViewById(R.id.tv_imperil_man);
        TextView tvImperilFamilies = (TextView) view.findViewById(R.id.tv_imperil_families);
        TextView tvMainObject = (TextView) view.findViewById(R.id.tv_main_object);

        tvDisName.setText(mDisName == null ? "" : mDisName);
        tvDisType.setText(mDisType + "");
        tvDisNo.setText(mDisNo == null ? "" : mDisNo);
        tvAreaId.setText(mDisLocation == null ? "" : mDisLocation);
        tvDisLocation.setText(mDisLocation == null ? "" : mDisLocation);
        tvDisCause.setText(mDisCause == null ? "" : mDisCause);
        tvImperilMan.setText(mImperilMan + "");
        tvImperilFamilies.setText(mImperilFamilies + "");
        tvMainObject.setText(mMainObject == null ? "" : mMainObject);

        AlertDialog dialog1 = new AlertDialog.Builder(MainActivity.this)
                .setView(view)
                .show();
    }

    /**
     * 伪登录
     *
     * @param str1
     * @param str2
     */
    private void setlogin(String str1, String str2) {
        Log.d("limeng", "请求中...");
        OkHttpUtils.get().url(getResources().getString(R.string.login))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        Log.d("limeng", "response.headers()_" + response.headers());
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        initStaData();
                    }
                });
    }

    /**
     * type不同的详细信息
     *
     * @param id
     * @param http
     * @param type
     */
    private void setOkhttpDetails(String id, String http, final int type) {
        waitingDialog = WaitingDialog.createLoadingDialog(this, "正在请求中...");
        OkHttpUtils.get().url(getResources().getString(R.string.base_http) + http + "/" + id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        new Handler().postDelayed(new Runnable() {

                            public void run() {
                                WaitingDialog.closeDialog(waitingDialog);
                            }

                        }, 300);
                        Log.d("limeng", "response=" + response);
                        Gson gson = new Gson();
                        switch (type) {
                            case 1:
                                mDetailPersonInfo = gson.fromJson(response, DetailPersonInfo.class);
                                llSwitchInfo.addView(addPersonView());
                                break;
                            case 2:
                                mDetailBaseInfo = gson.fromJson(response, DetailBaseInfo.class);
                                String info2 = "暂无数据可显示";
                                if (mDetailBaseInfo.getData() != null) {
                                    info2 = "隐患点名称：" + (mDetailBaseInfo.getData().getDisName() == null ? "" : mDetailBaseInfo.getData().getDisName()) + "\n"
                                            //+ "灾害点类型：" + (mDetailBaseInfo.getData().getDisType() + "") + "\n"
                                            + "灾害点编号：" + (mDetailBaseInfo.getData().getDisNo() == null ? "" : mDetailBaseInfo.getData().getDisNo()) + "\n"
                                            + "乡镇：" + (mDetailBaseInfo.getData().getDisLocation() == null ? "" : mDetailBaseInfo.getData().getDisLocation()) + "\n"
                                            + "详细地址：" + (mDetailBaseInfo.getData().getDisLocation() == null ? "" : mDetailBaseInfo.getData().getDisLocation()) + "\n"
                                            + "主要诱因：" + (mDetailBaseInfo.getData().getDisCause() == null ? "" : mDetailBaseInfo.getData().getDisCause()) + "\n"
                                            + "受威胁人数：" + (mDetailBaseInfo.getData().getImperilMan()) + "\n"
                                            + "受威胁户数：" + (mDetailBaseInfo.getData().getImperilFamilies()) + "\n"
                                            + "影响对象：" + (mDetailBaseInfo.getData().getMainObject() == null ? "" : mDetailBaseInfo.getData().getMainObject()) + "\n"
                                            + "威胁财产(万元)：" + (mDetailBaseInfo.getData().getImperilMoney() == null ? "" : mDetailBaseInfo.getData().getImperilMoney()) + "\n"
                                            + "危害等级：" + (mDetailBaseInfo.getData().getImperilLevel()) + "\n"
                                            + "处置意见：" + (mDetailBaseInfo.getData().getDealIdea() == null ? "" : mDetailBaseInfo.getData().getDealIdea()) + "\n"
                                            + "防治级别：" + (mDetailBaseInfo.getData().getDefenseLevel()) + "\n"
                                            + "稳定性：" + (mDetailBaseInfo.getData().getStableLevel()) + "\n"
                                            + "坡度：" + (mDetailBaseInfo.getData().getDisSlope() == null ? "" : mDetailBaseInfo.getData().getDisSlope()) + "\n"
                                            + "面积(km2)：" + (mDetailBaseInfo.getData().getDisArea() == null ? "" : mDetailBaseInfo.getData().getDisArea()) + "\n"
                                            + "体积(m3)：" + (mDetailBaseInfo.getData().getDisVolume() == null ? "" : mDetailBaseInfo.getData().getDisVolume()) + "\n"
                                            + "前缘高程(m)：" + (mDetailBaseInfo.getData().getDisBefore() == null ? "" : mDetailBaseInfo.getData().getDisBefore()) + "\n"
                                            + "后缘高程(m)：" + (mDetailBaseInfo.getData().getDisAfter() == null ? "" : mDetailBaseInfo.getData().getDisAfter()) + "\n"
                                            + "经纬度：" + mDetailBaseInfo.getData().getDisLon() + "," + mDetailBaseInfo.getData().getDisLat() + "\n"
                                            + "入库时间：" + (mDetailBaseInfo.getData().getComeTime() == null ? "" : mDetailBaseInfo.getData().getComeTime());
                                    Log.d("limeng", "info2：" + info2);
                                }
                                llSwitchInfo.addView(addTextView(info2));
                                break;
                            case 3:
                                mDetailPhoto = gson.fromJson(response, DetailPhoto.class);
                                llSwitchInfo.addView(addPhotoView());
                                break;
                            case 5:
                                mDetailDisCard = gson.fromJson(response, DetailDisCard.class);
                                llSwitchInfo.addView(addTableView1());
                                break;
                            case 6:
                                mDetailHeCard = gson.fromJson(response, DetailHeCard.class);
                                llSwitchInfo.addView(addTableView2());
                                break;
                            case 7:
                                mDetailPnInfo = gson.fromJson(response, DetailPnInfo.class);
                                llSwitchInfo.addView(addTableView3());
                                break;
                        }
                    }
                });
    }


    /**
     * 灾害点详细信息
     */
    private void showDialogDetails(final String id) {

        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_disaster_detail_info, null);
        final AlertDialog ss = new AlertDialog.Builder(MainActivity.this).setView(view).create();
        llSwitchInfo = (LinearLayout) view.findViewById(R.id.ll_switch_info);
        MyRadioGroup myrg = (MyRadioGroup) view.findViewById(R.id.myrg);
        myrg.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                llSwitchInfo.removeAllViews();
                switch (checkedId) {
                    case R.id.rb_detail_1_1:
                        setOkhttpDetails(id, getResources().getString(R.string.four_person_info), 1);
                        break;
                    case R.id.rb_detail_1_2:
                        setOkhttpDetails(maps.get(id), getResources().getString(R.string.selectByDisNo), 2);
                        break;
                    case R.id.rb_detail_1_3:
                        setOkhttpDetails(id, getResources().getString(R.string.selectFeaPicById), 3);
                        break;
                    case R.id.rb_detail_1_4:
                        YanShi(3000);
                        llSwitchInfo.addView(addTextView("暂无视频资源"));
                        break;
                    case R.id.rb_detail_2_1:
                        setOkhttpDetails(maps.get(id), getResources().getString(R.string.selectDisCardByDisNo), 5);
                        break;
                    case R.id.rb_detail_2_2:
                        setOkhttpDetails(maps.get(id), getResources().getString(R.string.selectHeCardByDisNo), 6);
                        break;
                    case R.id.rb_detail_2_3:
                        setOkhttpDetails(maps.get(id), getResources().getString(R.string.selectPnInfoByDisNo), 7);
                        break;
                    case R.id.rb_detail_2_4:
                        String info1 = "告警时间：2015-04-07\n" + "\n"
                                + "告警缘由：隐患点有异常\n" + "\n"
                                + "处置时间：2015-04-08\n" + "\n"
                                + "是否处置：是\n" + "\n"
                                + "处置结果：已提醒并疏散应还的周围群众并做异常报告上报\n" + "\n";
                        llSwitchInfo.addView(addTextView(info1));
                        break;
                    case R.id.rb_detail_3_1:
                        YanShi(3000);
                        llSwitchInfo.addView(addTextView("暂无全景图片资源"));
                        break;
                    case R.id.rb_detail_3_2:
                        String info2 = "告警时间:2015-04-07\n" + "\n"
                                + "本次预警方式：呼喊，电话\n" + "\n"
                                + "本次灾害等级:小型\n" + "\n"
                                + "预定疏散路线:垂直主滑坡方向，沿滑坡两侧撤离\n" + "\n"
                                + "处置意见：加强巡查工作，做好监测记录及时向上级反映\n" + "\n"
                                + "是否处置：是\n" + "\n";
                        llSwitchInfo.addView(addTextView(info2));
                        break;
                    case R.id.rb_detail_3_3:
                        String info3 = "危岩评估记录\n" + "\n"
                                + "    2014年5月4日危岩发生垮塌，垮塌方量约8m3，造成道路堵塞和民房破坏。 该危岩带直接威胁下方居民约13户55人生命财产安全，在校师生约165人， 和乡镇唯一进出道路的畅通，路过此地的车流量及人员较多，每天数千人（次）车辆。 该危岩带极大制约了村（乡）的建设发展规划，影响着附近居民的正常生产、生活。 为了彻底消除该地段危岩带的安全隐患，国土资源和房屋管理局拟将该处危岩带申报区级财政地质灾害防治资金， 特于2015年3月委托重庆市地质灾害防治工程勘查设计院对该危岩带进行应急抢险勘查。\n" + "\n"
                                + "    经重庆市地质灾害防治工程勘查设计院对该危岩进行勘察，根据危岩的稳定性分析和危害性预测结果， 对该危岩进行及时合理的治理是必要的，会带来较好的经济效益和社会效益。可避免和减轻其对环境与生态的不良危害， 美化旅游环境，人们得以一个安全、优美、舒适的学习、生活场所；勘查区危岩的治理，能最大限度的开发利用土地资源， 对地方经济发展无疑起到促进作用。\n" + "\n"
                                + "    (1)隐患点地理位置较重要，是进出的重要通道、地质灾害活动频繁，危害性较大，应尽快开展治理工程， 并加强监测工作。隐患点的存在直接威胁下方居民约13户55人生命财产安全，在校师生约165人。\n" + "\n";
                        llSwitchInfo.addView(addTextView(info3));
                        break;
                }
            }
        });

        ss.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                rg.check(R.id.rbtn_base_info);
            }
        });

        ss.show();

    }

    /**
     * 延时显示WaitingDialog
     *
     * @param time
     */
    private void YanShi(int time) {
        waitingDialog = WaitingDialog.createLoadingDialog(this, "正在请求中...");
        new Handler().postDelayed(new Runnable() {

            public void run() {
                WaitingDialog.closeDialog(waitingDialog);
            }

        }, time);
    }


    /**
     * 添加TextView
     *
     * @return
     */
    private View addPersonView() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);       //LayoutInflater inflater1=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater3 = LayoutInflater.from(this);
        View view = inflater3.inflate(R.layout.activity_recycleview, null);
        view.setLayoutParams(lp);
        RecyclerView rc = (RecyclerView) view.findViewById(R.id.rc_disaster_photo);
        //传入所有列数的最小公倍数，1和4的最小公倍数为4，即意味着每一列将被分为4格
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        //设置表格，根据position计算在该position处1列占几格数据
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                return 1;
            }
        });
        rc.setLayoutManager(gridLayoutManager);
        rc.setAdapter(new RcPersonAdapter(this, mDetailPersonInfo));
        return view;
    }

    /**
     * 添加TextView
     *
     * @return
     */
    private View addPhotoView() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);       //LayoutInflater inflater1=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater3 = LayoutInflater.from(this);
        View view = inflater3.inflate(R.layout.activity_disphoto, null);
        view.setLayoutParams(lp);
        RecyclerView rc1 = (RecyclerView) view.findViewById(R.id.rc_photo_neirong_1);
        RecyclerView rc2 = (RecyclerView) view.findViewById(R.id.rc_photo_neirong_2);
        RecyclerView rc3 = (RecyclerView) view.findViewById(R.id.rc_photo_neirong_3);
        RecyclerView rc4 = (RecyclerView) view.findViewById(R.id.rc_photo_neirong_4);
        String[] ss;
        List<String> mList1 = new ArrayList<>();
        List<String> mList2 = new ArrayList<>();
        List<String> mList3 = new ArrayList<>();
        List<String> mList4 = new ArrayList<>();
        for (int i = 0; i < mDetailPhoto.getData().size(); i++) {
            switch (mDetailPhoto.getData().get(i).getType()) {
                case "1":
                    ss = mDetailPhoto.getData().get(i).getUrl().split(",");
                    mList1 = Arrays.asList(ss);
                    Log.d("limeng", "mList1" + mList1.toString());
                    break;
                case "2":
                    ss = mDetailPhoto.getData().get(i).getUrl().split(",");
                    mList2 = Arrays.asList(ss);
                    Log.d("limeng", "mList2" + mList2.toString());
                    break;
                case "3":
                    ss = mDetailPhoto.getData().get(i).getUrl().split(",");
                    mList3 = Arrays.asList(ss);
                    Log.d("limeng", "mList3" + mList3.toString());
                    break;
                case "4":
                    ss = mDetailPhoto.getData().get(i).getUrl().split(",");
                    mList4 = Arrays.asList(ss);
                    Log.d("limeng", "mList4" + mList4.toString());
                    break;
            }

        }
        rc1.setLayoutManager(new LinearLayoutManager(this));
        RcPhotoAdapter rcAdapter1 = new RcPhotoAdapter(this, mList1);
        RcPhotoAdapter rcAdapter2 = new RcPhotoAdapter(this, mList2);
        RcPhotoAdapter rcAdapter3 = new RcPhotoAdapter(this, mList3);
        RcPhotoAdapter rcAdapter4 = new RcPhotoAdapter(this, mList4);
        final View view1 = getLayoutInflater().inflate(R.layout.dialog_pic, null);
        final ImageView imageView = (ImageView) view1.findViewById(R.id.iv_pic);
        final AlertDialog alertDialog = new AlertDialog.Builder(context).setView(view1).create();
        rcAdapter1.setItemClickListener(new RcPhotoAdapter.onItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                alertDialog.show();
                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
                lp.width = (int) (display.getWidth());
                lp.height = (int) (display.getHeight());
                alertDialog.getWindow().setAttributes(lp);
                String url = view.getTag() + "";
                Glide.with(context).load(url)
                        .placeholder(R.mipmap.downloading)
                        .error(R.mipmap.download_pass)
                        .into(imageView);
            }
        });
        rcAdapter2.setItemClickListener(new RcPhotoAdapter.onItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                alertDialog.show();
                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
                lp.width = (int) (display.getWidth());
                lp.height = (int) (display.getHeight());
                alertDialog.getWindow().setAttributes(lp);
                String url = view.getTag() + "";
                Glide.with(context).load(url)
                        .placeholder(R.mipmap.downloading)
                        .error(R.mipmap.download_pass)
                        .into(imageView);
            }
        });
        rcAdapter3.setItemClickListener(new RcPhotoAdapter.onItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                alertDialog.show();
                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
                lp.width = (int) (display.getWidth());
                lp.height = (int) (display.getHeight());
                alertDialog.getWindow().setAttributes(lp);
                String url = view.getTag() + "";
                Glide.with(context).load(url)
                        .placeholder(R.mipmap.downloading)
                        .error(R.mipmap.download_pass)
                        .into(imageView);
            }
        });
        rcAdapter4.setItemClickListener(new RcPhotoAdapter.onItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                alertDialog.show();
                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
                lp.width = (int) (display.getWidth());
                lp.height = (int) (display.getHeight());
                alertDialog.getWindow().setAttributes(lp);
                String url = view.getTag() + "";
                Glide.with(context).load(url)
                        .placeholder(R.mipmap.downloading)
                        .error(R.mipmap.download_pass)
                        .into(imageView);
            }
        });
        rc1.setAdapter(rcAdapter1);
        rc2.setLayoutManager(new LinearLayoutManager(this));
        rc2.setAdapter(rcAdapter2);
        rc3.setLayoutManager(new LinearLayoutManager(this));
        rc3.setAdapter(rcAdapter3);
        rc4.setLayoutManager(new LinearLayoutManager(this));
        rc4.setAdapter(rcAdapter4);
        return view;
    }


    /**
     * 添加TextView
     *
     * @return
     */
    private View addTextView(String info) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);       //LayoutInflater inflater1=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//      LayoutInflater inflater2 = getLayoutInflater();
        LayoutInflater inflater3 = LayoutInflater.from(this);
        View view = inflater3.inflate(R.layout.activity_tv_text, null);
        view.setLayoutParams(lp);
        TextView tv = (TextView) view.findViewById(R.id.tv_switch_text);
        tv.setText(info);
        return view;
    }

    /**
     * 添加table1:防灾明白卡
     *
     * @return
     */
    private View addTableView1() {
        DetailDisCard.DataBean mFCard = mDetailDisCard.getData();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);       //LayoutInflater inflater1=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater3 = LayoutInflater.from(this);
        View view = inflater3.inflate(R.layout.activity_table_1, null);
        view.setLayoutParams(lp);
        if (mFCard != null) {
            ((TextView) view.findViewById(R.id.tv_tavle1_1)).setText(mFCard.getDPosition() == null ? "" : mFCard.getDPosition());
            ((TextView) view.findViewById(R.id.tv_tavle1_2)).setText(mFCard.getDType() == null ? "" : mFCard.getDType());
            ((TextView) view.findViewById(R.id.tv_tavle1_3)).setText(mFCard.getDInduceFactor() == null ? "" : mFCard.getDInduceFactor());
            ((TextView) view.findViewById(R.id.tv_tavle1_4)).setText(mFCard.getDThreat() == null ? "" : mFCard.getDThreat());
            ((TextView) view.findViewById(R.id.tv_tavle1_5)).setText(mFCard.getDMonitorMan() == null ? "" : mFCard.getDMonitorMan());
            ((TextView) view.findViewById(R.id.tv_tavle1_6)).setText(mFCard.getDMonitorPhone() == null ? "" : mFCard.getDMonitorPhone());
            ((TextView) view.findViewById(R.id.tv_tavle1_7)).setText(mFCard.getDMonitorSign() == null ? "" : mFCard.getDMonitorSign());
            ((TextView) view.findViewById(R.id.tv_tavle1_8)).setText(mFCard.getDAlarmType() == null ? "" : mFCard.getDAlarmType());
            ((TextView) view.findViewById(R.id.tv_tavle1_9)).setText(mFCard.getDMonitorJudge() == null ? "" : mFCard.getDMonitorJudge());
            ((TextView) view.findViewById(R.id.tv_tavle1_10)).setText(mFCard.getDEPlace() == null ? "" : mFCard.getDEPlace());
            ((TextView) view.findViewById(R.id.tv_tavle1_11)).setText(mFCard.getDESignal() == null ? "" : mFCard.getDESignal());
            ((TextView) view.findViewById(R.id.tv_tavle1_12)).setText(mFCard.getDELine() == null ? "" : mFCard.getDELine());
            ((TextView) view.findViewById(R.id.tv_tavle1_13)).setText(mFCard.getDExcludeMan() == null ? "" : mFCard.getDExcludeMan());
            ((TextView) view.findViewById(R.id.tv_tavle1_14)).setText(mFCard.getDExcludePhone() == null ? "" : mFCard.getDExcludePhone());
            ((TextView) view.findViewById(R.id.tv_tavle1_15)).setText(mFCard.getDSecurityMan() == null ? "" : mFCard.getDSecurityMan());
            ((TextView) view.findViewById(R.id.tv_tavle1_16)).setText(mFCard.getDSecurityPhone() == null ? "" : mFCard.getDSecurityPhone());
            ((TextView) view.findViewById(R.id.tv_tavle1_17)).setText(mFCard.getDDocMan() == null ? "" : mFCard.getDDocMan());
            ((TextView) view.findViewById(R.id.tv_tavle1_18)).setText(mFCard.getDDocPhone() == null ? "" : mFCard.getDDocPhone());
            ((TextView) view.findViewById(R.id.tv_tavle1_19)).setText(mFCard.getDGrantUnit() == null ? "" : mFCard.getDGrantUnit());
            ((TextView) view.findViewById(R.id.tv_tavle1_20)).setText(mFCard.getDHoldUnit() == null ? "" : mFCard.getDHoldUnit());
            ((TextView) view.findViewById(R.id.tv_tavle1_21)).setText(mFCard.getDGrantPhone() == null ? "" : mFCard.getDGrantPhone());
            ((TextView) view.findViewById(R.id.tv_tavle1_22)).setText(mFCard.getDHoldPhone() == null ? "" : mFCard.getDHoldPhone());
            ((TextView) view.findViewById(R.id.tv_tavle1_23)).setText(mFCard.getDGrantDate() == null ? "" : mFCard.getDGrantDate());
            ((TextView) view.findViewById(R.id.tv_tavle1_24)).setText(mFCard.getDHoldDate() == null ? "" : mFCard.getDHoldDate());
        }

        return view;
    }

    /**
     * 添加table2:
     *
     * @return
     */
    private View addTableView2() {
        DetailHeCard.DataBean mHedgeCard = mDetailHeCard.getData();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);       //LayoutInflater inflater1=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater3 = LayoutInflater.from(this);
        View view = inflater3.inflate(R.layout.activity_table_2, null);
        view.setLayoutParams(lp);
        if (mHedgeCard != null) {
            ((TextView) view.findViewById(R.id.tv_table2_1)).setText(mHedgeCard.getHFamilyName() == null ? "" : mHedgeCard.getHFamilyName());
            ((TextView) view.findViewById(R.id.tv_table2_2)).setText(mHedgeCard.getHFamilyNum() == null ? "" : mHedgeCard.getHFamilyNum());
            ((TextView) view.findViewById(R.id.tv_table2_3)).setText(mHedgeCard.getHHouseType() == null ? "" : mHedgeCard.getHHouseType());
            ((TextView) view.findViewById(R.id.tv_table2_4)).setText(mHedgeCard.getHAddress() == null ? "" : mHedgeCard.getHAddress());

            ((TextView) view.findViewById(R.id.tv_table2_5)).setText(mHedgeCard.getName1() == null ? "" : mHedgeCard.getName1());
            ((TextView) view.findViewById(R.id.tv_table2_6)).setText(mHedgeCard.getSex1() == null ? "" : mHedgeCard.getSex1());
            ((TextView) view.findViewById(R.id.tv_table2_7)).setText(mHedgeCard.getAge1() + "");

            ((TextView) view.findViewById(R.id.tv_table2_8)).setText(mHedgeCard.getName2() == null ? "" : mHedgeCard.getName2());
            ((TextView) view.findViewById(R.id.tv_table2_9)).setText(mHedgeCard.getSex2() == null ? "" : mHedgeCard.getSex2());
            ((TextView) view.findViewById(R.id.tv_table2_10)).setText(mHedgeCard.getAge2() + "");

            ((TextView) view.findViewById(R.id.tv_table2_11)).setText(mHedgeCard.getName3() == null ? "" : mHedgeCard.getName3());
            ((TextView) view.findViewById(R.id.tv_table2_12)).setText(mHedgeCard.getSex3() == null ? "" : mHedgeCard.getSex3());
            ((TextView) view.findViewById(R.id.tv_table2_13)).setText(mHedgeCard.getAge3() + "");

            ((TextView) view.findViewById(R.id.tv_table2_14)).setText(mHedgeCard.getName4() == null ? "" : mHedgeCard.getName4());
            ((TextView) view.findViewById(R.id.tv_table2_15)).setText(mHedgeCard.getSex4() == null ? "" : mHedgeCard.getSex4());
            ((TextView) view.findViewById(R.id.tv_table2_16)).setText(mHedgeCard.getAge4() + "");


            ((TextView) view.findViewById(R.id.tv_table2_17)).setText(mHedgeCard.getHDisType() == null ? "" : mHedgeCard.getHDisType());
            ((TextView) view.findViewById(R.id.tv_table2_18)).setText(mHedgeCard.getHDisScale() == null ? "" : mHedgeCard.getHDisScale());
            ((TextView) view.findViewById(R.id.tv_table2_19)).setText(mHedgeCard.getHDisRelationship() == null ? "" : mHedgeCard.getHDisRelationship());
            ((TextView) view.findViewById(R.id.tv_table2_20)).setText(mHedgeCard.getHDisFactor() == null ? "" : mHedgeCard.getHDisFactor());
            ((TextView) view.findViewById(R.id.tv_table2_21)).setText(mHedgeCard.getHDisMatters() == null ? "" : mHedgeCard.getHDisMatters());
            ((TextView) view.findViewById(R.id.tv_table2_22)).setText(mHedgeCard.getHPreMan() == null ? "" : mHedgeCard.getHPreMan());
            ((TextView) view.findViewById(R.id.tv_table2_23)).setText(mHedgeCard.getHPrePhone() == null ? "" : mHedgeCard.getHPrePhone());
            ((TextView) view.findViewById(R.id.tv_table2_24)).setText(mHedgeCard.getHPreSignal() == null ? "" : mHedgeCard.getHPreSignal());
            ((TextView) view.findViewById(R.id.tv_table2_25)).setText(mHedgeCard.getHSignalMan() == null ? "" : mHedgeCard.getHSignalMan());
            ((TextView) view.findViewById(R.id.tv_table2_26)).setText(mHedgeCard.getHSignalPhone() == null ? "" : mHedgeCard.getHSignalPhone());
            ((TextView) view.findViewById(R.id.tv_table2_27)).setText(mHedgeCard.getHEvaLine() == null ? "" : mHedgeCard.getHEvaLine());
            ((TextView) view.findViewById(R.id.tv_table2_28)).setText(mHedgeCard.getHEvaPlacement() == null ? "" : mHedgeCard.getHEvaPlacement());
            ((TextView) view.findViewById(R.id.tv_table2_29)).setText(mHedgeCard.getHPlacementMan() == null ? "" : mHedgeCard.getHPlacementMan());
            ((TextView) view.findViewById(R.id.tv_table2_30)).setText(mHedgeCard.getHPlacementPhone() == null ? "" : mHedgeCard.getHPlacementPhone());
            ((TextView) view.findViewById(R.id.tv_table2_31)).setText(mHedgeCard.getHAmbulanceUnit() == null ? "" : mHedgeCard.getHAmbulanceUnit());
            ((TextView) view.findViewById(R.id.tv_table2_32)).setText(mHedgeCard.getHAmbulanceMan() == null ? "" : mHedgeCard.getHAmbulanceMan());
            ((TextView) view.findViewById(R.id.tv_table2_33)).setText(mHedgeCard.getHAmbulancePhone() == null ? "" : mHedgeCard.getHAmbulancePhone());
            ((TextView) view.findViewById(R.id.tv_table2_34)).setText(mHedgeCard.getHGrantUnit() == null ? "" : mHedgeCard.getHGrantUnit());
            ((TextView) view.findViewById(R.id.tv_table2_35)).setText(mHedgeCard.getHHolder() == null ? "" : mHedgeCard.getHHolder());
            ((TextView) view.findViewById(R.id.tv_table2_36)).setText(mHedgeCard.getHGrantPhone() == null ? "" : mHedgeCard.getHGrantPhone());
            ((TextView) view.findViewById(R.id.tv_table2_37)).setText(mHedgeCard.getHHolderPhone() == null ? "" : mHedgeCard.getHHolderPhone());
            ((TextView) view.findViewById(R.id.tv_table2_38)).setText(mHedgeCard.getHGrantDate() == null ? "" : mHedgeCard.getHGrantDate());
            ((TextView) view.findViewById(R.id.tv_table2_39)).setText(mHedgeCard.getHHolderDate() == null ? "" : mHedgeCard.getHHolderDate());
        }
        return view;
    }


    /**
     * 添加table2:
     *
     * @return
     */
    private View addTableView3() {
        DetailPnInfo.DataBean mDetailPn = mDetailPnInfo.getData();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);       //LayoutInflater inflater1=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//      LayoutInflater inflater2 = getLayoutInflater();
        LayoutInflater inflater3 = LayoutInflater.from(this);
        View view = inflater3.inflate(R.layout.activity_table_3, null);
        view.setLayoutParams(lp);
        if (mDetailPn != null) {
            ((TextView) view.findViewById(R.id.tv_table3_1)).setText(mDetailPn.getPDisName() == null ? "" : mDetailPn.getPDisName());
            ((TextView) view.findViewById(R.id.tv_table3_2)).setText(mDetailPn.getPNo() == null ? "" : mDetailPn.getPNo());
            ((TextView) view.findViewById(R.id.tv_table3_3)).setText("***");
            ((TextView) view.findViewById(R.id.tv_table3_4)).setText(mDetailPn.getPSurveyWay() == null ? "" : mDetailPn.getPSurveyWay());
            ((TextView) view.findViewById(R.id.tv_table3_5)).setText(mDetailPn.getPSurveyCycle() == null ? "" : mDetailPn.getPSurveyCycle());
            ((TextView) view.findViewById(R.id.tv_table3_6)).setText(mDetailPn.getPSurveyMeans() == null ? "" : mDetailPn.getPSurveyMeans());
            ((TextView) view.findViewById(R.id.tv_table3_7)).setText(mDetailPn.getPWallPart() == null ? "" : mDetailPn.getPWallPart());
            ((TextView) view.findViewById(R.id.tv_table3_8)).setText(mDetailPn.getPType() == null ? "" : mDetailPn.getPType());
            ((TextView) view.findViewById(R.id.tv_table3_9)).setText(mDetailPn.getPEMeasure() == null ? "" : mDetailPn.getPEMeasure());
            ((TextView) view.findViewById(R.id.tv_table3_10)).setText(mDetailPn.getPOutLine() == null ? "" : mDetailPn.getPOutLine());
            ((TextView) view.findViewById(R.id.tv_table3_11)).setText(mDetailPn.getPEOrder() == null ? "" : mDetailPn.getPEOrder());
            ((TextView) view.findViewById(R.id.tv_table3_12)).setText(mDetailPn.getPMonitorMan() == null ? "" : mDetailPn.getPMonitorMan());
            ((TextView) view.findViewById(R.id.tv_table3_13)).setText(mDetailPn.getPMonitorPhone() == null ? "" : mDetailPn.getPMonitorPhone());
            ((TextView) view.findViewById(R.id.tv_table3_14)).setText(mDetailPn.getPVillageHead() == null ? "" : mDetailPn.getPVillageHead());
            ((TextView) view.findViewById(R.id.tv_table3_15)).setText(mDetailPn.getPVillagePhone() == null ? "" : mDetailPn.getPVillagePhone());
            ((TextView) view.findViewById(R.id.tv_table3_16)).setText(mDetailPn.getPGroupHead() == null ? "" : mDetailPn.getPGroupHead());
            ((TextView) view.findViewById(R.id.tv_table3_17)).setText(mDetailPn.getPGroupPhone() == null ? "" : mDetailPn.getPGroupPhone());
            ((TextView) view.findViewById(R.id.tv_table3_18)).setText(mDetailPn.getPAlarmCall() == null ? "" : mDetailPn.getPAlarmCall());
            //http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=9&path=5001101060420101.jpg
            Glide.with(context)
                    .load("http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=9&path=" + mDetailPn.getPLinePic())
                    .placeholder(R.mipmap.downloading)
                    .thumbnail(0.1f)
                    .error(R.mipmap.download_pass)
                    .into((ImageView) view.findViewById(R.id.iv_table3_19));
            ((TextView) view.findViewById(R.id.tv_table3_20)).setText(mDetailPn.getPWeaveUnit() == null ? "" : mDetailPn.getPWeaveUnit());
            ((TextView) view.findViewById(R.id.tv_table3_21)).setText(mDetailPn.getPApproveUnit() == null ? "" : mDetailPn.getPApproveUnit());
        }
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_enlarge:
                setEnlarge();
                break;
            case R.id.iv_narrow:
                setNarrow();
                break;
            case R.id.btn_util_detail:
                setOkhttpKuangxuan(detailhttp, detailarea);
                break;
            case R.id.iv_location:
                if (personLocationGraphics.size() > 0) {
                    personLocationGraphics.clear();
                } else {
                    personLocation();
                    if (!layers.contains(chongqingLayer)) {
                        layers.clear();
                        elevationSources.clear();
                        layers.add(chongqingLayer);
                        clearAllGraphics();
                    }
                }
                break;
            case R.id.iv_search_main:
                if (searchPersonGraphics.size() > 0 || searchPlaceGraphics.size() > 0) {
                    searchPlaceGraphics.clear();
                    searchPersonGraphics.clear();
                } else {
                    if (!layers.contains(lowImageLayer)) {
                        layers.clear();
                        elevationSources.clear();
                        layers.add(lowImageLayer);
                        layers.add(highImageLayer);
                        elevationSources.add(elevationSource);
                        Camera camera = new Camera(28.769167, 106.910399, 50000.0, 0, 20, 0.0);
                        sceneView.setViewpointCameraAsync(camera, 2);
                    }
                    ToSearch();
                }
                break;
            case R.id.iv_luopan:
                resetPosition();
                break;
            case R.id.iv_area_back:
                setAreaBack();
                break;
            case R.id.iv_data_back:
                setDataBack();
                break;
            case R.id.ll_rainfall:
                llMoreStateBefore = llMoreState;
                llMoreState = 1;
                setRainfallMore();
                setDisasterLegend(R.layout.activity_rainfall_legend, 1);
                if (llMoreStateBefore != 1) {
                    //waitingDialog = WaitingDialog.createLoadingDialog(this, "正在请求中...");
                    layers.clear();
                    elevationSources.clear();
                    layers.add(vectorLayer);
                    Camera camera = new Camera(28.769167, 106.910399, 50000.0, 0, 20, 0.0);
                    sceneView.setViewpointCamera(camera);
                }
                break;
            case R.id.ll_dangerpoint:
                llMoreStateBefore = llMoreState;
                llMoreState = 2;
                setRainfallMore();
                setDisasterLegend(R.layout.activity_disaster_legend, 2);
                if (llMoreStateBefore != 2) {
                    // waitingDialog = WaitingDialog.createLoadingDialog(this, "正在请求中...");
                    layers.clear();
                    elevationSources.clear();
                    layers.add(lowImageLayer);
                    layers.add(highImageLayer);
                    elevationSources.add(elevationSource);
                    Camera camera = new Camera(28.769167, 106.910399, 50000.0, 0, 20, 0.0);
                    sceneView.setViewpointCamera(camera);
                }
                break;
            case R.id.ll_staff:
                llMoreStateBefore = llMoreState;
                llMoreState = 3;
                setRainfallMore();
                if (view != null) {
                    rlMain.removeView(view);
                }
                if (llMoreStateBefore != 3) {
                    layers.clear();
                    elevationSources.clear();

                    layers.add(dianziLayer);
                    Camera camera = new Camera(28.769167, 106.910399, 50000.0, 0, 20, 0.0);
                    sceneView.setViewpointCamera(camera);
                }
                break;
            case R.id.ll_equipment:
                llMoreStateBefore = llMoreState;
                llMoreState = 4;
                setRainfallMore();
                if (view != null) {
                    rlMain.removeView(view);
                }
                if (llMoreStateBefore != 4) {
                    layers.clear();
                    elevationSources.clear();
                    layers.add(lowImageLayer);
                    layers.add(highImageLayer);
                    elevationSources.add(elevationSource);
                    Camera camera = new Camera(29.375, 107.58056, 50000.0, 0, 20, 0.0);
                    sceneView.setViewpointCamera(camera);
                }
                break;
            case R.id.ll_qxsy:
                llMoreStateBefore = llMoreState;
                llMoreState = 5;
                setRainfallMore();
                if (view != null) {
                    rlMain.removeView(view);
                }
                break;
            case R.id.ll_xingzheng:
                llMoreStateBefore = llMoreState;
                llMoreState = 6;
                setRainfallMore();
                addWeather();
                if (view != null) {
                    rlMain.removeView(view);
                }
                if (llMoreStateBefore != 6 && !layers.contains(xingZhengLayer)) {
                    layers.clear();
                    elevationSources.clear();
                    layers.add(xingZhengLayer);
                    Camera camera = new Camera(28.769167, 106.910399, 50000.0, 0, 20, 0.0);
                    sceneView.setViewpointCamera(camera);
                }
                break;
            case R.id.btn_util:
                setUtilBack();
                break;
        }
    }

    private void setNarrow() {
        Camera currentViewpointCamera = sceneView.getCurrentViewpointCamera();
        Camera elevate = currentViewpointCamera.elevate(4000);
        sceneView.setViewpointCameraAsync(elevate, 1);

    }

    private void setEnlarge() {
        Camera currentViewpointCamera = sceneView.getCurrentViewpointCamera();
        Camera elevate = currentViewpointCamera.elevate(-4000);
        sceneView.setViewpointCameraAsync(elevate, 1);
    }

    private void personLocation() {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_person_location, null);
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.rg_person_location);
        final RadioButton rbQcqf = (RadioButton) view.findViewById(R.id.rb_qcqf_location);
        final RadioButton rbZhushou = (RadioButton) view.findViewById(R.id.rb_zsdz_location);
        final RadioButton rbPianqu = (RadioButton) view.findViewById(R.id.rb_pqfz_location);
        final RadioButton rbZhihui = (RadioButton) view.findViewById(R.id.rb_zhzx_location);
        RadioButton rbLocation = (RadioButton) view.findViewById(R.id.rb_person_location);
        TextView tvStart = (TextView) view.findViewById(R.id.tv_start_time);
        TextView tvEnd = (TextView) view.findViewById(R.id.tv_end_time);
        Spinner spList = (Spinner) view.findViewById(R.id.sp_person_list);
        Button btnSearch = (Button) view.findViewById(R.id.btn_location_search);
        final LinearLayout llTime = (LinearLayout) view.findViewById(R.id.ll_check_time);
        MyOnClickListener listener = new MyOnClickListener(rbQcqf, rbZhushou, rbPianqu, rbZhihui, tvStart, tvEnd, rbLocation, spList);
        rbQcqf.setOnClickListener(listener);
        rbZhushou.setOnClickListener(listener);
        rbPianqu.setOnClickListener(listener);
        rbZhihui.setOnClickListener(listener);
        btnSearch.setOnClickListener(listener);
        tvStart.setOnClickListener(listener);
        tvEnd.setOnClickListener(listener);
        personAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, personList);
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spList.setAdapter(personAdapter);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb_person_location:
                        llTime.setVisibility(View.GONE);
                        rbQcqf.setChecked(false);
                        rbPianqu.setChecked(false);
                        rbZhihui.setChecked(false);
                        rbZhushou.setChecked(false);
                        personList.clear();
                        personAdapter.notifyDataSetChanged();
                        break;
                    case R.id.rb_person_line:
                        llTime.setVisibility(View.VISIBLE);
                        rbQcqf.setChecked(false);
                        rbPianqu.setChecked(false);
                        rbZhihui.setChecked(false);
                        rbZhushou.setChecked(false);
                        personList.clear();
                        personAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });
        locationDialog = new AlertDialog.Builder(MainActivity.this)
                .setView(view)
                .show();
    }

    private class MyOnClickListener implements View.OnClickListener {
        RadioButton rbQcqf, rbZsdz, rbPqfz, rbZhzx, rbLocation;
        TextView tvStart, tvEnd;
        Spinner spList;
        int type;
        private String currentTime = new SimpleDateFormat("yyyy年MM月dd日hh:mm").format(new Date());

        MyOnClickListener(RadioButton rbQcqf, RadioButton rbZsdz, RadioButton rbPqfz, RadioButton rbZhzx, TextView tvStart, TextView tvEnd, RadioButton rbLocation, Spinner spList) {
            this.rbQcqf = rbQcqf;
            this.rbZsdz = rbZsdz;
            this.rbPqfz = rbPqfz;
            this.rbZhzx = rbZhzx;
            this.tvStart = tvStart;
            this.tvEnd = tvEnd;
            this.rbLocation = rbLocation;
            this.spList = spList;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rb_qcqf_location:
                    rbQcqf.setChecked(true);
                    rbZsdz.setChecked(false);
                    rbPqfz.setChecked(false);
                    rbZhzx.setChecked(false);
                    getPersonList(1, areaCode);
                    break;
                case R.id.rb_zsdz_location:
                    rbQcqf.setChecked(false);
                    rbZsdz.setChecked(true);
                    rbPqfz.setChecked(false);
                    rbZhzx.setChecked(false);
                    getPersonList(2, areaCode);
                    break;
                case R.id.rb_pqfz_location:
                    rbQcqf.setChecked(false);
                    rbZsdz.setChecked(false);
                    rbPqfz.setChecked(true);
                    rbZhzx.setChecked(false);
                    getPersonList(3, areaCode);
                    break;
                case R.id.rb_zhzx_location:
                    rbQcqf.setChecked(false);
                    rbZsdz.setChecked(false);
                    rbPqfz.setChecked(false);
                    rbZhzx.setChecked(true);
                    getPersonList(4, "500000");
                    break;
                case R.id.tv_start_time:
                    new DateTimePickUtil(MainActivity.this, currentTime).dateTimePicKDialog(tvStart);
                    break;
                case R.id.tv_end_time:
                    new DateTimePickUtil(MainActivity.this, currentTime).dateTimePicKDialog(tvEnd);
                    break;
                case R.id.btn_location_search:
                    personLocationData = null;
                    if (rbQcqf.isChecked()) {
                        type = 1;
                    } else if (rbZsdz.isChecked()) {
                        type = 2;
                    } else if (rbPqfz.isChecked()) {
                        type = 3;
                    } else {
                        type = 4;
                    }
                    if (rbLocation.isChecked()) {
                        if (rbQcqf.isChecked() || rbZsdz.isChecked() || rbPqfz.isChecked() || rbZhzx.isChecked()) {
                            if (rbZhzx.isChecked()) {
                                getPersonLocation(personImei.get(spList.getSelectedItemPosition()), type);
                            }
                        } else {
                            Toast.makeText(context, "请先选择人员类型！", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String start = tvStart.getText().toString();
                        String end = tvEnd.getText().toString();
                        if (TextUtils.isEmpty(start) || TextUtils.isEmpty(end)) {
                            Toast.makeText(context, "请先选择好时间！", Toast.LENGTH_SHORT).show();
                        } else if (!(rbQcqf.isChecked() || rbZsdz.isChecked() || rbPqfz.isChecked() || rbZhzx.isChecked())) {
                            Toast.makeText(context, "请先选择人员类型！", Toast.LENGTH_SHORT).show();
                        } else {
                            if (rbZhzx.isChecked()) {
                                Log.d(TAG, personImei.get(spList.getSelectedItemPosition()) + "/" + spList.getSelectedItemPosition());

                                getPersonLine(personImei.get(spList.getSelectedItemPosition()), type, start, end);
                            }
                        }
                    }
                    break;
            }
        }

        private void getPersonLocation(String s, int type) {
            Log.d(TAG, "imei:" + s);
            waitingDialog = WaitingDialog.createLoadingDialog(context, "正在请求...");
            OkHttpUtils.get().url(getResources().getString(R.string.base_http) + "location/manLastLocaton/" + s + "/" + type)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(context, "服务器连接失败！", Toast.LENGTH_SHORT).show();
                            WaitingDialog.closeDialog(waitingDialog);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Gson gson = new Gson();
                            personLocationInfo personLocationInfo = gson.fromJson(response, personLocationInfo.class);
                            personLocationData = personLocationInfo.getData();
                            try {
                                if (personLocationData.size() > 0) {
                                    setPersonLocationOverlay(personLocationData);
                                }
                                WaitingDialog.closeDialog(waitingDialog);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }

        private void getPersonList(final int type, String code) {
            personList.clear();
            personImei.clear();
            waitingDialog = WaitingDialog.createLoadingDialog(context, "正在请求...");
            OkHttpUtils.get().url(getResources().getString(R.string.base_http) + "location/manNameByType/" + code + "/" + type)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(context, "服务器连接失败！", Toast.LENGTH_SHORT).show();
                            WaitingDialog.closeDialog(waitingDialog);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                JSONObject object = new JSONObject(response);
                                JSONArray data = (JSONArray) object.get("data");
                                if (type == 4 && rbLocation.isChecked()) {
                                    personList.add("全部");
                                    personImei.add("0");
                                }
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject o = data.getJSONObject(i);
                                    personList.add(o.getString("name"));
                                    if (type == 4 || type == 1) {
                                        personImei.add(o.getString("imei"));
                                    }
                                }
                                personAdapter.notifyDataSetChanged();
                                WaitingDialog.closeDialog(waitingDialog);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }

        private void getPersonLine(String imei, int type, String start, String end) {
            waitingDialog = WaitingDialog.createLoadingDialog(context, "正在请求...");
            OkHttpUtils.get()
                    .url(getResources().getString(R.string.base_http) + "location/manLocus/" + imei + "/" + type + "/" + start + "/" + end)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(context, "网络连接失败！", Toast.LENGTH_SHORT).show();
                            WaitingDialog.closeDialog(waitingDialog);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            WaitingDialog.closeDialog(waitingDialog);
                            Gson gson = new Gson();
                            personLocationInfo info = gson.fromJson(response, personLocationInfo.class);
                            personLocationData = info.getData();
                            if (personLocationData.size() > 0) {
                                setPersonLineOverlay(personLocationData);
                            }
                        }
                    });
        }

    }

    private void setPersonLineOverlay(final List<personLocationInfo.DataBean> data) {
        SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 3);
        PointCollection borderCAtoNV = new PointCollection(SpatialReferences.getWgs84());
        BitmapDrawable startDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.start_icon);
        BitmapDrawable finishDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.finish_icon);
        BitmapDrawable processDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.process_icon);
        for (int i = 0; i < data.size(); i++) {
            borderCAtoNV.add(data.get(i).getLon(), data.get(i).getLat());
            final PictureMarkerSymbol campsiteSymbol;
            if (i == 0) {
                campsiteSymbol = new PictureMarkerSymbol(startDrawable);
                campsiteSymbol.setHeight(100);
                campsiteSymbol.setWidth(80);
            } else if (i == data.size() - 1) {
                campsiteSymbol = new PictureMarkerSymbol(finishDrawable);
                campsiteSymbol.setHeight(100);
                campsiteSymbol.setWidth(80);
            } else {
                campsiteSymbol = new PictureMarkerSymbol(processDrawable);
                campsiteSymbol.setHeight(30);
                campsiteSymbol.setWidth(30);
            }
            campsiteSymbol.loadAsync();
            final int finalI = i;
            campsiteSymbol.addDoneLoadingListener(new Runnable() {
                @Override
                public void run() {
                    Point campsitePoint = new Point(data.get(finalI).getLon(), data.get(finalI).getLat(), SpatialReferences.getWgs84());
                    Graphic campsiteGraphic = new Graphic(campsitePoint, campsiteSymbol);
                    String s = data.get(finalI).getId() + "1";
                    campsiteGraphic.setZIndex(Integer.valueOf(s));
                    personLocationGraphics.add(campsiteGraphic);
                    Camera camara = new Camera(data.get(0).getLat(), data.get(0).getLon(), 5000, 0, 0, 0.0);
                    sceneView.setViewpointCameraAsync(camara, 2);
                    locationDialog.dismiss();
                }
            });
        }
        Polyline polyline = new Polyline(borderCAtoNV);
        personLocationGraphics.add(new Graphic(polyline, lineSymbol));
        locationDialog.dismiss();
    }

    private void setPersonLocationOverlay(final List<personLocationInfo.DataBean> data) throws ParseException {
        for (int i = 0; i < data.size(); i++) {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data.get(i).getTime());
            Date currentDate = new Date();
            long l = currentDate.getTime() - date.getTime();
            String imageUrl = "";
            if (l > 3600000) {
                imageUrl = "http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=14&path=" + "outline" + "/" + data.get(i).getHead_pic().split("\\.")[0] + ".png";
            } else {
                imageUrl = "http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=14&path=" + "online" + "/" + data.get(i).getHead_pic().split("\\.")[0] + ".png";
            }
            final PictureMarkerSymbol campsiteSymbol = new PictureMarkerSymbol(imageUrl);
            campsiteSymbol.setHeight(100);
            campsiteSymbol.setWidth(80);
            campsiteSymbol.loadAsync();
            final int finalI = i;
            campsiteSymbol.addDoneLoadingListener(new Runnable() {
                @Override
                public void run() {
                    Point campsitePoint = new Point(data.get(finalI).getLon(), data.get(finalI).getLat(), SpatialReferences.getWgs84());
                    Graphic campsiteGraphic = new Graphic(campsitePoint, campsiteSymbol);
                    String s = data.get(finalI).getId() + "0";
                    campsiteGraphic.setZIndex(Integer.valueOf(s));
                    personLocationGraphics.add(campsiteGraphic);
                    if (data.size() == 1) {
                        Camera camara = new Camera(data.get(finalI).getLat(), data.get(finalI).getLon(), 10000, 0, 0, 0.0);
                        sceneView.setViewpointCameraAsync(camara, 2);
                        locationDialog.dismiss();
                    } else if (data.size() - 1 == finalI) {
                        Camera camara = new Camera(30.298775, 108.045955, 600000, 0, 0, 0.0);
                        sceneView.setViewpointCameraAsync(camara, 2);
                        locationDialog.dismiss();
                    }
                }
            });
        }
    }


    private void setUtilBack() {
        if (!llUtilState) {
            btnUtil.setText("关闭工具");
            llUtil.setVisibility(View.VISIBLE);
            llUtilState = true;
        } else {
            btnUtil.setText("打开工具");
            btnUtilDetail.setVisibility(View.GONE);
            clear();
            tvMeasureResult.setText("");
            llUtil.setVisibility(View.INVISIBLE);
            llUtilState = false;
        }
    }

    private int searchType1;
    private int searchType2;
    private String[] mItems2;

    /**
     * 搜索
     */
    private void ToSearch() {
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_search, null);
        final Spinner spinner1 = (Spinner) view.findViewById(R.id.spinner1);
        final Spinner spinner2 = (Spinner) view.findViewById(R.id.spinner2);
        final EditText etSearch = (EditText) view.findViewById(R.id.etSearch);
        Button btnSearch = (Button) view.findViewById(R.id.btnSearch);
        final RecyclerView rc = (RecyclerView) view.findViewById(R.id.date_show);
        rc.setLayoutManager(new LinearLayoutManager(context));
        // 建立数据源
        String[] mItems1 = getResources().getStringArray(R.array.search_type_1);
        mItems2 = getResources().getStringArray(R.array.search_type_2_1);
        final String[] mItems21 = getResources().getStringArray(R.array.search_type_2_1);
        final String[] mItems22 = getResources().getStringArray(R.array.search_type_2_2);
        final String[] mItems23 = getResources().getStringArray(R.array.search_type_2_3);
        final String[] mItems24 = getResources().getStringArray(R.array.search_type_2_4);
        final String[] mItems25 = getResources().getStringArray(R.array.search_type_2_5);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems2);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                searchType1 = pos;
                switch (pos) {
                    case 0:
                        mItems2 = mItems21;
                        break;
                    case 1:
                        mItems2 = mItems22;
                        etSearch.setText("滑坡");
                        break;
                    case 2:
                        mItems2 = mItems23;
                        etSearch.setText("张");
                        break;
                    case 3:
                        mItems2 = mItems24;
                        break;
                    case 4:
                        mItems2 = mItems25;
                        break;
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mItems2);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                searchType2 = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = etSearch.getText().toString();
                Log.d("limeng", searchType1 + "----" + searchType2 + mItems2.length);
                int type1 = 0, type2 = 0;
                switch (searchType1) {
                    case 1:
                        type1 = 8;
                        type2 = 27;
                        break;
                    case 2:
                        type1 = 26;
                        type2 = 32 + searchType2 - 1;
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "暂无数据！", Toast.LENGTH_SHORT).show();
                        return;
                }
                waitingDialog = WaitingDialog.createLoadingDialog(context, "正在请求中...");
                OkHttpUtils.get().url("http://183.230.182.149:18081/springmvc/seek/search/500110/" + info + "/" + type1 + "/" + type2)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(getApplicationContext(), "请检查网络！", Toast.LENGTH_SHORT).show();
                                WaitingDialog.closeDialog(waitingDialog);
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                WaitingDialog.closeDialog(waitingDialog);
                                Log.d("limeng", "response=" + response);
                                Gson gson = new Gson();
                                switch (searchType1) {
                                    case 1:
                                        final SearchPlace mSearchPlace = gson.fromJson(response, SearchPlace.class);
                                        RcSearchPlaceAdapter searchPlaceAdapter = new RcSearchPlaceAdapter(context, mSearchPlace);
                                        rc.setAdapter(searchPlaceAdapter);
                                        searchPlaceAdapter.setOnItemClickListener(new RcSearchPlaceAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view) {
                                                int position = rc.getChildAdapterPosition(view);
                                                if (position > 0) {
                                                    searchPlaceBean = mSearchPlace.getData().get(position - 1);
                                                    setSearchPlaceOverlay(searchPlaceBean);
                                                }
                                            }
                                        });
                                        break;
                                    case 2:
                                        final SearchPerson mSearchPerson = gson.fromJson(response, SearchPerson.class);
                                        RcSearchPersonAdapter searchPersonAdapter = new RcSearchPersonAdapter(context, mSearchPerson);
                                        rc.setAdapter(searchPersonAdapter);
                                        searchPersonAdapter.setOnItemClickListener(new RcSearchPersonAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view) {
                                                int position = rc.getChildAdapterPosition(view);
                                                if (position > 0) {
                                                    searchPersonBean = mSearchPerson.getData().get(position - 1);
                                                    setSearchPersonOverlay(searchPersonBean);
                                                }
                                            }
                                        });
                                        break;
                                }
                            }
                        });
            }
        });

        searchDialog = new AlertDialog.Builder(MainActivity.this).setView(view).create();
        searchDialog.show();

        //http://183.230.182.149:18081/springmvc/seek/search/"+areaCode+"/"+searchValue+"/"+searchType1+"/"+searchType2
        //http://183.230.182.149:18081/springmvc/seek/search/500110/s/8/27
        //http://183.230.182.149:18081/springmvc/seek/search/500110,/%E5%BC%A0/26/32
        //http://183.230.182.149:18081/springmvc/seek/search/500110,/%E7%8E%8B/26/33
    }

    private void setSearchPersonOverlay(final SearchPerson.DataBean data) {
        searchPersonGraphics.clear();
        BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(context, R.mipmap.search_person);
        final PictureMarkerSymbol symbol = new PictureMarkerSymbol(drawable);
        symbol.setWidth(80);
        symbol.setHeight(100);
        symbol.loadAsync();
        symbol.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                Point point = new Point(data.getLon(), data.getLat(), SpatialReferences.getWgs84());
                Graphic graphic = new Graphic(point, symbol);
                searchPersonGraphics.add(graphic);
                Camera camera = new Camera(data.getLat(), data.getLon(), 10000, 0, 0, 0.0);
                sceneView.setViewpointCameraAsync(camera, 2);
                searchDialog.dismiss();
            }
        });
    }

    private void setSearchPlaceOverlay(final SearchPlace.DataBean data) {
        searchPlaceGraphics.clear();
        BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(context, R.mipmap.search_point);
        final PictureMarkerSymbol symbol = new PictureMarkerSymbol(drawable);
        symbol.setWidth(80);
        symbol.setHeight(100);
        symbol.loadAsync();
        symbol.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                Point point = new Point(data.getLon(), data.getLat(), SpatialReferences.getWgs84());
                Graphic graphic = new Graphic(point, symbol);
                searchPlaceGraphics.add(graphic);
                Camera camera = new Camera(data.getLat(), data.getLon(), 10000, 0, 0, 0.0);
                sceneView.setViewpointCameraAsync(camera, 2);
                searchDialog.dismiss();
            }
        });
    }

    private void addWeather() {
        final List<DisasterPoint> disasterPoints = new ArrayList<>();
        DisasterPoint wandong = new DisasterPoint();
        wandong.setDis_lon("106.91979545");
        wandong.setDis_lat("28.95290346");
        wandong.setDis_name("万东镇");
        DisasterPoint shilin = new DisasterPoint();
        shilin.setDis_lon("106.93410938");
        shilin.setDis_lat("28.84384145");
        shilin.setDis_name("石林镇");
        DisasterPoint qingnian = new DisasterPoint();
        qingnian.setDis_lon("106.850094");
        qingnian.setDis_lat("28.85293135");
        qingnian.setDis_name("青年镇");
        DisasterPoint nantong = new DisasterPoint();
        nantong.setDis_lon("106.86845176");
        nantong.setDis_lat("28.93441762");
        nantong.setDis_name("南桐镇");
        DisasterPoint jinqiao = new DisasterPoint();
        jinqiao.setDis_lon("106.89649099");
        jinqiao.setDis_lat("29.03657006");
        jinqiao.setDis_name("金桥镇");
        DisasterPoint heishan = new DisasterPoint();
        heishan.setDis_lon("106.99274618");
        heishan.setDis_lat("28.91461293");
        heishan.setDis_name("黑山镇");
        DisasterPoint guanba = new DisasterPoint();
        guanba.setDis_lon("106.82294387");
        guanba.setDis_lat("28.80028586");
        guanba.setDis_name("关坝镇");
        DisasterPoint conglin = new DisasterPoint();
        conglin.setDis_lon("106.96608428");
        conglin.setDis_lat("29.01085608");
        conglin.setDis_name("丛林镇");
        disasterPoints.add(wandong);
        disasterPoints.add(shilin);
        disasterPoints.add(qingnian);
        disasterPoints.add(nantong);
        disasterPoints.add(jinqiao);
        disasterPoints.add(heishan);
        disasterPoints.add(guanba);
        disasterPoints.add(conglin);
        BitmapDrawable sun = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.sun);
        BitmapDrawable shade = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.shade);
        BitmapDrawable rain = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.rain);
        List<BitmapDrawable> drawables = new ArrayList<>();
        drawables.add(sun);
        drawables.add(shade);
        drawables.add(rain);
        drawables.add(sun);
        drawables.add(shade);
        drawables.add(rain);
        drawables.add(sun);
        drawables.add(shade);
        for (int i = 0; i < 8; i++) {
            final PictureMarkerSymbol symbol = new PictureMarkerSymbol(drawables.get(i));
            symbol.setWidth(50);
            symbol.setHeight(50);
            symbol.setOffsetY(11);
            symbol.loadAsync();
            final int finalI = i;
            symbol.addDoneLoadingListener(new Runnable() {
                @Override
                public void run() {
                    Point point = new Point(Double.valueOf(disasterPoints.get(finalI).getDis_lon()), Double.valueOf(disasterPoints.get(finalI).getDis_lat()), SpatialReferences.getWgs84());
                    Graphic graphic = new Graphic(point, symbol);
                    weatherGraphics.add(graphic);
                }

            });
        }
    }

    private void resetPosition() {
        if (layers.contains(shiLinLayers.get(0))) {
            Camera camera = new Camera(28.87312428984992, 106.91015726332898, 2000, 0, 0, 0.0);
            sceneView.setViewpointCameraAsync(camera, 2);
        } else if (layers.contains(jinQiaoLayers.get(0))) {
            Camera camera = new Camera(29.07337764118905, 106.8774290607224, 2000, 0, 0, 0.0);
            sceneView.setViewpointCameraAsync(camera, 2);
        } else {
            Camera camera = new Camera(28.769167, 106.910399, 50000.0, 0, 20, 0.0);
            sceneView.setViewpointCameraAsync(camera, 2);
        }
    }

    /**
     * 四重人员 type 1:群测群防 2：驻守 3:负责人 4：地环站
     */
    private void initPersonData(final String type, String http) {
        waitingDialog = WaitingDialog.createLoadingDialog(this, "正在请求中...");
        OkHttpUtils.get().url(getResources().getString(R.string.base_http) + http + areaCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                        WaitingDialog.closeDialog(waitingDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        WaitingDialog.closeDialog(waitingDialog);
                        Log.d("limeng", "response:" + response);
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("data");
                            qcPersons.clear();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                PersonLocation personLocation = new PersonLocation();
                                switch (type) {
                                    case "1":
                                        personLocation.setId(o.getInt("dis_id"));
                                        personLocation.setLat(o.getString("dis_lat"));
                                        personLocation.setLon(o.getString("dis_lon"));
                                        personLocation.setType("1");
                                        break;
                                    case "2":
                                        personLocation.setId(o.getInt("id"));
                                        personLocation.setLat(o.getString("lat"));
                                        personLocation.setLon(o.getString("lng"));
                                        personLocation.setType("2");
                                        break;
                                    case "3":
                                        personLocation.setId(o.getInt("id"));
                                        personLocation.setLat(o.getString("lat"));
                                        personLocation.setLon(o.getString("lon"));
                                        personLocation.setType("3");
                                        break;
                                    case "4":
                                        personLocation.setId(o.getInt("id"));
                                        personLocation.setLat(o.getString("lat"));
                                        personLocation.setLon(o.getString("lng"));
                                        personLocation.setType("4");
                                        break;
                                }
                                qcPersons.add(personLocation);
                            }
                            Log.d("limeng", "qcPersons.size();" + qcPersons.size());
                            setPersonGraphic();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void setPersonGraphic() {
        qcGraphics.clear();
        mPersonTypes.clear();
        final PictureMarkerSymbol symbol1 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.person_1));
        final PictureMarkerSymbol symbol2 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.person_2));
        final PictureMarkerSymbol symbol3 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.person_3));
        final PictureMarkerSymbol symbol4 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.person_4));
        symbol1.setWidth(40);
        symbol1.setHeight(40);
        symbol1.setOffsetY(11);
        symbol2.setWidth(40);
        symbol2.setHeight(40);
        symbol2.setOffsetY(11);
        symbol3.setWidth(40);
        symbol3.setHeight(40);
        symbol3.setOffsetY(11);
        symbol4.setWidth(40);
        symbol4.setHeight(40);
        symbol4.setOffsetY(11);
        symbol1.loadAsync();
        symbol1.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < qcPersons.size(); i++) {
                    PersonLocation disasterPoint = qcPersons.get(i);
                    Point point = new Point(Double.valueOf(disasterPoint.getLon()), Double.valueOf(disasterPoint.getLat()), SpatialReferences.getWgs84());
                    Graphic graphic1;
                    Graphic graphic2;
                    Graphic graphic3;
                    Graphic graphic4;
                    switch (disasterPoint.getType()) {
                        case "1":
                            graphic1 = new Graphic(point, symbol1);
                            graphic1.setZIndex(i);
                            mPersonTypes.add(new PersonType(disasterPoint.getId(), disasterPoint.getType()));
                            qcGraphics.add(graphic1);
                            break;
                        case "2":
                            graphic2 = new Graphic(point, symbol2);
                            graphic2.setZIndex(i);
                            mPersonTypes.add(new PersonType(disasterPoint.getId(), disasterPoint.getType()));
                            qcGraphics.add(graphic2);
                            break;
                        case "3":
                            graphic3 = new Graphic(point, symbol3);
                            graphic3.setZIndex(i);
                            mPersonTypes.add(new PersonType(disasterPoint.getId(), disasterPoint.getType()));
                            qcGraphics.add(graphic3);
                            break;
                        case "4":
                            graphic4 = new Graphic(point, symbol4);
                            graphic4.setZIndex(i);
                            mPersonTypes.add(new PersonType(disasterPoint.getId(), disasterPoint.getType()));
                            qcGraphics.add(graphic4);
                            break;
                    }
                }
                updatePersonGraphic(qcGraphics);

            }

        });
    }

    private void setDisasterLegend(@LayoutRes int resource, int type) {
        LayoutInflater inflater = getLayoutInflater();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.rightMargin = 100;
        lp.bottomMargin = 30;
        if (view == null) {
            view = inflater.inflate(resource, null);
            view.setLayoutParams(lp);
            rlMain.addView(view);
        } else {
            rlMain.removeView(view);
            view = inflater.inflate(resource, null);
            view.setLayoutParams(lp);
            rlMain.addView(view);

        }
        if (type == 2) {
            LinearLayout linearLayout1 = (LinearLayout) view.findViewById(R.id.ll_legend_1);
            LinearLayout linearLayout2 = (LinearLayout) view.findViewById(R.id.ll_legend_2);
            LinearLayout linearLayout3 = (LinearLayout) view.findViewById(R.id.ll_legend_3);
            LinearLayout linearLayout4 = (LinearLayout) view.findViewById(R.id.ll_legend_4);
            LinearLayout linearLayout5 = (LinearLayout) view.findViewById(R.id.ll_legend_5);
            LinearLayout linearLayout6 = (LinearLayout) view.findViewById(R.id.ll_legend_6);
            LinearLayout linearLayout7 = (LinearLayout) view.findViewById(R.id.ll_legend_7);
            linearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rbDisasterPoint.isChecked()) {
                        updateGraphic(allHuaPOGraphics);
                    } else {
                        updateGraphic(otherHuaPOGraphics);
                    }
                }
            });
            linearLayout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rbDisasterPoint.isChecked()) {
                        updateGraphic(allNiSHILiuGraphics);
                    } else {
                        updateGraphic(otherNiSHILiuGraphics);
                    }
                }
            });
            linearLayout3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rbDisasterPoint.isChecked()) {
                        updateGraphic(allWeiYanGraphics);
                    } else {
                        updateGraphic(otherWeiYanGraphics);
                    }
                }
            });
            linearLayout4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rbDisasterPoint.isChecked()) {
                        updateGraphic(allXiePoGraphics);
                    } else {
                        updateGraphic(otherXiePoGraphics);
                    }
                }
            });
            linearLayout5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rbDisasterPoint.isChecked()) {
                        updateGraphic(allTanTaGraphics);
                    } else {
                        updateGraphic(otherTanTaGraphics);
                    }
                }
            });
            linearLayout6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rbDisasterPoint.isChecked()) {
                        updateGraphic(allLieFengGraphics);
                    } else {
                        updateGraphic(otherLieFengGraphics);
                    }
                }
            });
            linearLayout7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rbDisasterPoint.isChecked()) {
                        updateGraphic(allTaAnGraphics);
                    } else {
                        updateGraphic(otherTaAnGraphics);
                    }
                }
            });

        }
    }


    private void clearAllGraphics() {
        disasterGraphics.clear();
        personGraphics.clear();
        localGraphics.clear();
        equipmentGraphics.clear();
        weathersGraphics.clear();
        allGraphics.clear();
        mTabDisasterInfo = null;
        qcPersons.clear();
    }

    /**
     * @param compoundButton
     * @param b
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int id = compoundButton.getId();
        switch (id) {
            case R.id.rb_ssyl:
                Log.d(TAG, "实时雨量打开了:" + b);
                if (b) {
                    layers.add(ssYLLayer);
                } else {
                    layers.remove(ssYLLayer);
                }
                break;
            case R.id.rb_yldzx:
                Log.d(TAG, "雨量等值线打开了:" + b);
                if (b) {
                    layers.add(dengZXLayer);
                } else {
                    layers.remove(dengZXLayer);
                }
                break;
            case R.id.rb_disaster_point:
                if (b) {
                    mDisasterType = "-1";
                    Log.d("limeng", "mDisasterType=" + mDisasterType);
                    initDisasterData(areaCode);
                } else {
                    clearAllGraphics();
                }
                break;
            case R.id.rb_state_point_0://已销号
                if (b) {
                    mDisasterType = "0";
                    Log.d("limeng", "mDisasterType=" + mDisasterType);
                    initDisasterDataByState(areaCode, 0 + "");
                } else {
                    clearAllGraphics();
                }
                break;
            case R.id.rb_state_point_2://已治理灾害点
                if (b) {
                    mDisasterType = "2";
                    Log.d("limeng", "mDisasterType=" + mDisasterType);
                    initDisasterDataByState(areaCode, 2 + "");
                } else {
                    clearAllGraphics();
                }
                break;
            case R.id.rb_state_point_3://已搬迁灾害点
                if (b) {
                    mDisasterType = "3";
                    Log.d("limeng", "mDisasterType=" + mDisasterType);
                    initDisasterDataByState(areaCode, 3 + "");
                } else {
                    clearAllGraphics();
                }
                break;
            case R.id.rb_state_point_1://库岸调查
                if (b) {
                    mDisasterType = "1";
                    Log.d("limeng", "mDisasterType=" + mDisasterType);
                    initDisasterDataByState(areaCode, 1 + "");
                } else {
                    clearAllGraphics();
                }
                break;
            case R.id.rb_qcqf_person:
                if (b) {
                    personType = "1";
                    initPersonData("1", getResources().getString(R.string.qcqf_man));
                    setPieChartData("71", "在线率");
                } else {
                    clearAllGraphics();
                }
                break;
            case R.id.rb_zs_person:
                if (b) {
                    personType = "2";
                    initPersonData("2", getResources().getString(R.string.zs_man));
                    setPieChartData("64", "在线率");
                } else {
                    clearAllGraphics();
                }
                break;
            case R.id.rb_pq_person:
                if (b) {
                    personType = "3";
                    initPersonData("3", getResources().getString(R.string.fzr_man));
                    setPieChartData("90", "在线率");
                } else {
                    clearAllGraphics();
                }
                break;
            case R.id.rb_dhz_person:
                if (b) {
                    personType = "4";
                    initPersonData("4", getResources().getString(R.string.dh_man));
                    setPieChartData("95", "在线率");
                } else {
                    clearAllGraphics();
                }
                break;
            case R.id.rb_equipment_jiance:
                if (b) {
                    EquipmentOkhttp.getAllEquipment(context, "500232", handler);
                    setPieChartData("88", "在线率");
                } else {
                    clearAllGraphics();
                }
                break;
            case R.id.rb_jinqiao:
                if (b) {
                    layers.clear();
                    elevationSources.clear();
                    ElevationSource elevationSource = new ArcGISTiledElevationSource(getResources().getString(R.string.jinqiao_elevation_url));
                    layers.addAll(jinQiaoLayers);
                    elevationSources.add(elevationSource);
                    Camera camera = new Camera(29.07337764118905, 106.8774290607224, 2000, 0, 0, 0.0);
                    sceneView.setViewpointCamera(camera);
                } else {
                    layers.removeAll(jinQiaoLayers);
                    elevationSources.remove(elevationSource);
                }
                break;
            case R.id.rb_shilin:
                if (b) {
                    layers.clear();
                    elevationSources.clear();
                    ElevationSource elevationSource = new ArcGISTiledElevationSource(getResources().getString(R.string.shilin_elevation_url));
                    layers.addAll(shiLinLayers);
                    elevationSources.add(elevationSource);
                    Camera camera = new Camera(28.87312428984992, 106.91015726332898, 2000, 0, 0, 0.0);
                    sceneView.setViewpointCamera(camera);
                } else {
                    layers.removeAll(shiLinLayers);
                    elevationSources.remove(elevationSource);
                }
                break;
            case R.id.rb_qxyj:
                if (b) {
                    layers.add(xzFeatureLayer);
                    changeRender();
                } else {
                    layers.remove(xzFeatureLayer);
                }
                break;
            case R.id.rb_qxyb:
                if (b) {
                    updateWeather(weatherGraphics);
                } else {
                    weathersGraphics.clear();
                }
                break;
        }
    }


    /**
     * 气象预警改变图层
     */
    private void changeRender() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Message msg = Message.obtain();
                    msg.what = 1;
                    while (true) {
                        setOldRender();
                        Thread.sleep(2000);
                        xzFeatureLayer.resetRenderer();
                        setOldRender2();
                        Thread.sleep(2000);
                        xzFeatureLayer.resetRenderer();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    private void updateWeather(List<Graphic> g) {
        weathersGraphics.addAll(g);
    }

    private void setOldRender() {
        UniqueValueRenderer uniqueValueRenderer = new UniqueValueRenderer();
        uniqueValueRenderer.getFieldNames().add("name");

        SimpleFillSymbol defaultFillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.NULL, Color.BLACK, new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.GRAY, 2));
        SimpleFillSymbol symbol1 = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.RED, new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 1));

//        uniqueValueRenderer.setDefaultSymbol(defaultFillSymbol);
        uniqueValueRenderer.setDefaultLabel("Other");

        List<Object> wanDongValue = new ArrayList<>();
        wanDongValue.add("万东镇");
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("万东镇", "State of California", symbol1, wanDongValue));

//        List<Object> congLinValue = new ArrayList<>();
//        congLinValue.add("丛林镇");
//        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("丛林镇", "State of Arizona", symbol2, congLinValue));
//
//        List<Object> guanBaValue = new ArrayList<>();
//        guanBaValue.add("关坝镇");
//        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("关坝镇", "State of Nevada", symbol3, guanBaValue));
//
//        List<Object> nanTongValue = new ArrayList<>();
//        nanTongValue.add("南桐镇");
//        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("南桐镇", "State of Nevada", symbol3, nanTongValue));
//
//        List<Object> shiLinValue = new ArrayList<>();
//        shiLinValue.add("石林镇");
//        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("石林镇", "State of Nevada", symbol1, shiLinValue));
//
//        List<Object> jinQiaoValue = new ArrayList<>();
//        jinQiaoValue.add("金桥镇");
//        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("金桥镇", "State of Nevada", symbol1, jinQiaoValue));
//
        List<Object> qingNianVlue = new ArrayList<>();
        qingNianVlue.add("青年镇");
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("青年镇", "State of Nevada", symbol1, qingNianVlue));
//
//        List<Object> heiShanValue = new ArrayList<>();
//        heiShanValue.add("黑山镇");
//        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("黑山镇", "State of Nevada", symbol3, heiShanValue));

        xzFeatureLayer.setRenderer(uniqueValueRenderer);
    }

    /**
     * 变换颜色
     */
    private void setOldRender2() {
        UniqueValueRenderer uniqueValueRenderer = new UniqueValueRenderer();
        uniqueValueRenderer.getFieldNames().add("name");

        SimpleFillSymbol defaultFillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.NULL, Color.BLACK, new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.GRAY, 2));
        SimpleFillSymbol symbol3 = new SimpleFillSymbol(SimpleFillSymbol.Style.NULL, Color.parseColor("#00ffffff"), new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 1));

//        uniqueValueRenderer.setDefaultSymbol(defaultFillSymbol);
        uniqueValueRenderer.setDefaultLabel("Other");

        List<Object> wanDongValue = new ArrayList<>();
        wanDongValue.add("万东镇");
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("万东镇", "State of California", symbol3, wanDongValue));

//        List<Object> congLinValue = new ArrayList<>();
//        congLinValue.add("丛林镇");
//        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("丛林镇", "State of Arizona", symbol2, congLinValue));
//
//        List<Object> guanBaValue = new ArrayList<>();
//        guanBaValue.add("关坝镇");
//        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("关坝镇", "State of Nevada", symbol3, guanBaValue));
//
//        List<Object> nanTongValue = new ArrayList<>();
//        nanTongValue.add("南桐镇");
//        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("南桐镇", "State of Nevada", symbol3, nanTongValue));
//
//        List<Object> shiLinValue = new ArrayList<>();
//        shiLinValue.add("石林镇");
//        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("石林镇", "State of Nevada", symbol1, shiLinValue));
//
//        List<Object> jinQiaoValue = new ArrayList<>();
//        jinQiaoValue.add("金桥镇");
//        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("金桥镇", "State of Nevada", symbol1, jinQiaoValue));
//
        List<Object> qingNianVlue = new ArrayList<>();
        qingNianVlue.add("青年镇");
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("青年镇", "State of Nevada", symbol3, qingNianVlue));
//
//        List<Object> heiShanValue = new ArrayList<>();
//        heiShanValue.add("黑山镇");
//        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("黑山镇", "State of Nevada", symbol3, heiShanValue));

        xzFeatureLayer.setRenderer(uniqueValueRenderer);
    }


    /**
     * 添加设备
     */
    private void addEquipment() {
        final PictureMarkerSymbol symbol1 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip1));
        final PictureMarkerSymbol symbol2 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip2));
        final PictureMarkerSymbol symbol3 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip3));
        final PictureMarkerSymbol symbol4 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip4));
        final PictureMarkerSymbol symbol5 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip5));
        final PictureMarkerSymbol symbol8 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip8));
        final PictureMarkerSymbol symbol9 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip9));
        final PictureMarkerSymbol symbol10 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip10));
        final PictureMarkerSymbol symbol11 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip11));
        final PictureMarkerSymbol symbol12 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip12));
        final PictureMarkerSymbol symbol14 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip14));
        final PictureMarkerSymbol symbol15 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip15));
        final PictureMarkerSymbol symbol16 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip16));
        final PictureMarkerSymbol symbol17 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip17));
        final PictureMarkerSymbol symbol18 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip18));
        final PictureMarkerSymbol symbol19 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip19));
        final PictureMarkerSymbol symbol20 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip20));
        final PictureMarkerSymbol symbol21 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip21));
        final PictureMarkerSymbol symbol22 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip22));
        final PictureMarkerSymbol symbol23 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip23));
        final PictureMarkerSymbol symbol24 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip24));
        final PictureMarkerSymbol symbol25 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip25));
        final PictureMarkerSymbol symbol26 = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.blueequip26));
        symbol1.setWidth(45);
        symbol1.setHeight(60);
        symbol1.setOffsetY(11);
        symbol2.setWidth(45);
        symbol2.setHeight(60);
        symbol2.setOffsetY(11);
        symbol3.setWidth(45);
        symbol3.setHeight(60);
        symbol3.setOffsetY(11);
        symbol4.setWidth(45);
        symbol4.setHeight(60);
        symbol4.setOffsetY(11);
        symbol8.setWidth(45);
        symbol8.setHeight(60);
        symbol8.setOffsetY(11);
        symbol9.setWidth(45);
        symbol9.setHeight(60);
        symbol9.setOffsetY(11);
        symbol10.setWidth(45);
        symbol10.setHeight(60);
        symbol10.setOffsetY(11);
        symbol11.setWidth(45);
        symbol11.setHeight(60);
        symbol11.setOffsetY(11);
        symbol12.setWidth(45);
        symbol12.setHeight(60);
        symbol12.setOffsetY(11);
        symbol14.setWidth(45);
        symbol14.setHeight(60);
        symbol14.setOffsetY(11);
        symbol15.setWidth(45);
        symbol15.setHeight(60);
        symbol15.setOffsetY(11);
        symbol16.setWidth(45);
        symbol16.setHeight(60);
        symbol16.setOffsetY(11);
        symbol17.setWidth(45);
        symbol17.setHeight(60);
        symbol17.setOffsetY(11);
        symbol18.setWidth(45);
        symbol18.setHeight(60);
        symbol18.setOffsetY(11);
        symbol19.setWidth(45);
        symbol19.setHeight(60);
        symbol19.setOffsetY(11);
        symbol20.setWidth(45);
        symbol20.setHeight(60);
        symbol20.setOffsetY(11);
        symbol21.setWidth(45);
        symbol21.setHeight(60);
        symbol21.setOffsetY(11);
        symbol22.setWidth(45);
        symbol22.setHeight(60);
        symbol22.setOffsetY(11);
        symbol23.setWidth(45);
        symbol23.setHeight(60);
        symbol23.setOffsetY(11);
        symbol24.setWidth(45);
        symbol24.setHeight(60);
        symbol24.setOffsetY(11);
        symbol25.setWidth(45);
        symbol25.setHeight(60);
        symbol25.setOffsetY(11);
        symbol26.setWidth(45);
        symbol26.setHeight(60);
        symbol26.setOffsetY(11);
        symbol1.loadAsync();
        symbol1.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                for (EquipmentLocation.DataBean data : equipmentLocation.getData()) {
                    if (1 == data.getType()) {
                        Log.d("limeng", "data.getResult().size()=" + data.getResult().size());
                        Random random = new Random();
                        for (EquipmentLocation.DataBean.ResultBean result : data.getResult()) {
                            if (0 != result.getLatitude()) {
                                Point point = new Point(result.getLongitude() + random.nextInt(1000) * 0.0001, result.getLatitude() + random.nextInt(1000) * 0.0001, SpatialReferences.getWgs84());
                                Graphic graphic = null;
                                switch (result.getMonitor_type()) {
                                    case 1:
                                        graphic = new Graphic(point, symbol1);
                                        break;
                                    case 2:
                                        graphic = new Graphic(point, symbol2);
                                        break;
                                    case 3:
                                        graphic = new Graphic(point, symbol3);
                                        break;
                                    case 4:
                                        graphic = new Graphic(point, symbol4);
                                        break;
                                    case 5:
                                        graphic = new Graphic(point, symbol5);
                                        break;
                                    case 8:
                                        graphic = new Graphic(point, symbol8);
                                        break;
                                    case 9:
                                        graphic = new Graphic(point, symbol9);
                                        break;
                                    case 10:
                                        graphic = new Graphic(point, symbol10);
                                        break;
                                    case 11:
                                        graphic = new Graphic(point, symbol11);
                                        break;
                                    case 12:
                                        graphic = new Graphic(point, symbol12);
                                        break;
                                    case 14:
                                        graphic = new Graphic(point, symbol14);
                                        break;
                                    case 15:
                                        graphic = new Graphic(point, symbol15);
                                        break;
                                    case 16:
                                        graphic = new Graphic(point, symbol16);
                                        break;
                                    case 17:
                                        graphic = new Graphic(point, symbol17);
                                        break;
                                    case 18:
                                        graphic = new Graphic(point, symbol18);
                                        break;
                                    case 19:
                                        graphic = new Graphic(point, symbol19);
                                        break;
                                    case 20:
                                        graphic = new Graphic(point, symbol20);
                                        break;
                                    case 21:
                                        graphic = new Graphic(point, symbol21);
                                        break;
                                    case 22:
                                        graphic = new Graphic(point, symbol22);
                                        break;
                                    case 23:
                                        graphic = new Graphic(point, symbol23);
                                        break;
                                    case 24:
                                        graphic = new Graphic(point, symbol24);
                                        break;
                                    case 25:
                                        graphic = new Graphic(point, symbol25);
                                        break;
                                    case 26:
                                        graphic = new Graphic(point, symbol26);
                                        break;
                                }
                                graphic.setZIndex(result.getId());
                                jianceGraphics.add(graphic);
                            }
                        }
                    }

                }

                updateEquipmentGraphic(jianceGraphics);
            }

        });
    }

    private void updateLocalGraphic(List<Graphic> g) {
        localGraphics.addAll(g);
    }

    /**
     * 更新人员
     *
     * @param q
     */
    private void updatePersonGraphic(List<Graphic> q) {
        personGraphics.addAll(q);
    }

    private void updateEquipmentGraphic(List<Graphic> graphics) {
        equipmentGraphics.addAll(graphics);
    }

    /**
     * 更新地图上的图标
     *
     * @param g 图标集合
     */
    private void updateGraphic(List<Graphic> g) {
        disasterGraphics.clear();
        Log.d("limeng", "allGraphics=" + allGraphics.size());
        disasterGraphics.addAll(g);
    }

    private void initDisasterData(String areaCode) {
        waitingDialog = WaitingDialog.createLoadingDialog(this, "正在请求中...");
        OkHttpUtils.get().url(getResources().getString(R.string.base_http) + getResources().getString(R.string.disaster_url) + areaCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                        WaitingDialog.closeDialog(waitingDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        WaitingDialog.closeDialog(waitingDialog);
                        Log.d("limeng", "onResponse" + response);
                        //{"meta":{"success":true,"message":"ok"},"data":"用户无访问权限"}
                        //String str="{\"meta\":{\"success\":true,\"message\":\"ok\"},\"data\":[]}";
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if ("用户无访问权限".equals(jsonObject.getString("data"))) {
                                Toast.makeText(context, "用户无访问权限", Toast.LENGTH_SHORT).show();
                            } else {
                                Gson gson = new Gson();
                                Type type = new TypeToken<TabDisasterInfo>() {
                                }.getType();
                                mTabDisasterInfo = gson.fromJson(response, type);
                                Log.d("limeng", "mTabDisasterInfo" + mTabDisasterInfo.getData().get(0).getDisName());
                                setOverlay();
                            }

                        } catch (JSONException e) {
                            Log.d("limeng", "e:" + e.toString());
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**
     * 灾害分类
     *
     * @param areaCode
     * @param mState
     */
    private void initDisasterDataByState(String areaCode, final String mState) {
        waitingDialog = WaitingDialog.createLoadingDialog(this, "正在请求中...");
        OkHttpUtils.get().url(getResources().getString(R.string.base_http) + getResources().getString(R.string.disaster_url_state) + mState + "/" + areaCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                        WaitingDialog.closeDialog(waitingDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        WaitingDialog.closeDialog(waitingDialog);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if ("用户无访问权限".equals(jsonObject.getString("data"))) {
                                Toast.makeText(context, "用户无访问权限", Toast.LENGTH_SHORT).show();
                            } else {
                                Gson gson = new Gson();
                                Type type = new TypeToken<DisasterByStateInfo>() {
                                }.getType();
                                mDisasterByStateInfo = gson.fromJson(response, type);
                                switch (mState) {
                                    case "0":
                                        mDisasterByStateInfo0 = gson.fromJson(response, type);
                                        setOverlayState(mDisasterByStateInfo0);
                                        break;
                                    case "2":
                                        mDisasterByStateInfo2 = gson.fromJson(response, type);
                                        setOverlayState(mDisasterByStateInfo2);
                                        break;
                                    case "3":
                                        mDisasterByStateInfo3 = gson.fromJson(response, type);
                                        setOverlayState(mDisasterByStateInfo3);
                                        break;
                                    case "1":
                                        mDisasterByStateInfo1 = gson.fromJson(response, type);
                                        setOverlayState(mDisasterByStateInfo1);
                                        break;
                                }
                            }

                        } catch (JSONException e) {
                            Log.d("limeng", "e:" + e.toString());
                            e.printStackTrace();
                        }
//
                    }
                });
    }

    /**
     * 灾害点图标
     */
    private void setOverlay() {
        maps.clear();
        BitmapDrawable huapo = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.point_1);
        BitmapDrawable nishiliu = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.point_2);
        BitmapDrawable weiyan = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.point_3);
        BitmapDrawable xiepo = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.point_4);
        BitmapDrawable tanta = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.point_5);
        BitmapDrawable liefeng = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.point_6);
        BitmapDrawable taan = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.point_7);
        List<BitmapDrawable> drawables = new ArrayList<>();
        drawables.add(huapo);
        drawables.add(nishiliu);
        drawables.add(weiyan);
        drawables.add(xiepo);
        drawables.add(tanta);
        drawables.add(liefeng);
        drawables.add(taan);
        for (int i = 1; i <= 7; i++) {
            final PictureMarkerSymbol pinStarBlueSymbol = new PictureMarkerSymbol(drawables.get(i - 1));
            //Optionally set the size, if not set the image will be auto sized based on its size in pixels,
            //its appearance would then differ across devices with different resolutions.
            pinStarBlueSymbol.setHeight(55);
            pinStarBlueSymbol.setWidth(55);
            //Optionally set the offset, to align the base of the symbol aligns with the point geometry
            pinStarBlueSymbol.setOffsetY(
                    8); //The image used for the symbol has a transparent buffer around it, so the offset is not simply height/2
            pinStarBlueSymbol.loadAsync();
            //[DocRef: END]
            final int finalI = i;
            pinStarBlueSymbol.addDoneLoadingListener(new Runnable() {
                @Override
                public void run() {
                    for (TabDisasterInfo.DataBean mDataBean : mTabDisasterInfo.getData()) {
                        if (mDataBean.getDisType() == finalI) {
                            Point point = new Point(mDataBean.getDisLon(), mDataBean.getDisLat(), SpatialReferences.getWgs84());
                            Graphic graphic = new Graphic(point, pinStarBlueSymbol);
                            graphic.setZIndex(mDataBean.getId());
                            maps.put(mDataBean.getId() + "", mDataBean.getDisNo());
                            allGraphics.add(graphic);
                            switch (finalI) {
                                case 1:
                                    allHuaPOGraphics.add(graphic);
                                    break;
                                case 2:
                                    allNiSHILiuGraphics.add(graphic);
                                    break;
                                case 3:
                                    allWeiYanGraphics.add(graphic);
                                    break;
                                case 4:
                                    allXiePoGraphics.add(graphic);
                                    break;
                                case 5:
                                    allTanTaGraphics.add(graphic);
                                    break;
                                case 6:
                                    allLieFengGraphics.add(graphic);
                                    break;
                                case 7:
                                    allTaAnGraphics.add(graphic);
                                    break;
                            }
                        }
                    }
                    updateGraphic(allGraphics);
                }
            });

        }

    }

    /**
     * 灾害点分类图标
     */
    private void setOverlayState(final DisasterByStateInfo disasterByStateInfo) {
        maps.clear();
        otherGraphics.clear();
        otherHuaPOGraphics.clear();
        otherLieFengGraphics.clear();
        otherNiSHILiuGraphics.clear();
        otherTaAnGraphics.clear();
        otherTanTaGraphics.clear();
        otherWeiYanGraphics.clear();
        otherXiePoGraphics.clear();
        BitmapDrawable huapo = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.point_1);
        BitmapDrawable nishiliu = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.point_2);
        BitmapDrawable weiyan = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.point_3);
        BitmapDrawable xiepo = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.point_4);
        BitmapDrawable tanta = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.point_5);
        BitmapDrawable liefeng = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.point_6);
        BitmapDrawable taan = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.point_7);
        List<BitmapDrawable> drawables = new ArrayList<>();
        drawables.add(huapo);
        drawables.add(nishiliu);
        drawables.add(weiyan);
        drawables.add(xiepo);
        drawables.add(tanta);
        drawables.add(liefeng);
        drawables.add(taan);
        for (int i = 1; i <= 7; i++) {
            final PictureMarkerSymbol pinStarBlueSymbol = new PictureMarkerSymbol(drawables.get(i - 1));
            //Optionally set the size, if not set the image will be auto sized based on its size in pixels,
            //its appearance would then differ across devices with different resolutions.
            pinStarBlueSymbol.setHeight(40);
            pinStarBlueSymbol.setWidth(40);
            //Optionally set the offset, to align the base of the symbol aligns with the point geometry
            pinStarBlueSymbol.setOffsetY(
                    11); //The image used for the symbol has a transparent buffer around it, so the offset is not simply height/2
            pinStarBlueSymbol.loadAsync();
            //[DocRef: END]
            final int finalI = i;
            pinStarBlueSymbol.addDoneLoadingListener(new Runnable() {
                @Override
                public void run() {
                    for (DisasterByStateInfo.DataBean mDataBean : disasterByStateInfo.getData()) {
                        if (mDataBean.getDis_type() == finalI) {
                            Point point = new Point(mDataBean.getDis_lon(), mDataBean.getDis_lat(), SpatialReferences.getWgs84());
                            Graphic graphic = new Graphic(point, pinStarBlueSymbol);
                            graphic.setZIndex(mDataBean.getId());
                            maps.put(mDataBean.getId() + "", mDisNo = mDataBean.getDis_no());
                            otherGraphics.add(graphic);
                            switch (finalI) {
                                case 1:
                                    otherHuaPOGraphics.add(graphic);
                                    break;
                                case 2:
                                    otherNiSHILiuGraphics.add(graphic);
                                    break;
                                case 3:
                                    otherWeiYanGraphics.add(graphic);
                                    break;
                                case 4:
                                    otherXiePoGraphics.add(graphic);
                                    break;
                                case 5:
                                    otherTanTaGraphics.add(graphic);
                                    break;
                                case 6:
                                    otherLieFengGraphics.add(graphic);
                                    break;
                                case 7:
                                    otherTaAnGraphics.add(graphic);
                                    break;
                            }
                        }
                    }
                    updateGraphic(otherGraphics);
                }

            });
        }
    }

    private void setRainfallMore() {
        switch (llMoreState) {
            case 1:
                rgRainfall.setVisibility(View.VISIBLE);
                rgDangerpoint.setVisibility(View.GONE);
                rgStaff.setVisibility(View.GONE);
                rgEquipment.setVisibility(View.GONE);
                rgQxsy.setVisibility(View.GONE);
                rgXingzheng.setVisibility(View.GONE);
                break;
            case 2:
                rgRainfall.setVisibility(View.GONE);
                rgDangerpoint.setVisibility(View.VISIBLE);
                rgStaff.setVisibility(View.GONE);
                rgEquipment.setVisibility(View.GONE);
                rgQxsy.setVisibility(View.GONE);
                rgXingzheng.setVisibility(View.GONE);
                break;
            case 3:
                rgRainfall.setVisibility(View.GONE);
                rgDangerpoint.setVisibility(View.GONE);
                rgStaff.setVisibility(View.VISIBLE);
                rgEquipment.setVisibility(View.GONE);
                rgQxsy.setVisibility(View.GONE);
                rgXingzheng.setVisibility(View.GONE);
                break;
            case 4:
                rgRainfall.setVisibility(View.GONE);
                rgDangerpoint.setVisibility(View.GONE);
                rgStaff.setVisibility(View.GONE);
                rgEquipment.setVisibility(View.VISIBLE);
                rgQxsy.setVisibility(View.GONE);
                rgXingzheng.setVisibility(View.GONE);
                break;
            case 5:
                rgRainfall.setVisibility(View.GONE);
                rgDangerpoint.setVisibility(View.GONE);
                rgStaff.setVisibility(View.GONE);
                rgEquipment.setVisibility(View.GONE);
                rgQxsy.setVisibility(View.VISIBLE);
                rgXingzheng.setVisibility(View.GONE);
                break;
            case 6:
                rgRainfall.setVisibility(View.GONE);
                rgDangerpoint.setVisibility(View.GONE);
                rgStaff.setVisibility(View.GONE);
                rgEquipment.setVisibility(View.GONE);
                rgQxsy.setVisibility(View.GONE);
                rgXingzheng.setVisibility(View.VISIBLE);
                break;
        }


        if (llMoreState != llMoreStateBefore) {
            rgRainfall.clearCheck();
            rgDangerpoint.clearCheck();
            rgStaff.clearCheck();
            rgEquipment.clearCheck();
            rgQxsy.clearCheck();
            rgXingzheng.clearCheck();
            pieChart.setVisibility(View.GONE);
            Log.d("limeng", "llMoreState:" + llMoreState + "\n" + "llMoreStateBefore:" + llMoreStateBefore);
            ObjectAnimator animator3 = null;
            ObjectAnimator animator4 = null;
            switch (llMoreState) {
                case 1:
                    animator3 = ObjectAnimator.ofFloat(ivRainfallMore, "rotation", 0, 90);
                    break;
                case 2:
                    animator3 = ObjectAnimator.ofFloat(ivDangerpointMore, "rotation", 0, 90);
                    break;
                case 3:
                    animator3 = ObjectAnimator.ofFloat(ivStaffMore, "rotation", 0, 90);
                    break;
                case 4:
                    animator3 = ObjectAnimator.ofFloat(ivEquipmentMore, "rotation", 0, 90);
                    break;
                case 5:
                    animator3 = ObjectAnimator.ofFloat(ivQxsy, "rotation", 0, 90);
                    break;
                case 6:
                    animator3 = ObjectAnimator.ofFloat(ivXzMore, "rotation", 0, 90);
            }
            if (animator3 != null) {
                animator3.setDuration(100);
                animator3.start();
            }
            switch (llMoreStateBefore) {
                case 1:
                    animator4 = ObjectAnimator.ofFloat(ivRainfallMore, "rotation", 90, 0);
                    break;
                case 2:
                    animator4 = ObjectAnimator.ofFloat(ivDangerpointMore, "rotation", 90, 0);
                    break;
                case 3:
                    animator4 = ObjectAnimator.ofFloat(ivStaffMore, "rotation", 90, 0);
                    break;
                case 4:
                    animator4 = ObjectAnimator.ofFloat(ivEquipmentMore, "rotation", 90, 0);
                    break;
                case 5:
                    animator4 = ObjectAnimator.ofFloat(ivQxsy, "rotation", 90, 0);
                    break;
                case 6:
                    animator4 = ObjectAnimator.ofFloat(ivXzMore, "rotation", 90, 0);
                    break;
            }
            if (animator4 != null) {
                animator4.setDuration(100);
                animator4.start();
            }
        }

    }

    private void setAreaBack() {
        if (llAreaState == false) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(llArea, "x", 0, -(llArea.getWidth() - 70));
            animator.setDuration(1000);
            animator.start();
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(ivAreaBack, "rotation", 0, 180);
            animator1.setDuration(100);
            animator1.start();
            llAreaState = true;
        } else {
            ObjectAnimator animator = ObjectAnimator.ofFloat(llArea, "x", -(llArea.getWidth() - 70), 0);
            animator.setDuration(1000);
            animator.start();
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(ivAreaBack, "rotation", 180, 0);
            animator1.setDuration(100);
            animator1.start();
            llAreaState = false;
        }
    }

    private void setDataBack() {
        if (llDataState == false) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(llData, "x", 0, -(llData.getWidth() - 70));
            animator.setDuration(1000);
            animator.start();
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(ivDataBack, "rotation", 0, 180);
            animator1.setDuration(100);
            animator1.start();
            llDataState = true;
        } else {
            ObjectAnimator animator = ObjectAnimator.ofFloat(llData, "x", -(llData.getWidth() - 70), 0);
            animator.setDuration(1000);
            animator.start();
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(ivDataBack, "rotation", 180, 0);
            animator1.setDuration(100);
            animator1.start();
            llDataState = false;
        }
    }

    public void setDrawingMode(DrawingMode drawingMode) {
        // If we try to start a new drawing before finishing our last, finish the current one
        if (mDrawingMode != DrawingMode.NONE) {
            finishDrawing();
        }
        mDrawingMode = drawingMode;
        // If the drawing mode is polyline or polygon, set the current point collection to an empty collection
        if (mDrawingMode == DrawingMode.POLYLINE || mDrawingMode == DrawingMode.POLYGON) {
            mCurrentPointCollection = new PointCollection(sceneView.getSpatialReference());
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
                    mCurrentPointCollection = new PointCollection(sceneView.getSpatialReference());
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
        clearAllGraphics();
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
        return new Point(midX, midY, sceneView.getSpatialReference());
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
        btnUtilDetail.setVisibility(View.GONE);
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
                    btnUtilDetail.setVisibility(View.VISIBLE);
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


}
