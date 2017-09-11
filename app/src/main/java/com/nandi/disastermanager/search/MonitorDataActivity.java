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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.search.entity.ChartData;
import com.nandi.disastermanager.search.entity.MonitorData;
import com.nandi.disastermanager.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private MonitorAdapter monitorAdapter;
    /**
     * 数据
     **/
    private MonitorData mMonitorData;
    private Context mContext;
    private String ID;
    private String startTime = "";
    private String endTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_data);
        ButterKnife.bind(this);
        mContext = this;
        mMonitorData = new MonitorData();
        ID = getIntent().getStringExtra("ID");
        Log.i("TAG", getIntent().getStringExtra("ID"));
        monitorListRequest(getIntent().getStringExtra("ID"));
    }

    private void monitorListRequest(String id) {

        OkHttpUtils.get().url("http://192.168.10.195:8080/gzcmd/detection/findMonitorData/" + id + "/1/10000")
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
                        mMonitorData = gson.fromJson(response, MonitorData.class);
                        if (mMonitorData.getMeta().isSuccess()){
                            setAdapter();

                        }else{
                            ToastUtils.showShort(mContext,mMonitorData.getMeta().getMessage());
                        }

                    }
                });

    }

    private void monitorCurveRequest(String id, String startTime, String endTime) {

        OkHttpUtils.get().url("http://192.168.10.195:8080/gzcmd/tabcollect/findHighcharts/" + id + "/" + startTime + "/" + endTime)
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
                        if (!(chartData.getData().getMONITORDATA().size()==0)){
                            setChartlines(chartData);
                        }
                    }
                });

    }

    private void setAdapter() {

        dateShow.setLayoutManager(new LinearLayoutManager(mContext));
        monitorAdapter = new MonitorAdapter(mContext, mMonitorData);
        monitorAdapter.setOnItemClickListener(new MonitorAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(mContext, MonitorPhotoActivity.class);
                intent.putExtra("ID", mMonitorData.getData().getResult().get(position - 1).getID());
                intent.putExtra("Time", mMonitorData.getData().getResult().get(position - 1).getTime());
                startActivity(intent);
            }
        });
        dateShow.setAdapter(monitorAdapter);

    }

    @OnClick({R.id.data_monitor, R.id.data_curve, R.id.tv_chart_start_time, R.id.tv_chart_end_time, R.id.btn_chart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.data_monitor:
                dateShow.setVisibility(View.VISIBLE);
                llChart.setVisibility(View.GONE);
                dataCurve.setTextColor(Color.GRAY);
                dataMonitor.setTextColor(Color.WHITE);
                break;
            case R.id.data_curve:
                dateShow.setVisibility(View.GONE);
                llChart.setVisibility(View.VISIBLE);
                mLineChart.setNoDataText("请选择时间");
                dataCurve.setTextColor(Color.WHITE);
                dataMonitor.setTextColor(Color.GRAY);
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
                if ("".equals(startTime)&&"".equals(endTime)) {
                    Toast.makeText(mContext,"请选择时间",Toast.LENGTH_SHORT).show();
                }else {
                    monitorCurveRequest(ID, startTime, endTime);
                }
                break;
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public  void setChartlines(ChartData chartData) {
        setLineChartStyle("监测数据(mm)", mLineChart);
        //设置数据
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (List<Float> lists : chartData.getData().getMONITORDATA()) {
            Log.d("limeng",lists.get(0)+"-"+lists.get(1));
            Entry pointEntry = new Entry(lists.get(0), lists.get(1));
            values.add(pointEntry);
        }
        LineDataSet set1;
        set1 = new LineDataSet(values, "监测数据(mm)");
        set1.setCircleColor(Color.GREEN);
        set1.setLineWidth(2f);
        set1.setDrawCircles(false);
        set1.setColor(Color.GREEN);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(false);
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        Log.d("limeng",values.size()+"-"+dataSets.size());
        mLineChart.setData(data);

    }

    /**
     * 设置折线图样式
     * @param text 描述
     * @param mLineChart 折线图控件
     */
    private void setLineChartStyle(String text,LineChart mLineChart) {
        Description description = new Description();
        description.setText("");
        description.setTextSize(19);
        description.setTextAlign(Paint.Align.LEFT);
        description.setPosition(100,100);
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

}
