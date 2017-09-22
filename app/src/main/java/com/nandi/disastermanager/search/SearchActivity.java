package com.nandi.disastermanager.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.nandi.disastermanager.MyApplication;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.dao.GreenDaoManager;
import com.nandi.disastermanager.search.entity.AreaInfo;
import com.nandi.disastermanager.search.entity.DisasterPoint;
import com.nandi.disastermanager.search.entity.LoginInfo;
import com.nandi.disastermanager.utils.InputUtil;
import com.nandi.disastermanager.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nandi.disastermanager.R.id.date_show;


public class SearchActivity extends Activity {

    @BindView(R.id.sp_1)
    Spinner sp1;
    @BindView(R.id.sp_2)
    Spinner sp2;
    @BindView(R.id.sp_3)
    Spinner sp3;
    @BindView(R.id.sp_4)
    Spinner sp4;
    @BindView(R.id.sp_5)
    Spinner sp5;
    @BindView(R.id.sp_6)
    Spinner sp6;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(date_show)
    RecyclerView dateShow;
    @BindView(R.id.tv_total_number)
    TextView tvTotalNumber;
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
    @BindView(R.id.tv_da_number)
    TextView tvDaNumber;
    @BindView(R.id.tv_zhong_number)
    TextView tvZhongNumber;
    @BindView(R.id.tv_xiao_number)
    TextView tvXiaoNumber;
    @BindView(R.id.ll_statics)
    LinearLayout llStatics;

