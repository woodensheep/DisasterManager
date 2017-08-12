package com.nandi.disastermanager;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.esri.arcgisruntime.geometry.Point;
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
import com.esri.arcgisruntime.mapping.view.SceneView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.UniqueValueRenderer;
import com.esri.arcgisruntime.util.ListenableList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nandi.disastermanager.adapter.RcPersonAdapter;
import com.nandi.disastermanager.adapter.RcPhotoAdapter;
import com.nandi.disastermanager.adapter.RcSearchPersonAdapter;
import com.nandi.disastermanager.adapter.RcSearchPlaceAdapter;
import com.nandi.disastermanager.entity.ChartsInfo;
import com.nandi.disastermanager.entity.DetailBaseInfo;
import com.nandi.disastermanager.entity.DetailDisCard;
import com.nandi.disastermanager.entity.DetailHeCard;
import com.nandi.disastermanager.entity.DetailPersonInfo;
import com.nandi.disastermanager.entity.DetailPhoto;
import com.nandi.disastermanager.entity.DetailPnInfo;
import com.nandi.disastermanager.entity.DisasterByStateInfo;
import com.nandi.disastermanager.entity.DisasterDetailInfo;
import com.nandi.disastermanager.entity.DisasterPoint;
import com.nandi.disastermanager.entity.PersonInfo;
import com.nandi.disastermanager.entity.PersonLocation;
import com.nandi.disastermanager.entity.SearchPerson;
import com.nandi.disastermanager.entity.SearchPlace;
import com.nandi.disastermanager.entity.TabDisasterInfo;
import com.nandi.disastermanager.ui.CircleBar;
import com.nandi.disastermanager.ui.MyRadioGroup;
import com.nandi.disastermanager.ui.WaitingDialog;
import com.nandi.disastermanager.utils.LocalJson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
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
    @BindView(R.id.tv_disaster_number)
    TextView tvDisasterNumber;
    @BindView(R.id.tv_person_number)
    TextView tvPersonNumber;
    @BindView(R.id.tv_equipment_number)
    TextView tvEquipmentNumber;
    @BindView(R.id.tv_leader_number)
    TextView tvLeaderNumber;
    @BindView(R.id.tv_charge_number)
    TextView tvChargeNumber;
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


    private boolean llAreaState = false;
    private boolean llDataState = false;
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
    List<ArcGISSceneLayer> jinQiaoLayers = new ArrayList<>();
    List<ArcGISSceneLayer> shiLinLayers = new ArrayList<>();
    private ArcGISScene scene;
    private ArcGISTiledElevationSource elevationSource;
    private LayerList layers;
    private Surface.ElevationSourceList elevationSources;
    private List<DisasterPoint> disasterPoints;
    private List<PersonLocation> qcPersons = new ArrayList<>();
    private List<PersonLocation> zsPersons = new ArrayList<>();
    private List<PersonLocation> pqPersons = new ArrayList<>();
    private List<PersonLocation> dhzPersons = new ArrayList<>();
    private GraphicsOverlay graphicsOverlay;
    private GraphicsOverlay personGraphicsOverlay;
    private GraphicsOverlay localGraphicsOverlay;
    private GraphicsOverlay equipmentGraphicOverlay;
    private GraphicsOverlay weatherGraphicOverlay;
    private ListenableList<GraphicsOverlay> graphicsOverlays;
    private List<Graphic> allGraphics = new ArrayList<>();//所有的灾害点图标
    private List<Graphic> otherGraphics = new ArrayList<>();//已消耗灾害点图标
    private List<Graphic> notConsumeGraphics = new ArrayList<>();//未消耗灾害点图标
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

    private List<Graphic> notHuaPOGraphics = new ArrayList<>();
    private List<Graphic> notNiSHILiuGraphics = new ArrayList<>();
    private List<Graphic> notWeiYanGraphics = new ArrayList<>();
    private List<Graphic> notXiePoGraphics = new ArrayList<>();
    private List<Graphic> notTanTaGraphics = new ArrayList<>();
    private List<Graphic> notLieFengGraphics = new ArrayList<>();
    private List<Graphic> notTaAnGraphics = new ArrayList<>();

    private List<Graphic> conHuaPOGraphics = new ArrayList<>();
    private List<Graphic> conNiSHILiuGraphics = new ArrayList<>();
    private List<Graphic> conWeiYanGraphics = new ArrayList<>();
    private List<Graphic> conXiePoGraphics = new ArrayList<>();
    private List<Graphic> conTanTaGraphics = new ArrayList<>();
    private List<Graphic> conLieFengGraphics = new ArrayList<>();
    private List<Graphic> conTaAnGraphics = new ArrayList<>();
    private List<Graphic> qcGraphics = new ArrayList<>();
    private List<Graphic> zsGraphics = new ArrayList<>();
    private List<Graphic> pqGraphics = new ArrayList<>();
    private List<Graphic> dhzGraphics = new ArrayList<>();
    private List<Graphic> jianceGraphics = new ArrayList<>();
    private List<Graphic> weatherGraphics = new ArrayList<>();
    private ListenableList<Graphic> graphics;
    private ListenableList<Graphic> personGraphics;
    private ListenableList<Graphic> localGraphics;
    private ListenableList<Graphic> equipmentGraphics;
    private ListenableList<Graphic> weathersGraphics;
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
    private LinearLayout llSwitchInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        areaCode = "500110";
        ButterKnife.bind(this);
        initStaData();
        initLocalData();
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
        weatherGraphicOverlay = new GraphicsOverlay();
        graphics = graphicsOverlay.getGraphics();
        personGraphics = personGraphicsOverlay.getGraphics();
        localGraphics = localGraphicsOverlay.getGraphics();
        equipmentGraphics = equipmentGraphicOverlay.getGraphics();
        weathersGraphics = weatherGraphicOverlay.getGraphics();
        graphicsOverlays = sceneView.getGraphicsOverlays();
        elevationSources = scene.getBaseSurface().getElevationSources();
        scene.setBasemap(Basemap.createImagery());
        sceneView.setScene(scene);
        setListeners();
        setlogin("", "");
    }


    private void setPieChartData(String text, String desText) {
        pieChart.setVisibility(View.VISIBLE);
        pieChart.setText(text);
        pieChart.setDesText(desText);
        pieChart.setSweepAngle(Integer.parseInt(text) * 180 / 100);
    }

    private void initLocalData() {
        pqPersons.add(new PersonLocation("28.95514", "106.926366", 10011));
        pqPersons.add(new PersonLocation("28.816001", "106.816087", 10021));
        pqPersons.add(new PersonLocation("28.943602", "106.882525", 10031));
        pqPersons.add(new PersonLocation("29.031026", "106.965438", 10041));
        pqPersons.add(new PersonLocation("28.869188", "106.908478", 10051));
        pqPersons.add(new PersonLocation("28.940619", "106.945398", 10061));
        pqPersons.add(new PersonLocation("28.861747", "106.842987", 10071));
        pqPersons.add(new PersonLocation("28.914455", "107.003855", 10081));
        pqPersons.add(new PersonLocation("29.070643", "106.880268", 10091));
        pqPersons.add(new PersonLocation("28.95102", "106.923961", 10101));

        dhzPersons.add(new PersonLocation("28.96346", "106.927437", 293));
        dhzPersons.add(new PersonLocation("28.96446", "106.928437", 303));
        dhzPersons.add(new PersonLocation("28.96546", "106.929437", 313));
        dhzPersons.add(new PersonLocation("28.96646", "106.927437", 333));

        zsPersons.add(new PersonLocation("28.961367", "106.932906", 10012));
        zsPersons.add(new PersonLocation("28.948905", "106.888573", 10022));
        zsPersons.add(new PersonLocation("29.07643", "106.886891", 10032));
        zsPersons.add(new PersonLocation("28.9435", "106.955571", 10042));

        BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.person);
        final PictureMarkerSymbol symbol = new PictureMarkerSymbol(drawable);
        symbol.setWidth(30);
        symbol.setHeight(30);
        symbol.setOffsetY(11);
        symbol.loadAsync();
        symbol.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                for (PersonLocation disasterPoint : pqPersons) {
                    Point point = new Point(Double.valueOf(disasterPoint.getLon()), Double.valueOf(disasterPoint.getLat()), SpatialReferences.getWgs84());
                    Graphic graphic = new Graphic(point, symbol);
                    graphic.setZIndex(disasterPoint.getId());
                    pqGraphics.add(graphic);
                }
                for (PersonLocation disasterPoint : zsPersons) {
                    Point point = new Point(Double.valueOf(disasterPoint.getLon()), Double.valueOf(disasterPoint.getLat()), SpatialReferences.getWgs84());
                    Graphic graphic = new Graphic(point, symbol);
                    graphic.setZIndex(disasterPoint.getId());
                    zsGraphics.add(graphic);
                }
                for (PersonLocation disasterPoint : dhzPersons) {
                    Point point = new Point(Double.valueOf(disasterPoint.getLon()), Double.valueOf(disasterPoint.getLat()), SpatialReferences.getWgs84());
                    Graphic graphic = new Graphic(point, symbol);
                    graphic.setZIndex(disasterPoint.getId());
                    dhzGraphics.add(graphic);
                }

            }

        });
    }


    private void initStaData() {
        waitingDialog = WaitingDialog.createLoadingDialog(this, "正在请求中...");
        OkHttpUtils.get().url(getResources().getString(R.string.statistics_url))
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
                            tvDisasterNumber.setText(oj.getString("disasterTotal"));
                            tvPersonNumber.setText(oj.getString("personnelTotal"));
                            tvChargeNumber.setText(oj.getString("LandTotal"));
                            tvEquipmentNumber.setText(oj.getString("equipmentTotal"));
                            tvLeaderNumber.setText(oj.getString("townshipTotal"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void setListeners() {
        ivSearchMain.setOnClickListener(this);
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
                // get the screen point where user tapped
                android.graphics.Point screenPoint = new android.graphics.Point((int) e.getX(), (int) e.getY());
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
                            //// TODO: 2017/8/10 显示乡镇统计信息
                            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_township_statistics, null);
                            TextView tvTownshipName = (TextView) view.findViewById(R.id.tv_township_name);
                            tvTownshipName.setText(name);
                            AlertDialog mDialogCharts = new AlertDialog.Builder(MainActivity.this)
                                    .setView(view)
                                    .show();
                        }
                    } catch (InterruptedException | ExecutionException e1) {
                        e1.printStackTrace();
                    }
                }
                // identify graphics on the graphics overlay
                final ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphic = sceneView.identifyGraphicsOverlayAsync(graphicsOverlay, screenPoint, 10.0, false, 2);
                final ListenableFuture<IdentifyGraphicsOverlayResult> personIdentifyGraphic = sceneView.identifyGraphicsOverlayAsync(personGraphicsOverlay, screenPoint, 10.0, false, 2);
                final ListenableFuture<IdentifyGraphicsOverlayResult> localIdentifyGraphic = sceneView.identifyGraphicsOverlayAsync(localGraphicsOverlay, screenPoint, 10.0, false, 2);
                final ListenableFuture<IdentifyGraphicsOverlayResult> equipmentIdentifyGraphic = sceneView.identifyGraphicsOverlayAsync(equipmentGraphicOverlay, screenPoint, 10.0, false, 2);
                identifyGraphic.addDoneListener(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            IdentifyGraphicsOverlayResult identifyGraphicsOverlayResult = identifyGraphic.get();
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
                                showPersonInfo(zIndex);
                            }
                        } catch (InterruptedException | ExecutionException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                localIdentifyGraphic.addDoneListener(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            IdentifyGraphicsOverlayResult identifyGraphicsOverlayResult = localIdentifyGraphic.get();
                            if (identifyGraphicsOverlayResult.getGraphics().size() > 0) {
                                int zIndex = identifyGraphicsOverlayResult.getGraphics().get(0).getZIndex();
                                showLocalPersonInfo(zIndex);
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
                                showEquipmentInfo(zIndex);
                            }
                        } catch (InterruptedException | ExecutionException e1) {
                            e1.printStackTrace();
                        }
                    }
                });

                return super.onSingleTapConfirmed(e);
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
    }


    /**
     * 测试设备信息
     *
     * @param zIndex
     */
    private void showEquipmentInfo(int zIndex) {
        //TODO 根据Id显示设备信息
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_shebi, null);
        final LinearLayout llSheBeiInfo1 = (LinearLayout) view.findViewById(R.id.ll_shebei_info_1);
        final LinearLayout llSheBeiInfo2 = (LinearLayout) view.findViewById(R.id.ll_shebei_info_2);
        final LinearLayout llSheBeiInfo3 = (LinearLayout) view.findViewById(R.id.ll_shebei_info_3);
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
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_charts, null);
                LineChartView mChartView = (LineChartView) view.findViewById(R.id.chart);
                ChartsInfo chartsInfo = LocalJson.parseJson(context, "gpsData.json");
                Log.d("limeng", "chartsInfo.getData().getX1().size()=" + chartsInfo.getData().getX1().size());
                Log.d("limeng", "chartsInfo.getData().getX1().get(0).size()=" + chartsInfo.getData().getX1().get(0).size());
                ArrayList<PointValue> values = new ArrayList<PointValue>();//折线上的点
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

                for (int i = 0; i < chartsInfo.getData().getX1().size(); i++) {
                    List<Float> x = chartsInfo.getData().getX1().get(i);
                    values.add(new PointValue(x.get(0), x.get(1) / 1000));
                }

                Line line = new Line(values).setColor(Color.GREEN);//声明线并设置颜色
                line.setCubic(false);//设置是平滑的还是直的
                line.setStrokeWidth(1);
                line.setHasPoints(false);
                List<Line> lines = new ArrayList<Line>();
                lines.add(line);
                mChartView.setInteractive(false);//设置图表是可以交互的（拖拽，缩放等效果的前提）
                mChartView.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);//设置缩放方向
                LineChartData data = new LineChartData();
                Axis axisX = new Axis();//x轴
                Axis axisY = new Axis();//y轴
                axisX.setHasSeparationLine(true);
                axisX.setTextColor(Color.BLACK);
                axisX.setLineColor(Color.BLACK);
                axisX.setInside(false);
                axisX.setMaxLabelChars(8);
                axisX.setName("日期");
                axisY.setName("实时位移(m)");
                axisY.setLineColor(Color.BLACK);
                axisY.setTextColor(Color.BLACK);
                axisY.setTextSize(16);
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
                data.setLines(lines);
                mChartView.setLineChartData(data);//给图表设置数据

                AlertDialog mDialogCharts = new AlertDialog.Builder(MainActivity.this)
                        .setView(view)
                        .show();
            }
        });
        AlertDialog dialog1 = new AlertDialog.Builder(MainActivity.this)
                .setView(view)
                .show();
    }

    private void showLocalPersonInfo(int zIndex) {
        final String id, type;
        String s = String.valueOf(zIndex);
        id = s.substring(0, s.length() - 1);
        type = String.valueOf(s.charAt(s.length() - 1));
        Log.d(TAG, "id:" + id + "\ntype:" + type);
        waitingDialog = WaitingDialog.createLoadingDialog(this, "正在请求中...");
        OkHttpUtils.get().url(getResources().getString(R.string.get_local_person_info))
                .addParams("id", id)
                .addParams("type", type)
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
                        Log.d("limeng", "response:" + response);
                        Log.d("limeng", "response:" + response);
                        String online = null;
                        String dispicture = null;
                        TextView tvOnline = null;
                        try {
                            JSONObject object = new JSONObject(response);
                            String info = "";
                            View view = null;
                            if ("1".equals(type)) {
                                String name = object.getString("admin_name");
                                String address = object.getString("area_location");
                                String mobile = object.getString("real_mobile");
                                online = object.getString("online");
                                dispicture = object.getString("admin_pic");
                                info = "姓名：" + name + "\n"
                                        + "乡镇：" + address + "\n"
                                        + "电话：" + mobile;
                                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_person_info1, null);
                                ((TextView) view.findViewById(R.id.tv_1_person_name)).append(name == null ? "" : name);
                                ((TextView) view.findViewById(R.id.tv_1_person_address)).append(address == null ? "" : address);
                                ((TextView) view.findViewById(R.id.tv_1_person_mobile)).append(mobile == null ? "" : mobile);
                                tvOnline = ((TextView) view.findViewById(R.id.tv_1_person_is));
                            } else if ("3".equals(type)) {
                                String name = object.getString("name");
                                String address = object.getString("location");
                                String tel = object.getString("zhibantel");
                                String job = object.getString("job");
                                String mobile = object.getString("iphone");
                                online = object.getString("online");
                                dispicture = object.getString("url");
                                info = "姓名：" + name + "\n"
                                        + "地址：" + address + "\n"
                                        + "值班电话：" + tel + "\n"
                                        + "职位：" + job + "\n"
                                        + "手机：" + mobile;
                                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_person_info2, null);
                                ((TextView) view.findViewById(R.id.tv_2_person_name)).append(name == null ? "" : name);
                                ((TextView) view.findViewById(R.id.tv_2_person_address)).append(address == null ? "" : address);
                                ((TextView) view.findViewById(R.id.tv_2_person_mobile)).append(mobile == null ? "" : mobile);
                                ((TextView) view.findViewById(R.id.tv_2_person_tel)).append(tel == null ? "" : tel);
                                ((TextView) view.findViewById(R.id.tv_2_person_job)).append(job == null ? "" : job);
                                tvOnline = ((TextView) view.findViewById(R.id.tv_2_person_is));
                            } else if ("2".equals(type)) {
                                String name = object.getString("disname");
                                String gender = object.getString("gender");
                                String age = object.getString("age");
                                String mobile = object.getString("phone");
                                String manage_area = object.getString("manage_area");
                                String address = object.getString("disarea");
                                String danwei = object.getString("unit_name");
                                online = object.getString("online");
                                String Head_url = object.getString("unit_name");
                                dispicture = object.getString("dispicture");
                                info = "姓名：" + name + "\n"
                                        + "性别：" + gender + "\n"
                                        + "年龄：" + age + "\n"
                                        + "电话：" + mobile + "\n"
                                        + "地址：" + address + "\n"
                                        + "单位：" + danwei + "\n"
                                        + "管理区域：" + manage_area + "\n";
                                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_person_info3, null);
                                ((TextView) view.findViewById(R.id.tv_3_person_name)).append(name == null ? "" : name);
                                ((TextView) view.findViewById(R.id.tv_3_person_gender)).append(gender == null ? "" : gender);
                                ((TextView) view.findViewById(R.id.tv_3_person_age)).append(age == null ? "" : age);
                                ((TextView) view.findViewById(R.id.tv_3_person_mobile)).append(mobile == null ? "" : mobile);
                                ((TextView) view.findViewById(R.id.tv_3_person_address)).append(address == null ? "" : address);
                                ((TextView) view.findViewById(R.id.tv_3_person_danwei)).append(danwei == null ? "" : danwei);
                                ((TextView) view.findViewById(R.id.tv_3_person_manage_area)).append(manage_area == null ? "" : manage_area);
                                tvOnline = ((TextView) view.findViewById(R.id.tv_3_person_is));
                            }
                            if ("0".equals(online)) {
                                tvOnline.append("不在线");
                                tvOnline.setTextColor(Color.RED);
                            } else {
                                tvOnline.append("在线");
                                tvOnline.setTextColor(Color.GREEN);
                            }
                            new AlertDialog.Builder(MainActivity.this)
                                    .setView(view)
                                    .show();
                            Log.d("limeng", "dispicture=" + dispicture);
                            Glide.with(MainActivity.this)
                                    .load("http://183.230.108.112:9077/cqapp/" + dispicture)
                                    .placeholder(R.mipmap.downloading)
                                    .thumbnail(0.1f)
                                    .error(R.mipmap.download_pass)
                                    .into((ImageView) view.findViewById(R.id.dialog_image));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void showPersonInfo(int zIndex) {
        waitingDialog = WaitingDialog.createLoadingDialog(this, "正在请求中...");
        OkHttpUtils.get().url(getResources().getString(R.string.get_person_info))
                .addParams("id", zIndex + "")
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
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<PersonInfo>>() {
                        }.getType();
                        List<PersonInfo> personInfos = gson.fromJson(response, type);
                        PersonInfo personInfo = personInfos.get(0);
                        Log.d(TAG, "人员信息：" + personInfo);
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
                        if (personInfo.getOnline() == 0) {
                            tvOnline.append("不在线");
                            tvOnline.setTextColor(Color.RED);
                        } else {
                            tvOnline.append("在线");
                            tvOnline.setTextColor(Color.GREEN);
                        }
                        ((TextView) view.findViewById(R.id.tv_0_person_ismonitor)).append(personInfo.getIs_monitor() == 1 ? "监测负责人" : "监测人");
                        ((TextView) view.findViewById(R.id.tv_0_person_brithday)).append(personInfo.getBrithday() == null ? "" : personInfo.getBrithday());
                        ((TextView) view.findViewById(R.id.tv_0_person_realmobile)).append(personInfo.getReal_mobile() == null ? "" : personInfo.getReal_mobile());
                        ((TextView) view.findViewById(R.id.tv_0_person_mobile)).append(personInfo.getMobile() == null ? "" : personInfo.getMobile());
                        new AlertDialog.Builder(MainActivity.this)
                                .setView(view)
                                .show();
                        Glide.with(MainActivity.this)
                                .load("http://183.230.108.112:9077/cqapp/" + personInfo.getHead_url())
                                .placeholder(R.mipmap.downloading)
                                .thumbnail(0.1f)
                                .error(R.mipmap.download_pass)
                                .into((ImageView) view.findViewById(R.id.dialog_image));
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
        Log.d("limeng", "type=" + type + "zIndex=" + id + "size=" + mTabDisasterInfo.getData().size());
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
        // TODO 动态添加布局(xml方式)
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
        // TODO 动态添加布局(xml方式)
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
        rc1.setAdapter(new RcPhotoAdapter(this, mList1));
        rc2.setLayoutManager(new LinearLayoutManager(this));
        rc2.setAdapter(new RcPhotoAdapter(this, mList2));
        rc3.setLayoutManager(new LinearLayoutManager(this));
        rc3.setAdapter(new RcPhotoAdapter(this, mList3));
        rc4.setLayoutManager(new LinearLayoutManager(this));
        rc4.setAdapter(new RcPhotoAdapter(this, mList4));
        return view;
    }


    /**
     * 添加TextView
     *
     * @return
     */
    private View addTextView(String info) {
        // TODO 动态添加布局(xml方式)
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
        // TODO 动态添加布局(xml方式)
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
        // TODO 动态添加布局(xml方式)
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
        // TODO 动态添加布局(xml方式)
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
            case R.id.iv_search_main:
                ToSearch();
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
                    graphicsOverlays.clear();
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
                    graphicsOverlays.clear();
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
                    graphicsOverlays.clear();
                    initPersonData();
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
                addEquipment();
                if (llMoreStateBefore != 4) {
                    layers.clear();
                    elevationSources.clear();
                    graphicsOverlays.clear();
                    layers.add(lowImageLayer);
                    layers.add(highImageLayer);
                    elevationSources.add(elevationSource);
                    Camera camera = new Camera(28.769167, 106.910399, 50000.0, 0, 20, 0.0);
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
                    graphicsOverlays.clear();
                    layers.add(xingZhengLayer);
                    Camera camera = new Camera(28.769167, 106.910399, 50000.0, 0, 20, 0.0);
                    sceneView.setViewpointCamera(camera);
                }
                break;
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
        final Spinner spinner1= (Spinner) view.findViewById(R.id.spinner1);
        final Spinner spinner2= (Spinner) view.findViewById(R.id.spinner2);
        final EditText etSearch= (EditText) view.findViewById(R.id.etSearch);
        Button btnSearch= (Button) view.findViewById(R.id.btnSearch);
        final RecyclerView rc=(RecyclerView) view.findViewById(R.id.date_show);
        rc.setLayoutManager(new LinearLayoutManager(context));
        // 建立数据源
        String[] mItems1 = getResources().getStringArray(R.array.search_type_1);
        mItems2 = getResources().getStringArray(R.array.search_type_2_1);
        final String[] mItems21 = getResources().getStringArray(R.array.search_type_2_1);
        final String[] mItems22 = getResources().getStringArray(R.array.search_type_2_2);
        final String[] mItems23 = getResources().getStringArray(R.array.search_type_2_3);
        final String[] mItems24= getResources().getStringArray(R.array.search_type_2_4);
        final String[] mItems25= getResources().getStringArray(R.array.search_type_2_5);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems1);
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems2);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                searchType1=pos;
                switch (pos){
                    case 0:
                        mItems2=mItems21;
                        break;
                    case 1:
                        mItems2=mItems22;
                        etSearch.setText("滑坡");
                        break;
                    case 2:
                        mItems2=mItems23;
                        etSearch.setText("张");
                        break;
                    case 3:
                        mItems2=mItems24;
                        break;
                    case 4:
                        mItems2=mItems25;
                        break;
                }
                ArrayAdapter<String> adapter2=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, mItems2);
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
                searchType2=pos;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info=etSearch.getText().toString();
                Log.d("limeng",searchType1+"----"+searchType2+mItems2.length);
                int type1=0,type2=0;
                switch (searchType1){
                    case 1:
                        type1=8;
                        type2=27;
                        break;
                    case 2:
                        type1=26;
                        type2=32+searchType2-1;
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "暂无数据！", Toast.LENGTH_SHORT).show();
                        return;
                }
                waitingDialog = WaitingDialog.createLoadingDialog(context, "正在请求中...");
                OkHttpUtils.get().url("http://183.230.182.149:18081/springmvc/seek/search/500110/"+info+"/"+type1+"/"+type2)
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
                                        SearchPlace mSearchPlace = gson.fromJson(response, SearchPlace.class);
                                        rc.setAdapter(new RcSearchPlaceAdapter(context,mSearchPlace));
                                        break;
                                    case 2:
                                        SearchPerson mSearchPerson = gson.fromJson(response, SearchPerson.class);
                                        rc.setAdapter(new RcSearchPersonAdapter(context,mSearchPerson));
                                        break;
                                }
                            }
                        });
            }
        });

        final AlertDialog ss = new AlertDialog.Builder(MainActivity.this).setView(view).create();
        ss.show();

        //http://183.230.182.149:18081/springmvc/seek/search/"+areaCode+"/"+searchValue+"/"+searchType1+"/"+searchType2
        //http://183.230.182.149:18081/springmvc/seek/search/500110/s/8/27
        //http://183.230.182.149:18081/springmvc/seek/search/500110,/%E5%BC%A0/26/32
        //http://183.230.182.149:18081/springmvc/seek/search/500110,/%E7%8E%8B/26/33
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
            sceneView.setViewpointCamera(camera);
        } else if (layers.contains(jinQiaoLayers.get(0))) {
            Camera camera = new Camera(29.07337764118905, 106.8774290607224, 2000, 0, 0, 0.0);
            sceneView.setViewpointCamera(camera);
        } else {
            Camera camera = new Camera(28.769167, 106.910399, 50000.0, 0, 20, 0.0);
            sceneView.setViewpointCamera(camera);
        }
    }

    private void initPersonData() {
        waitingDialog = WaitingDialog.createLoadingDialog(this, "正在请求中...");
        OkHttpUtils.get().url(getResources().getString(R.string.get_person_location))
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
                            JSONObject object = new JSONObject(response);
                            JSONObject oj = object.getJSONObject("data");
                            JSONArray array = oj.getJSONArray("群测群防人");
                            for (int i = 0; i < array.length() - 1; i++) {
                                JSONObject o = array.getJSONObject(i);
                                PersonLocation personLocation = new PersonLocation();
                                personLocation.setId(o.getInt("id"));
                                personLocation.setLat(o.getString("dis_lat"));
                                personLocation.setLon(o.getString("dis_lon"));
                                qcPersons.add(personLocation);
                            }
                            setPersonGraphic();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void setPersonGraphic() {
        BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.person);
        final PictureMarkerSymbol symbol = new PictureMarkerSymbol(drawable);
        symbol.setWidth(30);
        symbol.setHeight(30);
        symbol.setOffsetY(11);
        symbol.loadAsync();
        symbol.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                for (PersonLocation disasterPoint : qcPersons) {
                    Point point = new Point(Double.valueOf(disasterPoint.getLon()), Double.valueOf(disasterPoint.getLat()), SpatialReferences.getWgs84());
                    Graphic graphic = new Graphic(point, symbol);
                    graphic.setZIndex(disasterPoint.getId());
                    qcGraphics.add(graphic);
                }

            }

        });
    }

    private void setDisasterLegend(@LayoutRes int resource, int type) {
        LayoutInflater inflater = getLayoutInflater();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.rightMargin = 10;
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

                }
                break;
            case R.id.rb_state_point_0://已销号
                if (b) {
                    mDisasterType = "0";
                    Log.d("limeng", "mDisasterType=" + mDisasterType);
                    initDisasterDataByState(areaCode, 0 + "");
                }
                break;
            case R.id.rb_state_point_2://已治理灾害点
                if (b) {
                    mDisasterType = "2";
                    Log.d("limeng", "mDisasterType=" + mDisasterType);
                    initDisasterDataByState(areaCode, 2 + "");
                }
                break;
            case R.id.rb_state_point_3://已搬迁灾害点
                if (b) {
                    mDisasterType = "3";
                    Log.d("limeng", "mDisasterType=" + mDisasterType);
                    initDisasterDataByState(areaCode, 3 + "");
                }
                break;
            case R.id.rb_state_point_1://库岸调查
                if (b) {
                    mDisasterType = "1";
                    Log.d("limeng", "mDisasterType=" + mDisasterType);
                    initDisasterDataByState(areaCode, 1 + "");
                }
                break;
            case R.id.rb_qcqf_person:
                if (b) {
                    updatePersonGraphic(qcGraphics);
                    setPieChartData("71", "在线率");
                }
                break;
            case R.id.rb_zs_person:
                if (b) {
                    updateLocalGraphic(zsGraphics);
                    setPieChartData("64", "在线率");
                }
                break;
            case R.id.rb_pq_person:
                if (b) {
                    updateLocalGraphic(pqGraphics);
                    setPieChartData("90", "在线率");
                }
                break;
            case R.id.rb_dhz_person:
                if (b) {
                    updateLocalGraphic(dhzGraphics);
                    setPieChartData("35", "在线率");
                }
                break;
            case R.id.rb_equipment_jiance:
                if (b) {
                    updateEquipmentGraphic(jianceGraphics);
                    setPieChartData("88", "在线率");
                }
                break;
            case R.id.rb_jinqiao:
                if (b) {
                    layers.clear();
                    elevationSources.clear();
                    graphicsOverlays.clear();
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
                    graphicsOverlays.clear();
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
                    setRender();
                    layers.add(xzFeatureLayer);
                } else {
                    layers.remove(xzFeatureLayer);
                }
                break;
            case R.id.rb_qxyb:
                if (b) {
                    updateWeather(weatherGraphics);
                } else {
                    graphicsOverlays.clear();
                }
                break;
        }
    }

    private void updateWeather(List<Graphic> g) {
        weathersGraphics.clear();
        weathersGraphics.addAll(g);
        graphicsOverlays.clear();
        graphicsOverlays.add(weatherGraphicOverlay);
    }

    private void setRender() {
        UniqueValueRenderer uniqueValueRenderer = new UniqueValueRenderer();
        uniqueValueRenderer.getFieldNames().add("name");

        SimpleFillSymbol defaultFillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.NULL, Color.BLACK, new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.GRAY, 2));
        SimpleFillSymbol symbol1 = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.RED, new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.GRAY, 3));
        SimpleFillSymbol symbol2 = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.parseColor("#FF6100"), new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.GRAY, 3));
        SimpleFillSymbol symbol3 = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.YELLOW, new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.GRAY, 3));

        uniqueValueRenderer.setDefaultSymbol(defaultFillSymbol);
        uniqueValueRenderer.setDefaultLabel("Other");

        List<Object> wanDongValue = new ArrayList<>();
        wanDongValue.add("万东镇");
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("万东镇", "State of California", symbol2, wanDongValue));

        List<Object> congLinValue = new ArrayList<>();
        congLinValue.add("丛林镇");
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("丛林镇", "State of Arizona", symbol2, congLinValue));

        List<Object> guanBaValue = new ArrayList<>();
        guanBaValue.add("关坝镇");
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("关坝镇", "State of Nevada", symbol3, guanBaValue));

        List<Object> nanTongValue = new ArrayList<>();
        nanTongValue.add("南桐镇");
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("南桐镇", "State of Nevada", symbol3, nanTongValue));

        List<Object> shiLinValue = new ArrayList<>();
        shiLinValue.add("石林镇");
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("石林镇", "State of Nevada", symbol1, shiLinValue));

        List<Object> jinQiaoValue = new ArrayList<>();
        jinQiaoValue.add("金桥镇");
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("金桥镇", "State of Nevada", symbol1, jinQiaoValue));

        List<Object> qingNianVlue = new ArrayList<>();
        qingNianVlue.add("青年镇");
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("青年镇", "State of Nevada", symbol2, qingNianVlue));

        List<Object> heiShanValue = new ArrayList<>();
        heiShanValue.add("黑山镇");
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue("黑山镇", "State of Nevada", symbol3, heiShanValue));

        xzFeatureLayer.setRenderer(uniqueValueRenderer);
    }


    private void addEquipment() {
        BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.sign);
        final PictureMarkerSymbol symbol = new PictureMarkerSymbol(drawable);
        symbol.setWidth(50);
        symbol.setHeight(50);
        symbol.setOffsetY(11);
        symbol.loadAsync();
        symbol.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                Point point = new Point(106.91015726332898, 28.87312428984992, SpatialReferences.getWgs84());
                Graphic graphic = new Graphic(point, symbol);
                graphic.setZIndex(100000);
                jianceGraphics.add(graphic);
            }

        });
    }

    private void updateLocalGraphic(List<Graphic> g) {
        localGraphics.clear();
        localGraphics.addAll(g);
        graphicsOverlays.clear();
        graphicsOverlays.add(localGraphicsOverlay);
    }

    private void updatePersonGraphic(List<Graphic> q) {
        personGraphics.clear();
        personGraphics.addAll(q);
        graphicsOverlays.clear();
        graphicsOverlays.add(personGraphicsOverlay);
    }

    private void updateEquipmentGraphic(List<Graphic> graphics) {
        equipmentGraphics.clear();
        equipmentGraphics.addAll(graphics);
        graphicsOverlays.clear();
        graphicsOverlays.add(equipmentGraphicOverlay);
    }

    /**
     * 更新地图上的图标
     *
     * @param g 图标集合
     */
    private void updateGraphic(List<Graphic> g) {
        graphics.clear();
        graphics.addAll(g);
        graphicsOverlays.clear();
        graphicsOverlays.add(graphicsOverlay);
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

    private void setMoreState() {
        if (llAreaState == false) {
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(ivAreaBack, "rotation", 0, 180);
            animator1.setDuration(100);
            animator1.start();
            llAreaState = true;
        } else {
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(ivAreaBack, "rotation", 180, 0);
            animator1.setDuration(100);
            animator1.start();
            llAreaState = false;
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


}
