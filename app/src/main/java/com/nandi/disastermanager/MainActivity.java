package com.nandi.disastermanager;

import android.animation.ObjectAnimator;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.mapping.ArcGISScene;
import com.esri.arcgisruntime.mapping.ArcGISTiledElevationSource;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.LayerList;
import com.esri.arcgisruntime.mapping.Surface;
import com.esri.arcgisruntime.mapping.view.Camera;
import com.esri.arcgisruntime.mapping.view.DefaultSceneViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.SceneView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.util.ListenableList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

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
    @BindView(R.id.rb_canceled_point)
    RadioButton rbCanceledPoint;//已消耗
    @BindView(R.id.rb_handled_point)
    RadioButton rbHandledPoint;//未消耗
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
    private ListenableList<GraphicsOverlay> graphicsOverlays;
    private List<Graphic> allGraphics = new ArrayList<>();//所有的灾害点图标
    private List<Graphic> consumedGraphics = new ArrayList<>();//已消耗灾害点图标
    private List<Graphic> notConsumeGraphics = new ArrayList<>();//未消耗灾害点图标
    private List<Graphic> allHuaPOGraphics = new ArrayList<>();
    private List<Graphic> allNiSHILiuGraphics = new ArrayList<>();
    private List<Graphic> allWeiYanGraphics = new ArrayList<>();
    private List<Graphic> allXiePoGraphics = new ArrayList<>();
    private List<Graphic> allTanTaGraphics = new ArrayList<>();
    private List<Graphic> allLieFengGraphics = new ArrayList<>();
    private List<Graphic> allTaAnGraphics = new ArrayList<>();

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
    private ListenableList<Graphic> graphics;
    private ListenableList<Graphic> personGraphics;
    private ListenableList<Graphic> localGraphics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initStaData();
        initLocalData();
        dianziLayer = new ArcGISMapImageLayer(getResources().getString(R.string.dianziditu_url));
        lowImageLayer = new ArcGISMapImageLayer(getResources().getString(R.string.image_layer_13_url));
        highImageLayer = new ArcGISMapImageLayer(getResources().getString(R.string.image_layer_13_19_url));
        vectorLayer = new ArcGISMapImageLayer(getResources().getString(R.string.shiliangtu_url));
        dengZXLayer = new ArcGISMapImageLayer(getResources().getString(R.string.yuliang_url));
        ssYLLayer=new ArcGISMapImageLayer(getResources().getString(R.string.ssyl_url));
        elevationSource = new ArcGISTiledElevationSource(
                getResources().getString(R.string.elevation_image_service));
        scene = new ArcGISScene();
        layers = scene.getOperationalLayers();
        graphicsOverlay = new GraphicsOverlay();
        personGraphicsOverlay = new GraphicsOverlay();
        localGraphicsOverlay = new GraphicsOverlay();
        graphics = graphicsOverlay.getGraphics();
        personGraphics = personGraphicsOverlay.getGraphics();
        localGraphics = localGraphicsOverlay.getGraphics();
        graphicsOverlays = sceneView.getGraphicsOverlays();
        elevationSources = scene.getBaseSurface().getElevationSources();
        scene.setBasemap(Basemap.createImagery());
        sceneView.setScene(scene);
        setListeners();
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
        OkHttpUtils.get().url(getResources().getString(R.string.statistics_url))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "统计信息获取失败，请检查网路！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
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
        ivAreaBack.setOnClickListener(this);
        ivDataBack.setOnClickListener(this);
        llRainfall.setOnClickListener(this);
        llDangerpoint.setOnClickListener(this);
        llStaff.setOnClickListener(this);
        llEquipment.setOnClickListener(this);
        rbSsyl.setOnCheckedChangeListener(this);
        rbYldzx.setOnCheckedChangeListener(this);
        rbDisasterPoint.setOnCheckedChangeListener(this);
        rbCanceledPoint.setOnCheckedChangeListener(this);
        rbHandledPoint.setOnCheckedChangeListener(this);
        rbQcqfPerson.setOnCheckedChangeListener(this);
        rbDhzPerson.setOnCheckedChangeListener(this);
        rbZsPerson.setOnCheckedChangeListener(this);
        rbPqPerson.setOnCheckedChangeListener(this);
        sceneView.setOnTouchListener(new DefaultSceneViewOnTouchListener(sceneView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                // get the screen point where user tapped
                android.graphics.Point screenPoint = new android.graphics.Point((int) e.getX(), (int) e.getY());
                // identify graphics on the graphics overlay
                final ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphic = sceneView.identifyGraphicsOverlayAsync(graphicsOverlay, screenPoint, 10.0, false, 2);
                final ListenableFuture<IdentifyGraphicsOverlayResult> personIdentifyGraphic = sceneView.identifyGraphicsOverlayAsync(personGraphicsOverlay, screenPoint, 10.0, false, 2);
                final ListenableFuture<IdentifyGraphicsOverlayResult> localIdentifyGraphic = sceneView.identifyGraphicsOverlayAsync(localGraphicsOverlay, screenPoint, 10.0, false, 2);
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

                return super.onSingleTapConfirmed(e);
            }
        });
    }

    private void showLocalPersonInfo(int zIndex) {
        final String id, type;
        String s = String.valueOf(zIndex);
        id = s.substring(0, s.length() - 1);
        type = String.valueOf(s.charAt(s.length() - 1));
        Log.d(TAG, "id:" + id + "\ntype:" + type);
        OkHttpUtils.get().url(getResources().getString(R.string.get_local_person_info))
                .addParams("id", id)
                .addParams("type", type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("limeng","response:"+response);

                        try {
                            JSONObject object = new JSONObject(response);
                            String info = "";
                            View view = null;
                            if ("1".equals(type)) {
                                String name = object.getString("admin_name");
                                String address = object.getString("area_location");
                                String mobile = object.getString("real_mobile");
                                info = "姓名：" + name + "\n"
                                        + "乡镇：" + address + "\n"
                                        + "电话：" + mobile;
                                view= LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_person_info1, null);
                                ((TextView)view.findViewById(R.id.tv_1_person_name)).append(name==null?"":name);
                                ((TextView)view.findViewById(R.id.tv_1_person_address)).append(address==null?"":address);
                                ((TextView)view.findViewById(R.id.tv_1_person_mobile)).append(mobile==null?"":mobile);

                            } else if ("3".equals(type)) {
                                String name = object.getString("name");
                                String address = object.getString("location");
                                String tel = object.getString("zhibantel");
                                String job = object.getString("job");
                                String mobile = object.getString("iphone");
                                info = "姓名：" + name + "\n"
                                        + "地址：" + address + "\n"
                                        + "值班电话：" + tel + "\n"
                                        + "职位：" + job + "\n"
                                        + "手机：" + mobile;
                                view= LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_person_info2, null);
                                ((TextView)view.findViewById(R.id.tv_2_person_name)).append(name==null?"":name);
                                ((TextView)view.findViewById(R.id.tv_2_person_address)).append(address==null?"":address);
                                ((TextView)view.findViewById(R.id.tv_2_person_mobile)).append(mobile==null?"":mobile);
                                ((TextView)view.findViewById(R.id.tv_2_person_tel)).append(tel==null?"":tel);
                                ((TextView)view.findViewById(R.id.tv_2_person_job)).append(job==null?"":job);
                            } else if ("2".equals(type)) {
                                String name = object.getString("disname");
                                String gender = object.getString("gender");
                                String age = object.getString("age");
                                String mobile = object.getString("phone");
                                String manage_area = object.getString("manage_area");
                                String address = object.getString("disarea");
                                String danwei = object.getString("unit_name");
                                info = "姓名：" + name + "\n"
                                        + "性别：" + gender + "\n"
                                        + "年龄：" + age + "\n"
                                        + "电话：" + mobile + "\n"
                                        + "地址：" + address + "\n"
                                        + "单位：" + danwei + "\n"
                                        + "管理区域：" + manage_area + "\n";
                                view= LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_person_info3, null);
                                ((TextView)view.findViewById(R.id.tv_3_person_name)).append(name==null?"":name);
                                ((TextView)view.findViewById(R.id.tv_3_person_gender)).append(gender==null?"":gender);
                                ((TextView)view.findViewById(R.id.tv_3_person_age)).append(age==null?"":age);
                                ((TextView)view.findViewById(R.id.tv_3_person_mobile)).append(mobile==null?"":mobile);
                                ((TextView)view.findViewById(R.id.tv_3_person_address)).append(address==null?"":address);
                                ((TextView)view.findViewById(R.id.tv_3_person_danwei)).append(danwei==null?"":danwei);
                                ((TextView)view.findViewById(R.id.tv_3_person_manage_area)).append(manage_area==null?"":manage_area);
                            }
                            new AlertDialog.Builder(MainActivity.this)
                                    .setView(view)
                                    .show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void showPersonInfo(int zIndex) {
        OkHttpUtils.get().url(getResources().getString(R.string.get_person_info))
                .addParams("id", zIndex + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<PersonInfo>>() {
                        }.getType();
                        List<PersonInfo> personInfos = gson.fromJson(response, type);
                        PersonInfo personInfo = personInfos.get(0);
                        Log.d(TAG, "人员信息：" + personInfo);
                        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_person_info, null);
                        ((TextView)view.findViewById(R.id.tv_0_person_name)).append(personInfo.getName()==null?"":personInfo.getName());
                        ((TextView)view.findViewById(R.id.tv_0_person_work)).append(personInfo.getWork()==null?"":personInfo.getWork());
                        String polics="";
                        switch (personInfo.getPolics()==null?-1:Integer.parseInt(personInfo.getPolics())){
                            case 1: polics="中共党员" ;break;
                            case 2: polics="中共预备党员" ;break;
                            case 3: polics="共青团员" ;break;
                            case 4: polics="群众" ;break;
                            case 5: polics="民革党员" ;break;
                            case 6: polics="民盟盟员" ;break;
                            case 7: polics="民建会员" ;break;
                        }
                        ((TextView)view.findViewById(R.id.tv_0_person_polics)).append(polics);
                        ((TextView)view.findViewById(R.id.tv_0_person_nation)).append(personInfo.getNation()==null?"":personInfo.getNation());
                        ((TextView)view.findViewById(R.id.tv_0_person_address)).append(personInfo.getAddress()==null?"":personInfo.getAddress());

                        ((TextView)view.findViewById(R.id.tv_0_person_ismonitor)).append(personInfo.getIs_monitor()==1?"监测负责人":"监测人");
                        ((TextView)view.findViewById(R.id.tv_0_person_brithday)).append(personInfo.getBrithday()==null?"":personInfo.getBrithday());
                        ((TextView)view.findViewById(R.id.tv_0_person_realmobile)).append(personInfo.getReal_mobile()==null?"":personInfo.getReal_mobile());
                        ((TextView)view.findViewById(R.id.tv_0_person_mobile)).append(personInfo.getMobile()==null?"":personInfo.getMobile());
                        new AlertDialog.Builder(MainActivity.this)
                                .setView(view)
                                .show();
                    }
                });
    }

    private void showInfo(int zIndex) {
        OkHttpUtils.get().url(getResources().getString(R.string.get_disaster_info))
                .addParams("id", zIndex + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<DisasterInfo>>() {
                        }.getType();
                        List<DisasterInfo> disasterInfos = gson.fromJson(response, type);
                        DisasterInfo disasterInfo = disasterInfos.get(0);
//                        String info = "名称：" + disasterInfo.getDis_name() + "\n"
//                                + "地点：" + disasterInfo.getDis_location() + "\n"
//                                + "经纬度：" + disasterInfo.getDis_lon() + "," + disasterInfo.getDis_lat() + "\n"
//                                + "灾害因素：" + disasterInfo.getDis_cause() + "\n"
//                                + "受灾面积：" + disasterInfo.getDis_area() + "\n"
//                                + "受灾体积：" + disasterInfo.getDis_volume() + "\n"
//                                + "威胁户数：" + disasterInfo.getImperil_families() + "\n"
//                                + "威胁人数：" + disasterInfo.getImperil_man() + "\n"
//                                + "威胁房屋：" + disasterInfo.getImperil_house() + "\n"
//                                + "威胁房屋面积：" + disasterInfo.getImperil_area() + "\n"
//                                + "影响对象：" + disasterInfo.getMain_object() + "\n"
//                                + "威胁财产：" + disasterInfo.getImperil_money() + "\n"
//                                + "灾害等级：" + disasterInfo.getImperil_level() + "\n"
//                                + "是否涉水：" + (disasterInfo.getDis_sfss() == 1 ? "是" : "否") + "\n"
//                                + "告警号码:" + disasterInfo.getWarn_mobile() + "\n"
//                                + "入库时间:" + disasterInfo.getCome_time() + "\n";
//                        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_disaster_info, null);
//                        TextView tvInfo = (TextView) view.findViewById(R.id.dialog_text);
//                        tvInfo.setText(info);
//                        new AlertDialog.Builder(MainActivity.this)
//                                .setView(view)
//                                .show();

                        setDialogViewDatas(disasterInfo);
                    }
                });
    }


    private void setDialogViewDatas(DisasterInfo pointInfo) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_disasterinfo, null);
        final LinearLayout llBaseInfo = (LinearLayout) view.findViewById(R.id.ll_base_info);
        final LinearLayout llImageInfo = (LinearLayout) view.findViewById(R.id.ll_image_info);
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.rg_disaster_info);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_base_info:
                        llBaseInfo.setVisibility(View.VISIBLE);
                        llImageInfo.setVisibility(View.GONE);
                        break;
                    case R.id.rbtn_image_info:
                        llBaseInfo.setVisibility(View.GONE);
                        llImageInfo.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        TextView tvDisName = (TextView) view.findViewById(R.id.tv_dis_name);
        TextView tvDisType = (TextView) view.findViewById(R.id.tv_dis_type);
        TextView tvDisState = (TextView) view.findViewById(R.id.tv_dis_state);
        TextView tvDisLocation = (TextView) view.findViewById(R.id.tv_dis_location);
        TextView tvDisLon = (TextView) view.findViewById(R.id.tv_dis_lon);
        TextView tvDisLat = (TextView) view.findViewById(R.id.tv_dis_lat);
        TextView tvDisCause = (TextView) view.findViewById(R.id.tv_dis_cause);
        TextView tvDisSlope = (TextView) view.findViewById(R.id.tv_dis_slope);
        TextView tvDisArea = (TextView) view.findViewById(R.id.tv_dis_area);
        TextView tvDisVolume = (TextView) view.findViewById(R.id.tv_dis_volume);
        TextView tvDisBefore = (TextView) view.findViewById(R.id.tv_dis_before);
        TextView tvDisAfter = (TextView) view.findViewById(R.id.tv_dis_after);
        TextView tvImperilFamilies = (TextView) view.findViewById(R.id.tv_imperil_families);
        TextView tvImperilMan = (TextView) view.findViewById(R.id.tv_imperil_man);
        TextView tvImperilHouse = (TextView) view.findViewById(R.id.tv_imperil_house);
        TextView tvImperilArea = (TextView) view.findViewById(R.id.tv_imperil_area);
        TextView tvMainObject = (TextView) view.findViewById(R.id.tv_main_object);
        TextView tvImperilMoney = (TextView) view.findViewById(R.id.tv_imperil_money);
        TextView tvStableLevel = (TextView) view.findViewById(R.id.tv_stable_level);
        TextView tvImperilLevel = (TextView) view.findViewById(R.id.tv_imperil_level);
        TextView tvDealIdea = (TextView) view.findViewById(R.id.tv_deal_idea);
        TextView tvDefenseLevel = (TextView) view.findViewById(R.id.tv_defense_level);
        TextView tvAreaId = (TextView) view.findViewById(R.id.tv_area_id);
        TextView tvQcqfryId = (TextView) view.findViewById(R.id.tv_qcqfry_id);
        TextView tvWarnMobile = (TextView) view.findViewById(R.id.tv_warn_mobile);
        TextView tvHasMobile = (TextView) view.findViewById(R.id.tv_has_mobile);
        TextView tvBz = (TextView) view.findViewById(R.id.tv_bz);
        TextView tvDisRadius = (TextView) view.findViewById(R.id.tv_dis_radius);
        TextView tvScale = (TextView) view.findViewById(R.id.tv_scale);
        TextView tvStateTime = (TextView) view.findViewById(R.id.tv_state_time);
        TextView tvComeTime = (TextView) view.findViewById(R.id.tv_come_time);
        TextView tvOperation = (TextView) view.findViewById(R.id.tv_Operation);
        TextView tvStatusNo = (TextView) view.findViewById(R.id.tv_status_no);
        TextView tvDisSfss = (TextView) view.findViewById(R.id.tv_dis_sfss);
        tvDisName.setText(pointInfo.getDis_name() + "");
        tvDisType.setText(pointInfo.getDis_type() + "");
        tvDisState.setText(pointInfo.getDis_state() + "");
        tvDisLocation.setText(pointInfo.getDis_location() + "");
        tvDisLon.setText(pointInfo.getDis_lon() + "");
        tvDisLat.setText(pointInfo.getDis_lat() + "");
        tvDisCause.setText(pointInfo.getDis_cause() + "");
        tvDisSlope.setText(pointInfo.getDis_slope() + "");
        tvDisArea.setText(pointInfo.getDis_area() + "");
        tvDisVolume.setText(pointInfo.getDis_volume() + "");
        tvDisBefore.setText(pointInfo.getDis_before() + "");
        tvDisAfter.setText(pointInfo.getDis_after() + "");
        tvImperilFamilies.setText(pointInfo.getImperil_families() + "");
        tvImperilMan.setText(pointInfo.getImperil_man() + "");
        tvImperilHouse.setText(pointInfo.getImperil_house() + "");
        tvImperilArea.setText(pointInfo.getImperil_area() + "");
        tvMainObject.setText(pointInfo.getMain_object() + "");
        tvImperilMoney.setText(pointInfo.getImperil_money() + "");
        tvStableLevel.setText(pointInfo.getStable_level() + "");
        tvImperilLevel.setText(pointInfo.getImperil_level() + "");
        tvDealIdea.setText(pointInfo.getDeal_idea() + "");
        tvDefenseLevel.setText(pointInfo.getDefense_level() + "");
        tvAreaId.setText(pointInfo.getArea_id() + "");
        tvQcqfryId.setText(pointInfo.getQcqfry_id() + "");
        tvWarnMobile.setText(pointInfo.getWarn_mobile() + "");
        tvHasMobile.setText(pointInfo.getHas_mobile() + "");
        tvBz.setText(pointInfo.getBz() + "");
        tvDisRadius.setText(pointInfo.getDis_radius() + "");
        tvScale.setText(pointInfo.getScale() + "");
        tvStateTime.setText(pointInfo.getState_time() + "");
        tvComeTime.setText(pointInfo.getCome_time() + "");
        tvOperation.setText(pointInfo.getOperation() + "");
        tvStatusNo.setText(pointInfo.getStatus_no() + "");
        tvDisSfss.setText(pointInfo.getDis_sfss() + "");

        new AlertDialog.Builder(MainActivity.this)
                .setView(view)
                .show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                    layers.clear();
                    elevationSources.clear();
                    graphicsOverlays.clear();
                    initData();
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
                break;

        }
    }

    private void initPersonData() {
        OkHttpUtils.get().url(getResources().getString(R.string.get_person_location))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
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
                    } else if (rbHandledPoint.isChecked()) {
                        updateGraphic(notHuaPOGraphics);
                    } else if (rbCanceledPoint.isChecked()) {
                        updateGraphic(conHuaPOGraphics);
                    }
                }
            });
            linearLayout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rbDisasterPoint.isChecked()) {
                        updateGraphic(allNiSHILiuGraphics);
                    } else if (rbHandledPoint.isChecked()) {
                        updateGraphic(notNiSHILiuGraphics);
                    } else if (rbCanceledPoint.isChecked()) {
                        updateGraphic(conNiSHILiuGraphics);
                    }
                }
            });
            linearLayout3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rbDisasterPoint.isChecked()) {
                        updateGraphic(allWeiYanGraphics);
                    } else if (rbHandledPoint.isChecked()) {
                        updateGraphic(notWeiYanGraphics);
                    } else if (rbCanceledPoint.isChecked()) {
                        updateGraphic(conWeiYanGraphics);
                    }
                }
            });
            linearLayout4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rbDisasterPoint.isChecked()) {
                        updateGraphic(allXiePoGraphics);
                    } else if (rbHandledPoint.isChecked()) {
                        updateGraphic(notXiePoGraphics);
                    } else if (rbCanceledPoint.isChecked()) {
                        updateGraphic(conXiePoGraphics);
                    }
                }
            });
            linearLayout5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rbDisasterPoint.isChecked()) {
                        updateGraphic(allTanTaGraphics);
                    } else if (rbHandledPoint.isChecked()) {
                        updateGraphic(notTanTaGraphics);
                    } else if (rbCanceledPoint.isChecked()) {
                        updateGraphic(conTaAnGraphics);
                    }
                }
            });
            linearLayout6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rbDisasterPoint.isChecked()) {
                        updateGraphic(allLieFengGraphics);
                    } else if (rbHandledPoint.isChecked()) {
                        updateGraphic(notLieFengGraphics);
                    } else if (rbCanceledPoint.isChecked()) {
                        updateGraphic(conLieFengGraphics);
                    }
                }
            });
            linearLayout7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rbDisasterPoint.isChecked()) {
                        updateGraphic(allTaAnGraphics);
                    } else if (rbHandledPoint.isChecked()) {
                        updateGraphic(notTaAnGraphics);
                    } else if (rbCanceledPoint.isChecked()) {
                        updateGraphic(conTaAnGraphics);
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
                Log.d(TAG, "所有的灾害点集合大小：" + allGraphics.size() + "\n"
                        + "已消耗灾害点集合大小：" + consumedGraphics.size() + "\n"
                        + "未消耗灾害点集合大小：" + notConsumeGraphics.size());
                if (b) {
                    updateGraphic(allGraphics);
                }
                break;
            case R.id.rb_canceled_point://已消耗
                if (b) {
                    updateGraphic(consumedGraphics);
                }
                break;
            case R.id.rb_handled_point:
                if (b) {
                    updateGraphic(notConsumeGraphics);
                }
                break;
            case R.id.rb_qcqf_person:
                if (b) {
                    updatePersonGraphic(qcGraphics);
                }
                break;
            case R.id.rb_zs_person:
                if (b) {
                    updateLocalGraphic(zsGraphics);
                }
                break;
            case R.id.rb_pq_person:
                if (b) {
                    updateLocalGraphic(pqGraphics);
                }
                break;
            case R.id.rb_dhz_person:
                if (b) {
                    updateLocalGraphic(dhzGraphics);
                }
                break;
        }
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

    private void initData() {
        OkHttpUtils.get().url(getResources().getString(R.string.get_disaster_point))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<DisasterPoint>>() {
                        }.getType();
                        disasterPoints = gson.fromJson(response, type);
                        Log.d("WSD", "集合大小：" + disasterPoints.size() + "\n数据:" + disasterPoints);
                        setOverlay();
                    }
                });
    }

    private void setOverlay() {
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
                    for (DisasterPoint disasterPoint : disasterPoints) {
                        if (disasterPoint.getDis_type() == finalI) {
                            Point point = new Point(Double.valueOf(disasterPoint.getDis_lon()), Double.valueOf(disasterPoint.getDis_lat()), SpatialReferences.getWgs84());
                            Graphic graphic = new Graphic(point, pinStarBlueSymbol);
                            graphic.setZIndex(disasterPoint.getId());
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
                            if (disasterPoint.getDis_state() == 1) {
                                notConsumeGraphics.add(graphic);
                                switch (finalI) {
                                    case 1:
                                        notHuaPOGraphics.add(graphic);
                                        break;
                                    case 2:
                                        notNiSHILiuGraphics.add(graphic);
                                        break;
                                    case 3:
                                        notWeiYanGraphics.add(graphic);
                                        break;
                                    case 4:
                                        notXiePoGraphics.add(graphic);
                                        break;
                                    case 5:
                                        notTanTaGraphics.add(graphic);
                                        break;
                                    case 6:
                                        notLieFengGraphics.add(graphic);
                                        break;
                                    case 7:
                                        notTaAnGraphics.add(graphic);
                                        break;
                                }
                            } else {
                                consumedGraphics.add(graphic);
                                switch (finalI) {
                                    case 1:
                                        conHuaPOGraphics.add(graphic);
                                        break;
                                    case 2:
                                        conNiSHILiuGraphics.add(graphic);
                                        break;
                                    case 3:
                                        conWeiYanGraphics.add(graphic);
                                        break;
                                    case 4:
                                        conXiePoGraphics.add(graphic);
                                        break;
                                    case 5:
                                        conTanTaGraphics.add(graphic);
                                        break;
                                    case 6:
                                        conLieFengGraphics.add(graphic);
                                        break;
                                    case 7:
                                        conTaAnGraphics.add(graphic);
                                        break;
                                }
                            }
                        }
                    }
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
                break;
            case 2:
                rgRainfall.setVisibility(View.GONE);
                rgDangerpoint.setVisibility(View.VISIBLE);
                rgStaff.setVisibility(View.GONE);
                rgEquipment.setVisibility(View.GONE);
                break;
            case 3:
                rgRainfall.setVisibility(View.GONE);
                rgDangerpoint.setVisibility(View.GONE);
                rgStaff.setVisibility(View.VISIBLE);
                rgEquipment.setVisibility(View.GONE);
                break;
            case 4:
                rgRainfall.setVisibility(View.GONE);
                rgDangerpoint.setVisibility(View.GONE);
                rgStaff.setVisibility(View.GONE);
                rgEquipment.setVisibility(View.VISIBLE);
                break;
        }


        if (llMoreState != llMoreStateBefore) {
            rgRainfall.clearCheck();
            rgDangerpoint.clearCheck();
            rgStaff.clearCheck();
            rgEquipment.clearCheck();
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
