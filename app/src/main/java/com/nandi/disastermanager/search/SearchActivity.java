package com.nandi.disastermanager.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.dao.GreenDaoManager;
import com.nandi.disastermanager.search.entity.AreaInfo;
import com.nandi.disastermanager.search.entity.DisasterPoint;
import com.nandi.disastermanager.search.entity.ListType;
import com.nandi.disastermanager.search.entity.LoginInfo;
import com.nandi.disastermanager.ui.WaitingDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

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
    private String disasterCode="";
    private String city="";
    private String county="";
    private String town="";
    private String threatLevel="";
    private String type="";
    private String inducement="";
    private String disasterName="";
    private List<String> mItems4=new ArrayList<>();
    private String[] mItems5=new String[10];
    private List<String> mItems6=new ArrayList<>();;
    private ListType listType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_gz);
        ButterKnife.bind(this);
        context = this;
        getlistType();

    }


    private void initViews() {
        List<String> mItems1 = new ArrayList<>();
        final List<String> mItems2 = new ArrayList<>();
        final List<String> mItems3 = new ArrayList<>();
         mItems5 = getResources().getStringArray(R.array.search_type_5);
        mItems1.add("筛查州市");
        for (AreaInfo areaInfo : GreenDaoManager.queryAreaLevel(2)) {
            mItems1.add(areaInfo.getName());
        }
        mItems2.add("筛查区县");
        mItems3.add("筛查乡镇");
        // 建立Adapter并且绑定数据源
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
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!"筛查州市".equals(adapter1.getItem(position))) {
                    String name = adapter1.getItem(position);
                    city= name;
                    county="";
                    town="";
                    int code = GreenDaoManager.queryArea2(name).get(0).getArea_id();
                    mItems2.clear();
                    mItems2.add("筛查区县");
                    for (AreaInfo areaInfo : GreenDaoManager.queryAreaLevel(3, code)) {
                        mItems2.add(areaInfo.getName());
                    }
                    adapter2.notifyDataSetChanged();
                    mItems3.clear();
                    mItems3.add("筛查乡镇");
                    adapter3.notifyDataSetChanged();
                }else {
                    city="";
                    county="";
                    town="";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!"筛查区县".equals(adapter2.getItem(position))) {
                    String name = adapter2.getItem(position);
                    county=name;
                    town="";
                    int code = GreenDaoManager.queryArea3(name).get(0).getArea_id();
                    mItems3.clear();
                    mItems3.add("筛查乡镇");
                    for (AreaInfo areaInfo : GreenDaoManager.queryAreaLevel(4, code)) {
                        mItems3.add(areaInfo.getName());
                    }
                    adapter3.notifyDataSetChanged();

                }else {
                    county="";
                    town="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!"筛查乡镇".equals(adapter3.getItem(position))) {
                     town = adapter3.getItem(position);
                }else {
                    town="";
                }
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!"等级".equals(adapter4.getItem(position))) {
                    threatLevel=adapter4.getItem(position) ;
                }else {
                    threatLevel="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sp5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!"灾害类型".equals(adapter5.getItem(position))) {
                    type=adapter5.getItem(position);
                }else {
                    type="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!"诱发因素".equals(adapter6.getItem(position))) {
                    inducement=adapter6.getItem(position);
                }else {
                    inducement="";
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
                Log.d("limeng", "6");
            }
        });
        dateShow.setAdapter(rcSearchAdapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disasterName=etSearch.getText().toString().trim();
                mDisasterPoints.clear();
                List<DisasterPoint> disasterPoints = GreenDaoManager.queryDisasterList(city, county, town, threatLevel, type, inducement, disasterName);
                Log.d("limeng", "onClick"+disasterPoints.size());
                mDisasterPoints.addAll(disasterPoints);
                rcSearchAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getlistType() {
        WaitingDialog.createLoadingDialog(context,"");
        OkHttpUtils.get().url("http://192.168.10.73:8080/gzcmd/appdocking/listType")
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
                        Gson gson=new Gson();
                        listType=gson.fromJson(response, ListType.class);
                        if (listType.getData()==null){
                            return;
                        }
                        mItems4.clear();
                        mItems4.add("等级");
                        for (int i=0;i<listType.getData().getXqdj().size();i++) {
                            mItems4.add(listType.getData().getXqdj().get(i));
                        }
                        mItems6.clear();
                        mItems6.add("诱发因素");
                        for (int i=0;i<listType.getData().getYfys().size();i++) {
                            mItems6.add(listType.getData().getYfys().get(i));
                        }
                        initViews();
                    }
                });

    }

    /**
     * 启动activity
     *
     * @param context 启动的activity
     */
    public static void startIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }
}