    private Context context;
    /**
     * 数据
     **/
    private List<DisasterPoint> mDisasterPoints = new ArrayList<>();
    /**
     * recycleView适配器
     **/
    RcSearchAdapter rcSearchAdapter;
    private LoginInfo loginInfo;
    private String disasterCode = "";
    private String city = "";
    private int cityNum;
    private String county = "";
    private int countyNum;
    private String town = "";
    private String threatLevel = "";
    private String type = "";
    private String inducement = "";
    private String disasterName = "";
    private List<String> mItems4 = new ArrayList<>();
    private String[] mItems5 = new String[10];
    private List<String> mItems6 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_gz);
        ButterKnife.bind(this);
        context = this;
        MyApplication.getActivities().add(this);
        getListType();
    }

    private void initViews() {
        List<String> mItems1 = new ArrayList<>();
        final List<String> mItems2 = new ArrayList<>();
        final List<String> mItems3 = new ArrayList<>();
        mItems5 = getResources().getStringArray(R.array.search_type_5);
        cityNum = GreenDaoManager.queryAreaLevel(2).size();
        countyNum = GreenDaoManager.queryAreaLevel(3).size();
        if (cityNum == 1) {
            city = GreenDaoManager.queryAreaLevel(2).get(0).getName();
        } else {
            mItems1.add("选择州市");
        }
        for (AreaInfo areaInfo : GreenDaoManager.queryAreaLevel(2)) {
            mItems1.add(areaInfo.getName());
        }
        Log.d("limeng", "GreenDaoManager.queryAreaLevel(3).size()=" + GreenDaoManager.queryAreaLevel(3).size());
        if (countyNum == 1) {
            county = GreenDaoManager.queryAreaLevel(3).get(0).getName();
        } else {
            mItems2.add("选择区县");
        }
        for (AreaInfo areaInfo : GreenDaoManager.queryAreaLevel(3)) {
            mItems2.add(areaInfo.getName());
        }
        mItems3.add("选择乡镇");
        // 建立Adapter并且绑定数据源
        Log.d("cp1", mItems1.toString() + "/" + mItems2.toString() + "/" + mItems3.toString());
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems1);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems2);
        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems3);
        final ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems4);
        final ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems5);
        final ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems6);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        sp1.setAdapter(adapter1);
        sp2.setAdapter(adapter2);
        sp3.setAdapter(adapter3);
        sp4.setAdapter(adapter4);
        sp5.setAdapter(adapter5);
        sp6.setAdapter(adapter6);
        Log.d("cp2", mItems1.toString() + "/" + mItems2.toString() + "/" + mItems3.toString());
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!"选择州市".equals(adapter1.getItem(position))) {
                    String name = adapter1.getItem(position);
                    city = name;
                    county = "";
                    town = "";
                    int code = GreenDaoManager.queryArea2(name).get(0).getArea_id();
                    mItems2.clear();
                    mItems2.add("选择区县");
                    if (countyNum == 1) {
                        mItems2.remove("选择区县");
                    }
                    for (AreaInfo areaInfo : GreenDaoManager.queryAreaLevel(3, code)) {
                        mItems2.add(areaInfo.getName());
                    }
                    adapter2.notifyDataSetChanged();
                    sp2.setSelection(0);
                    mItems3.clear();
                    mItems3.add("选择乡镇");
                    adapter3.notifyDataSetChanged();
                } else {
                    city = "";
                    county = "";
                    town = "";
                    mItems2.clear();
                    mItems2.add("选择区县");
                    mItems3.clear();
                    mItems3.add("选择乡镇");
                    adapter2.notifyDataSetChanged();
                    adapter3.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!"选择区县".equals(adapter2.getItem(position))) {
                    String name = adapter2.getItem(position);
                    county = name;
                    town = "";
                    int code = GreenDaoManager.queryArea3(name).get(0).getArea_id();
                    mItems3.clear();
                    mItems3.add("选择乡镇");
                    for (AreaInfo areaInfo : GreenDaoManager.queryAreaLevel(4, code)) {
                        mItems3.add(areaInfo.getName());
                    }
                    adapter3.notifyDataSetChanged();
                    sp3.setSelection(0);
                } else {
                    county = "";
                    town = "";
                    mItems3.clear();
                    mItems3.add("选择乡镇");
                    adapter3.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!"选择乡镇".equals(adapter3.getItem(position))) {
                    town = adapter3.getItem(position);
                } else {
                    town = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!"选择等级".equals(adapter4.getItem(position))) {
                    threatLevel = adapter4.getItem(position);
                } else {
                    threatLevel = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sp5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!"选择类型".equals(adapter5.getItem(position))) {
                    type = adapter5.getItem(position);
                } else {
                    type = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!"选择诱因".equals(adapter6.getItem(position))) {
                    inducement = adapter6.getItem(position);
                } else {
                    inducement = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dateShow.setLayoutManager(new LinearLayoutManager(this));
        rcSearchAdapter = new RcSearchAdapter(this, mDisasterPoints);
        rcSearchAdapter.setOnItemClickListener(new RcSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Long id = (Long) view.getTag();
                if (id == -1) {
                    return;
                }
            }
        });
        dateShow.setAdapter(rcSearchAdapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disasterName = etSearch.getText().toString().trim();
                mDisasterPoints.clear();
                List<DisasterPoint> disasterPoints = GreenDaoManager.queryDisasterList(city, county, town, threatLevel, type, inducement, disasterName);
                Log.d("limeng", "onClick" + disasterPoints.size());
                if (disasterPoints.size() == 0) {
                    ToastUtils.showShort(context, "无符合条件数据");
                    llStatics.setVisibility(View.GONE);
                    return;
                }
                llStatics.setVisibility(View.VISIBLE);
                setStatics(disasterPoints);
                mDisasterPoints.addAll(disasterPoints);
                rcSearchAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setStatics(List<DisasterPoint> disasterPoints) {
        int total=disasterPoints.size(), huapo = 0, bengta = 0, nishiliu = 0, diliefeng = 0, xiepo = 0, teda = 0, da = 0, zhong = 0, xiao = 0, taxian = 0;
        for (DisasterPoint disasterPoint : disasterPoints) {
            if ("滑坡".equals(disasterPoint.getDisasterType())) {
                huapo++;
            } else if ("地面塌陷".equals(disasterPoint.getDisasterType())) {
                taxian++;
            } else if ("泥石流".equals(disasterPoint.getDisasterType())) {
                nishiliu++;
            } else if ("地裂缝".equals(disasterPoint.getDisasterType())) {
                diliefeng++;
            } else if ("不稳定斜坡".equals(disasterPoint.getDisasterType())) {
                xiepo++;
            } else if ("崩塌".equals(disasterPoint.getDisasterType())) {
                bengta++;
            }
            if ("特大型".equals(disasterPoint.getDisasterGrade())) {
                teda++;
            } else if ("大型".equals(disasterPoint.getDisasterGrade())) {
                da++;
            } else if ("中型".equals(disasterPoint.getDisasterGrade())) {
                zhong++;
            } else if ("小型".equals(disasterPoint.getDisasterGrade())) {
                xiao++;
            }
        }
        tvTotalNumber.setText(total+"");
        tvHuapoNumber.setText(huapo+"");
        tvTaxianNumber.setText(taxian+"");
        tvNishiliuNumber.setText(nishiliu+"");
        tvDiliefengNumber.setText(diliefeng+"");
        tvXiepoNumber.setText(xiepo+"");
        tvBengtaNumber.setText(bengta+"");
        tvTedaNumber.setText(teda+"");
        tvDaNumber.setText(da+"");
        tvZhongNumber.setText(zhong+"");
        tvXiaoNumber.setText(xiao+"");
    }
    private void getListType() {

        mItems4.clear();
        mItems4.add("选择等级");
        for (int i = 0; i < GreenDaoManager.getDistinct(1).size(); i++) {
            mItems4.add(GreenDaoManager.getDistinct(1).get(i));
        }
        mItems6.clear();
        mItems6.add("选择诱因");
        for (int i = 0; i < GreenDaoManager.getDistinct(0).size(); i++) {
            mItems6.add(GreenDaoManager.getDistinct(0).get(i));
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (InputUtil.isShouldHideInput(v, ev)) {
                InputUtil.hideSoftInput(v.getWindowToken(), context);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
