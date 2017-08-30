package com.nandi.disastermanager.http;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.nandi.disastermanager.R;
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
import com.nandi.disastermanager.entity.SingleEquipment;
import com.nandi.disastermanager.ui.WaitingDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import okhttp3.Call;

import static com.nandi.disastermanager.R.id.chart;

/**
 * Created by lemon on 2017/6/22.
 * 设备相关网络请求
 */

public class EquipmentOkhttp {
    public static final int[] Colors = {Color.GREEN, Color.YELLOW, Color.RED, Color.BLUE, Color.BLACK,
            Color.GRAY, Color.CYAN,Color.DKGRAY,0x111111,0x122131,0x231312,0x342121,0x341275};
    public static final int ALL_EQUIPMENT = 20;
    public static final int SINGLE_EQUIPMENT = 21;
    public static final int SINGLE_EQUIPMENT_DETAIL = 22;

    /**
     * 请求所有设备
     *
     * @param context
     * @param areaId  区县id
     */
    public static void getAllEquipment(final Context context, String areaId, final Handler handler) {
        WaitingDialog.createLoadingDialog(context, "正在请求");
        OkHttpUtils.get().url(context.getResources().getString(R.string.base_http) +
                context.getResources().getString(R.string.all_devices) + areaId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        WaitingDialog.closeDialog();
                        Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        WaitingDialog.closeDialog();
                        Gson gson = new Gson();
                        EquipmentLocation equipmentLocation = gson.fromJson(response, EquipmentLocation.class);
                        Message msg = Message.obtain();
                        msg.obj = equipmentLocation;
                        msg.what = ALL_EQUIPMENT;
                        handler.sendMessage(msg);
                    }
                });

    }

    /**
     * 请求单个设备
     *
     * @param context
     * @param id      设备id
     */
    public static void getSingleEquipment(final Context context, int id, final Handler handler) {
        WaitingDialog.createLoadingDialog(context, "正在请求");
        OkHttpUtils.get().url(context.getResources().getString(R.string.base_http) +
                context.getResources().getString(R.string.single_devices) + id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        WaitingDialog.closeDialog();
                        Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        WaitingDialog.closeDialog();
                        Gson gson = new Gson();
                        SingleEquipment singleEquipment = gson.fromJson(response, SingleEquipment.class);
                        Message msg = Message.obtain();
                        msg.obj = singleEquipment;
                        msg.what = SINGLE_EQUIPMENT;
                        handler.sendMessage(msg);
                    }
                });

    }

    /**
     * 请求单个设备1,3,22数据可能有问题
     *
     * @param context
     */
    public static void getSingleEquipmentDetail(final Context context, String deviceNo, final String type, final Handler handler) {
        String http = null;
        switch (type) {
            case "1":
                http = "/monitor/equipmentchart/" + deviceNo + "/undefined";
                break;
            case "3":
                http = "/monitor/chartyl/" + deviceNo;
                break;
            case "4":
                http = "/pjInstrument/shenyaji/" + deviceNo + "/undefined";
                break;
            case "6":
                http = "/deepqx/findQingXieYisb/" + deviceNo + "/undefined";
                break;
            case "9":
                http = "/pjInstrument/groundwater/" + deviceNo + "/undefined";
                break;
            case "10":
                http = "/pjInstrument/waterRate/" + deviceNo + "/undefined";
                break;
            case "11":
                http = "/monitor/laxiandbd/" + deviceNo + "/undefined";
                break;
            case "12":
                http = "/deepqx/findKuShuiWei/" + deviceNo;
                break;
            case "14":
                http = "/monitor/crackgauge/" + deviceNo + "/undefined";
                break;
            case "15":
                http = "/pjInstrument/totalstation/" + deviceNo + "/undefined";
                break;
            case "17":
                http = "/pjInstrument/rengongshuizhun/" + deviceNo + "/undefined";
                break;
            case "16":
                http = "/pjInstrument/slopeDevice/" + deviceNo + "/undefined";
                break;
            case "19":
                http = "/monitor/laxiansb/" + deviceNo + "/undefined";
                break;
            case "20":
                http = "/deepqx/findDeepCXCurve/" + deviceNo + "/undefined";
                break;
            case "21":
                http = "/deepqx/findJiGuangdb/" + deviceNo + "/undefined";
                break;
            case "22":
                http = "/monitor/tuYalilicurve/" + deviceNo;
                break;
            case "23":
                http = "/pjInstrument/ceFeiCurve/" + deviceNo;
                break;
            case "24":
                http = "/pjInstrument/ceFeiCurve/" + deviceNo;
                break;
            case "25":
                http = "/monitor/qingjiaocurve/" + deviceNo + "/undefined";
                break;
            case "26":
                http = "/monitor/stresscurve/" + deviceNo + "/undefined";
                break;
        }
        WaitingDialog.createLoadingDialog(context, "正在请求");
        OkHttpUtils.get().url(context.getResources().getString(R.string.base_http) +
                http)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        WaitingDialog.closeDialog();
                        Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        WaitingDialog.closeDialog();
                        Message msg = Message.obtain();
                        msg.obj = response;
                        msg.arg1 = Integer.parseInt(type);
                        msg.what = SINGLE_EQUIPMENT_DETAIL;
                        handler.sendMessage(msg);
                    }
                });

    }

    public static void getType1lines(Context context, EquipmentType1 equipmentType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_charts, null);
        LineChart mLineChart = (LineChart) view.findViewById(chart);
       setLineChartStyle("位移(mm)",mLineChart);
        //设置数据
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (List<Float> lists : equipmentType.getData().getSwy()) {
            Entry pointEntry = new Entry(lists.get(0), lists.get(1));
            values.add(pointEntry);
        }
        LineDataSet set1;
        set1 = new LineDataSet(values, "水平位移");
        set1.setCircleColor(Colors[0]);
        set1.setLineWidth(2f);
        set1.setDrawCircles(false);
        set1.setColor(Colors[0]);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(false);
        dataSets.add(set1);
        values = new ArrayList<>();
        for (List<Float> lists : equipmentType.getData().getX1()) {
            Entry pointEntry = new Entry(lists.get(0), lists.get(1));
            values.add(pointEntry);
        }
        LineDataSet set2;
        set2 = new LineDataSet(values, "X位移");
        set2.setCircleColor(Colors[1]);
        set2.setLineWidth(2f);
        set2.setDrawCircles(false);
        set2.setColor(Colors[1]);
        set2.setDrawCircleHole(false);
        set2.setValueTextSize(9f);
        set2.setDrawFilled(false);
        dataSets.add(set2);
        values = new ArrayList<>();
        for (List<Float> lists : equipmentType.getData().getY1()) {
            Entry pointEntry = new Entry(lists.get(0), lists.get(1));
            values.add(pointEntry);
        }
        LineDataSet set3;
        set3 = new LineDataSet(values, "Y位移");
        set3.setCircleColor(Colors[2]);
        set3.setLineWidth(2f);
        set3.setDrawCircles(false);
        set3.setColor(Colors[2]);
        set3.setDrawCircleHole(false);
        set3.setValueTextSize(9f);
        set3.setDrawFilled(false);
        dataSets.add(set3);

        LineData data = new LineData(dataSets);
        mLineChart.setData(data);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        lp.height = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
    }


    public static void getType3lines(Context context, EquipmentType3 equipmentType) {
        if (null == equipmentType.getData()) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.activity_charts, null);
        LineChart mLineChart = (LineChart) view.findViewById(chart);
       setLineChartStyle("位移(mm)",mLineChart);
        //设置数据
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (List<Float> lists : equipmentType.getData().getX()) {
            Entry pointEntry = new Entry(lists.get(0), lists.get(1));
            values.add(pointEntry);
        }
        LineDataSet set1;
        set1 = new LineDataSet(values, "X位移");
        set1.setCircleColor(Colors[1]);
        set1.setLineWidth(2f);
        set1.setDrawCircles(false);
        set1.setColor(Colors[1]);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(false);
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        mLineChart.setData(data);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        lp.height = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
    }

    public static void getType11lines(Context context, EquipmentType11 equipmentType) {
        if (null == equipmentType.getData()) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.activity_charts, null);
        LineChart mLineChart = (LineChart) view.findViewById(chart);
       setLineChartStyle("位移(mm)",mLineChart);
        //设置数据
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (List<Float> lists : equipmentType.getData().getLjwy()) {
            Entry pointEntry = new Entry(lists.get(0), lists.get(1));
            values.add(pointEntry);
        }
        LineDataSet set1;
        set1 = new LineDataSet(values, "累计位移");
        set1.setCircleColor(Colors[1]);
        set1.setLineWidth(2f);
        set1.setDrawCircles(false);
        set1.setColor(Colors[1]);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(false);
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        mLineChart.setData(data);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        lp.height = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
    }

    public static void getType14lines(Context context, EquipmentType14 equipmentType) {
        if (null == equipmentType.getData()) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.activity_charts, null);
        LineChart mLineChart = (LineChart) view.findViewById(chart);


       setLineChartStyle("位移(mm)",mLineChart);
        //设置数据
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (List<Float> lists : equipmentType.getData().getData()) {
            Entry pointEntry = new Entry(lists.get(0), lists.get(1));
            values.add(pointEntry);
        }
        LineDataSet set1;
        set1 = new LineDataSet(values, "位移");
        set1.setCircleColor(Colors[1]);
        set1.setLineWidth(2f);
        set1.setDrawCircles(false);
        set1.setColor(Colors[1]);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(false);
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        mLineChart.setData(data);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        lp.height = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
    }


    public static void getType19lines(Context context, EquipmentType19 equipmentType) {
        if (null == equipmentType.getData()) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.activity_charts, null);
        LineChart mLineChart = (LineChart) view.findViewById(chart);


       setLineChartStyle("位移(mm)",mLineChart);
        //设置数据
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (List<Float> lists : equipmentType.getData().getLjwy()) {
            Entry pointEntry = new Entry(lists.get(0), lists.get(1));
            values.add(pointEntry);
        }
        LineDataSet set1;
        set1 = new LineDataSet(values, "累计位移");
        set1.setCircleColor(Colors[1]);
        set1.setLineWidth(2f);
        set1.setDrawCircles(false);
        set1.setColor(Colors[1]);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(false);
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        mLineChart.setData(data);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        lp.height = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
    }


    /**
     * Y轴位移(mm)
     * @param context
     * @param equipmentType
     */
    public static void getType20lines(Context context, EquipmentType20 equipmentType) {
        if (null == equipmentType.getData()) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.activity_charts, null);
        LineChart mLineChart = (LineChart) view.findViewById(chart);


       setLineChartStyle("位移(mm)",mLineChart);
        //设置数据
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (List<Float> lists : equipmentType.getData().getX()) {
            Entry pointEntry = new Entry(lists.get(0), lists.get(1));
            values.add(pointEntry);
        }
        LineDataSet set1;
        set1 = new LineDataSet(values, "X位移");
        set1.setCircleColor(Colors[0]);
        set1.setLineWidth(2f);
        set1.setDrawCircles(false);
        set1.setColor(Colors[0]);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(false);
        dataSets.add(set1);
        values=new ArrayList<>();
        for (List<Float> lists : equipmentType.getData().getY()) {
            Entry pointEntry = new Entry(lists.get(0), lists.get(1));
            values.add(pointEntry);
        }
        LineDataSet set2;
        set2= new LineDataSet(values, "Y位移");
        set2.setCircleColor(Colors[1]);
        set2.setLineWidth(2f);
        set2.setDrawCircles(false);
        set2.setColor(Colors[1]);
        set2.setDrawCircleHole(false);
        set2.setValueTextSize(9f);
        set2.setDrawFilled(false);
        dataSets.add(set2);
        LineData data = new LineData(dataSets);
        mLineChart.setData(data);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        lp.height = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
    }

    public static void getType22lines(Context context, EquipmentType22 equipmentType) {
        if (null == equipmentType.getData()) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.activity_charts, null);
        LineChart mLineChart = (LineChart) view.findViewById(chart);

       setLineChartStyle("N/KN",mLineChart);
        //设置数据
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < equipmentType.getData().getData().size(); i++) {
            for (List<Float> lists : equipmentType.getData().getData()) {
                Entry entry=new Entry(lists.get(0),lists.get(1));
                values.add(entry);
            }
            LineDataSet set1;
            set1 = new LineDataSet(values,"");
            Random random = new Random();
            //set1.setCircleColor(Color.argb(1, random.nextInt(255) / 255, random.nextInt(255) / 255, random.nextInt(255) / 255));
            set1.setLineWidth(2f);
            set1.setDrawCircles(false);
            set1.setColor(Color.argb(1, random.nextInt(255) / 255, random.nextInt(255) / 255, random.nextInt(255) / 255));
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(false);
            dataSets.add(set1);
            values = new ArrayList<>();
        }

        LineData data = new LineData(dataSets);
        mLineChart.setData(data);


        AlertDialog dialog = new AlertDialog.Builder(context)
                .show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        lp.height = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
    }


    /**
     * type值为23的设备折线信息显示
     *
     * @param context
     * @param equipmentType
     */
    public static void getType23lines(final Context context, EquipmentType23 equipmentType) {
        if (null == equipmentType.getData()) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.activity_charts, null);
        LineChart mLineChart = (LineChart) view.findViewById(chart);
       setLineChartStyle("位移(mm)",mLineChart);
        //设置数据
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < equipmentType.getData().getData().size(); i++) {
            for (List<Float> lists : equipmentType.getData().getData().get(i).getData()) {
                Entry pointEntry = new Entry(lists.get(0), lists.get(1));
                values.add(pointEntry);
            }
            LineDataSet set1;
            set1 = new LineDataSet(values, equipmentType.getData().getData().get(i).getParaId());
            set1.setCircleColor(Colors[i]);
            set1.setLineWidth(2f);
            set1.setDrawCircles(false);
            set1.setColor(Colors[i]);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(false);
            dataSets.add(set1);
            values = new ArrayList<>();
        }
        LineData data = new LineData(dataSets);
        mLineChart.setData(data);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        lp.height = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
    }


    /**
     * 设置折线图样式
     * @param text 描述
     * @param mLineChart 折线图控件
     */
    private static void setLineChartStyle(String text,LineChart mLineChart) {
        Description description = new Description();
        description.setText(text);
        description.setTextSize(19);
        description.setTextAlign(Paint.Align.LEFT);
        description.setPosition(100,100);
        mLineChart.setDescription(description);
        //给X轴属性
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setAvoidFirstLastClipping(true);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        //设置x轴显示标签数量  还有一个重载方法第二个参数为布尔值强制设置数量 如果启用会导致绘制点出现偏差
        xAxis.setLabelCount(10);
        xAxis.setTextColor(Color.BLACK);//设置轴标签的颜色
        xAxis.setTextSize(16f);//设置轴标签的大小
        xAxis.setAxisLineWidth(3f);//设置x轴线宽度
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        final Date date = new Date();
        //设置x轴标签格式化显示数据
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                date.setTime(Long.parseLong(new BigDecimal(value).toPlainString()));
                String s = simpleDateFormat.format(date);
                return s;
            }
        });
        //Y轴设置
        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawLabels(true);
        leftAxis.setTextColor(Color.BLACK);//设置轴标签的颜色
        leftAxis.setTextSize(18f);//设置轴标签的大小
        leftAxis.setAxisLineWidth(3f);//设置x轴线宽度
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setDrawZeroLine(false);
        leftAxis.setSpaceBottom(20);    //Y轴坐标距底有多少距离，即留白
        mLineChart.getAxisRight().setEnabled(false);
        //设置动画和标签的样式
        mLineChart.animateX(2500);
        Legend l = mLineChart.getLegend();
        l.setTextSize(18);
        l.setForm(Legend.LegendForm.LINE);
        l.setWordWrapEnabled(true);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(20);


    }


    public static void getType25lines(Context context, EquipmentType25 equipmentType) {
        if (null == equipmentType.getData()) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.activity_charts, null);
        LineChart mLineChart = (LineChart) view.findViewById(chart);


       setLineChartStyle("位移(mm)",mLineChart);
        //设置数据
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (List<Float> lists : equipmentType.getData().getX()) {
            Entry pointEntry = new Entry(lists.get(0), lists.get(1));
            values.add(pointEntry);
        }
        LineDataSet set1;
        set1 = new LineDataSet(values, "X位移");
        set1.setCircleColor(Colors[0]);
        set1.setLineWidth(2f);
        set1.setDrawCircles(false);
        set1.setColor(Colors[0]);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(false);
        dataSets.add(set1);
        values=new ArrayList<>();
        for (List<Float> lists : equipmentType.getData().getY()) {
            Entry pointEntry = new Entry(lists.get(0), lists.get(1));
            values.add(pointEntry);
        }
        LineDataSet set2;
        set2 = new LineDataSet(values, "Y位移");
        set2.setCircleColor(Colors[1]);
        set2.setLineWidth(2f);
        set2.setDrawCircles(false);
        set2.setColor(Colors[1]);
        set2.setDrawCircleHole(false);
        set2.setValueTextSize(9f);
        set2.setDrawFilled(false);
        dataSets.add(set2);
        LineData data = new LineData(dataSets);
        mLineChart.setData(data);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        lp.height = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
    }

    public static void getType26lines(Context context, EquipmentType26 equipmentType) {
        if (null == equipmentType.getData()) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.activity_charts, null);
        LineChart mLineChart = (LineChart) view.findViewById(chart);


       setLineChartStyle("K/KN",mLineChart);
        //设置数据
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (List<Float> lists : equipmentType.getData().getValue()) {
            Entry pointEntry = new Entry(lists.get(0), lists.get(1));
            values.add(pointEntry);
        }
        LineDataSet set1;
        set1 = new LineDataSet(values, "");
        set1.setCircleColor(Colors[0]);
        set1.setLineWidth(2f);
        set1.setDrawCircles(false);
        set1.setColor(Colors[0]);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(false);
        dataSets.add(set1);

        LineData data = new LineData(dataSets);
        mLineChart.setData(data);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        lp.height = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
    }


}
