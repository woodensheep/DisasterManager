package com.nandi.disastermanager.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import com.nandi.disastermanager.MyApplication;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.dao.GreenDaoManager;
import com.nandi.disastermanager.search.entity.ChartData;
import com.nandi.disastermanager.search.entity.DisasterPoint;
import com.nandi.disastermanager.search.entity.MonitorData;
import com.nandi.disastermanager.search.entity.MonitorPhoto;
import com.nandi.disastermanager.search.entity.MonitorPoint;
import com.nandi.disastermanager.utils.Constant;
import com.nandi.disastermanager.utils.SharedUtils;
import com.nandi.disastermanager.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.nandi.disastermanager.R.id.chart;

public class MonitorDataActivity extends Activity {

    @BindView(R.id.date_show)
    RecyclerView dateShow;
    @BindView(R.id.data_monitor)
    TextView dataMonitor;
    @BindView(R.id.data_curve)
    TextView dataCurve;
    @BindView(R.id.tv_chart_start_time)
    TextView tvChartStartTime;
    @BindView(R.id.tv_chart_end_time)
    TextView tvChartEndTime;
    @BindView(R.id.ll_chart)
    LinearLayout llChart;
    @BindView(R.id.btn_chart)
    Button btnChart;
    @BindView(chart)
    LineChart mLineChart;
    @BindView(R.id.monitor_data1)
    LinearLayout monitorData1;
    private Context mContext;
    private String ID;
    private String startTime = "";
    private String endTime = "";
    private MonitorPhoto monitorPhoto;
    //点的数据
    private List<List<Double>> lists = new ArrayList<>();
    private MonitorAdapter monitorAdapter;
    private List<MonitorPoint> monitorPoints;
    private String name;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_data);
        ButterKnife.bind(this);
        mContext = this;
        MyApplication.getActivities().add(this);
        name = (String) SharedUtils.getShare(this, Constant.USER_NAME, "");
        password = (String) SharedUtils.getShare(this, Constant.PASSWORD, "");
        setAdapter();
    }

    private void setAdapter() {
        ID = getIntent().getStringExtra("ID");

        monitorPoints = GreenDaoManager.queryMonitorData(ID);
        if (monitorPoints.size() == 0) {
            ToastUtils.showShort(mContext, "当前没有监测点数据");
        } else {
            dateShow.setLayoutManager(new LinearLayoutManager(mContext));
            monitorAdapter = new MonitorAdapter(mContext, monitorPoints);
            monitorAdapter.setOnItemClickListener(new MonitorAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    String id = monitorPoints.get(position).getMonitorId();
                    String time = monitorPoints.get(position).getTime();
                    downloadPhoto(id, time);
                }
            });
            dateShow.setAdapter(monitorAdapter);
        }

    }

    /**
     * 登录请求
     */

    private void loginPost() {
        OkHttpUtils.get().url(getString(R.string.base_gz_url) + "/appdocking/login/" + name + "/" + password + "/2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showShort(mContext, "获取权限失败");
                    }

                    @Override
                    public void onResponse(final String response, int id) {

                    }
                });

    }

    private void monitorCurveRequest(String id, String startTime, String endTime) {
        loginPost();
        OkHttpUtils.get().url(getString(R.string.base_gz_url) + "/tabcollect/findHighcharts/" + id + "/" + startTime + "/" + endTime)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.i("qingsong", response);
                        Gson gson = new Gson();
                        ChartData chartData = gson.fromJson(response, ChartData.class);
                        if (!(chartData.getData().getMONITORDATA().size() == 0)) {
                            setChartlines(chartData);
                        }
                    }
                });

    }

    private void downloadPhoto(String id, String time) {
        loginPost();
        OkHttpUtils.get().url(getString(R.string.base_gz_url) + "/detection/findPhoto/" + id + "/" + time)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("qingsong", response);
                        Gson gson = new Gson();
                        monitorPhoto = gson.fromJson(response, MonitorPhoto.class);
                        if (monitorPhoto.getMeta().isSuccess()) {
                            if (monitorPhoto.getData().size() == 0) {
                                ToastUtils.showLong(mContext, "当前监测点没有图片");
                                return;
                            }
                            Intent intent = new Intent(mContext, MonitorPhotoActivity.class);
                            intent.putExtra("MONITOR_PHOTO", monitorPhoto);
                            startActivity(intent);
                        } else {
                            ToastUtils.showShort(mContext, monitorPhoto.getMeta().getMessage());
                        }

                    }
                });

    }


    @OnClick({R.id.data_monitor, R.id.data_curve, R.id.tv_chart_start_time, R.id.tv_chart_end_time, R.id.btn_chart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.data_monitor:
                monitorData1.setVisibility(View.VISIBLE);
                llChart.setVisibility(View.GONE);
                dataCurve.setTextColor(Color.WHITE);
                dataMonitor.setTextColor(Color.parseColor("#00f3f3"));
                break;
            case R.id.data_curve:
                Log.d("cp", "开始时间：" + getStartTime() + "结束时间：" + getTime(new Date()));
                monitorCurveRequest(ID, getStartTime(), getTime(new Date()));
                monitorData1.setVisibility(View.GONE);
                llChart.setVisibility(View.VISIBLE);
                mLineChart.setNoDataText("请选择时间");
                dataCurve.setTextColor(Color.parseColor("#00f3f3"));
                dataMonitor.setTextColor(Color.WHITE);
                break;
            case R.id.tv_chart_start_time:
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        tvChartStartTime.setText(getTime(date));
                        startTime = getTime(date);
                    }
                }).setSubmitText("确定")
                        .setCancelText("取消")
                        .build();
                //pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                break;
            case R.id.tv_chart_end_time:
                //时间选择器
                TimePickerView pvTime2 = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        tvChartEndTime.setText(getTime(date));
                        endTime = getTime(date);
                    }
                }).setSubmitText("确定")
                        .setCancelText("取消")
                        .build();
                //pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime2.show();
                break;
            case R.id.btn_chart:
                if ("".equals(startTime) && "".equals(endTime)) {
                    Toast.makeText(mContext, "请选择时间", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("cp", "start:" + startTime + "end:" + endTime);
                    monitorCurveRequest(ID, startTime, endTime);
                }
                break;
        }
    }

    private String getStartTime() {
        Date date = new Date();//获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间
        Date time = calendar.getTime();
        return getTime(time);
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public void setChartlines(ChartData chartData) {

        //设置数据
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//        for (List<Double> lists : chartData.getData().getMONITORDATA()) {
//            Log.d("limeng", lists.get(0) + "-" + lists.get(1));
//            Entry pointEntry = new Entry(lists.get(0)/1000, lists.get(1));
//
//            values.add(pointEntry);
//        }
        lists.clear();
        lists = chartData.getData().getMONITORDATA();
        for (int i = 0; i < chartData.getData().getMONITORDATA().size(); i++) {
            Entry pointEntry = new Entry((float) i, Float.parseFloat(lists.get(i).get(1).toString()));
            Log.i("qingsong", pointEntry.getX() + "-" + pointEntry.getY());
            values.add(pointEntry);
        }
        setLineChartStyle("监测数据(mm)", mLineChart);
        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view, lists);
        mv.setChartView(mLineChart); // For bounds control
        mLineChart.setMarker(mv); // Set the marker to the chart
        LineDataSet set1;
        set1 = new LineDataSet(values, "监测数据(mm)");
        set1.setCircleColor(Color.GREEN);
        set1.setLineWidth(1.5f);
        set1.setCircleRadius(2.5f);
        set1.setDrawCircles(true);
        set1.setColor(Color.GREEN);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(false);
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        Log.d("limeng", values.size() + "-" + dataSets.size());
        mLineChart.clear();
        mLineChart.setData(data);

    }

    /**
     * 设置折线图样式
     *
     * @param text       描述
     * @param mLineChart 折线图控件
     */
    private void setLineChartStyle(String text, LineChart mLineChart) {
        Description description = new Description();
        description.setText("");
        description.setTextSize(19);
        description.setTextAlign(Paint.Align.LEFT);
        description.setPosition(100, 100);
        mLineChart.setDescription(description);
        mLineChart.setNoDataText("暂无数据");
        //给X轴属性
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setAvoidFirstLastClipping(true);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        //设置x轴显示标签数量  还有一个重载方法第二个参数为布尔值强制设置数量 如果启用会导致绘制点出现偏差
        if (lists.size() >= 6) {
            xAxis.setLabelCount(6);
        } else {
            xAxis.setLabelCount(lists.size());
        }
        xAxis.setTextColor(Color.BLACK);//设置轴标签的颜色
        xAxis.setTextSize(16f);//设置轴标签的大小
        xAxis.setAxisLineWidth(3f);//设置x轴线宽度
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final Date date = new Date();
        //设置x轴标签格式化显示数据
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Log.i("limeng", lists.size() + "-value:" + value);
                if (value < 0 || value >= lists.size()) {
                    return "";
                }
                double time = lists.get((int) value).get(0);
                date.setTime(Long.parseLong(new BigDecimal(time).toPlainString()));
                String s = simpleDateFormat.format(date);
                //Log.d("limeng",Long.parseLong(new BigDecimal(value).toPlainString())+"-"+s);
                return s;
            }

        });
        //点击点监听
        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });

        //Y轴设置
        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawLabels(true);
        leftAxis.setTextColor(Color.BLACK);//设置轴标签的颜色
        leftAxis.setTextSize(13f);//设置轴标签的大小
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

}
