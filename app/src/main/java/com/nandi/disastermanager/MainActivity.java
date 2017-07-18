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
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
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

import java.lang.reflect.Type;
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
    RadioButton rbCanceledPoint;
    @BindView(R.id.rb_handled_point)
    RadioButton rbHandledPoint;
    @BindView(R.id.rb_moved_point)
    RadioButton rbMovedPoint;
    private boolean llAreaState = false;
    private boolean llDataState = false;
    private int llMoreState = -1;
    private int llMoreStateBefore = -1;
    private View view;

    ArcGISMapImageLayer lowImageLayer;
    ArcGISMapImageLayer highImageLayer;
    ArcGISMapImageLayer vectorLayer;
    ArcGISMapImageLayer dengZXLayer;
    ArcGISMapImageLayer ssYLLayer;
    private ArcGISScene scene;
    private ArcGISTiledElevationSource elevationSource;
    private LayerList layers;
    private Surface.ElevationSourceList elevationSources;
    private List<DisasterPoint> disasterPoints;
    private GraphicsOverlay graphicsOverlay;
    private ListenableList<GraphicsOverlay> graphicsOverlays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        lowImageLayer = new ArcGISMapImageLayer(getResources().getString(R.string.image_layer_13_url));
        highImageLayer = new ArcGISMapImageLayer(getResources().getString(R.string.image_layer_13_19_url));
        vectorLayer = new ArcGISMapImageLayer(getResources().getString(R.string.shiliangtu_url));
        dengZXLayer = new ArcGISMapImageLayer(getResources().getString(R.string.yuliang_url));
        ssYLLayer = new ArcGISMapImageLayer(getResources().getString(R.string.ssyl_url));
        elevationSource = new ArcGISTiledElevationSource(
                getResources().getString(R.string.elevation_url));
        scene = new ArcGISScene();
        layers = scene.getOperationalLayers();
        graphicsOverlay = new GraphicsOverlay();
        graphicsOverlays = sceneView.getGraphicsOverlays();
        elevationSources = scene.getBaseSurface().getElevationSources();
        scene.setBasemap(Basemap.createImagery());
        sceneView.setScene(scene);
        setListeners();
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
        rbMovedPoint.setOnCheckedChangeListener(this);
        sceneView.setOnTouchListener(new DefaultSceneViewOnTouchListener(sceneView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                // get the screen point where user tapped
                android.graphics.Point screenPoint = new android.graphics.Point((int) e.getX(), (int) e.getY());
                // identify graphics on the graphics overlay
                final ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphic = sceneView.identifyGraphicsOverlayAsync(graphicsOverlay, screenPoint, 10.0, false, 2);

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

                return super.onSingleTapConfirmed(e);
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
                        String info = "名称：" + disasterInfo.getDis_name() + "\n"
                                + "地点：" + disasterInfo.getDis_location() + "\n"
                                + "经纬度：" + disasterInfo.getDis_lon() + "," + disasterInfo.getDis_lat() + "\n"
                                + "灾害因素：" + disasterInfo.getDis_cause() + "\n"
                                + "受灾面积：" + disasterInfo.getDis_area() + "\n"
                                + "受灾体积：" + disasterInfo.getDis_volume() + "\n"
                                + "威胁户数：" + disasterInfo.getImperil_families() + "\n"
                                + "威胁人数：" + disasterInfo.getImperil_man() + "\n"
                                + "威胁房屋：" + disasterInfo.getImperil_house() + "\n"
                                + "威胁房屋面积：" + disasterInfo.getImperil_area() + "\n"
                                + "影响对象：" + disasterInfo.getMain_object() + "\n"
                                + "威胁财产：" + disasterInfo.getImperil_money() + "\n"
                                + "灾害等级：" + disasterInfo.getImperil_level() + "\n"
                                + "是否涉水：" + (disasterInfo.getDis_sfss() == 1 ? "是" : "否") + "\n"
                                + "告警号码:" + disasterInfo.getWarn_mobile() + "\n"
                                + "入库时间:" + disasterInfo.getCome_time() + "\n";
                        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_disaster_info, null);
                        TextView tvInfo = (TextView) view.findViewById(R.id.dialog_text);
                        tvInfo.setText(info);
                        new AlertDialog.Builder(MainActivity.this)
                                .setView(view)
                                .show();
                    }
                });
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
                setDisasterLegend(R.layout.activity_rainfall_legend,1);
                if (llMoreStateBefore!=1){
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
                setDisasterLegend(R.layout.activity_disaster_legend,2);
                if (llMoreStateBefore!=2){
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
                break;
            case R.id.ll_equipment:
                llMoreStateBefore = llMoreState;
                llMoreState = 4;
                setRainfallMore();
                break;

        }
    }

    private void setDisasterLegend(@LayoutRes int resource,int type) {
        LayoutInflater inflater = getLayoutInflater();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.rightMargin=10;
        lp.bottomMargin=20;
        if (view==null){
        view = inflater.inflate(resource, null);
        view.setLayoutParams(lp);
        rlMain.addView(view);
        }else{
            rlMain.removeView(view);
            view = inflater.inflate(resource, null);
            view.setLayoutParams(lp);
            rlMain.addView(view);
            if(type==2) {
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
                        Toast.makeText(MainActivity.this,"11111",Toast.LENGTH_SHORT).show();
                    }
                });
                linearLayout2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"2",Toast.LENGTH_SHORT).show();
                    }
                });
                linearLayout3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"3",Toast.LENGTH_SHORT).show();
                    }
                });
                linearLayout4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"4",Toast.LENGTH_SHORT).show();
                    }
                });
                linearLayout5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"5",Toast.LENGTH_SHORT).show();
                    }
                });
                linearLayout6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"6",Toast.LENGTH_SHORT).show();
                    }
                });
                linearLayout7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"7",Toast.LENGTH_SHORT).show();
                    }
                });

            }
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
                    graphicsOverlays.add(graphicsOverlay);
                } else {
                    graphicsOverlays.clear();
                }

                break;
        }
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
        BitmapDrawable pinStarBlueDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.sign);
        final PictureMarkerSymbol pinStarBlueSymbol = new PictureMarkerSymbol(pinStarBlueDrawable);
        //Optionally set the size, if not set the image will be auto sized based on its size in pixels,
        //its appearance would then differ across devices with different resolutions.
        pinStarBlueSymbol.setHeight(40);
        pinStarBlueSymbol.setWidth(40);
        //Optionally set the offset, to align the base of the symbol aligns with the point geometry
        pinStarBlueSymbol.setOffsetY(
                11); //The image used for the symbol has a transparent buffer around it, so the offset is not simply height/2
        pinStarBlueSymbol.loadAsync();
        //[DocRef: END]
        pinStarBlueSymbol.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                for (DisasterPoint disasterPoint : disasterPoints) {
                    Point point = new Point(Double.valueOf(disasterPoint.getDis_lon()), Double.valueOf(disasterPoint.getDis_lat()), SpatialReferences.getWgs84());
                    Graphic graphic = new Graphic(point, pinStarBlueSymbol);
                    graphic.setZIndex(disasterPoint.getId());
                    graphicsOverlay.getGraphics().add(graphic);
                }
            }
        });
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
