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

import com.nandi.disastermanager.R;
import com.nandi.disastermanager.dao.GreenDaoManager;
import com.nandi.disastermanager.search.entity.AreaInfo;
import com.nandi.disastermanager.search.entity.DisasterPoint;
import com.nandi.disastermanager.search.entity.LoginInfo;

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

    private Context context;
    /**数据**/
    private List<DisasterPoint> mDisasterPoints=new ArrayList<>();
    /**recycleView适配器**/
    RcSearchAdapter rcSearchAdapter;
    private LoginInfo loginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_gz);
        ButterKnife.bind(this);
        context=this;
        initViews();
    }



    private void initViews() {
        List<String> mItems1=new ArrayList<>();
        final List<String> mItems2=new ArrayList<>();
        final List<String> mItems3=new ArrayList<>();
        final String[] mItems4 = getResources().getStringArray(R.array.search_type_4);
        final String[] mItems5 = getResources().getStringArray(R.array.search_type_5);
        final String[] mItems6 = getResources().getStringArray(R.array.search_type_6);
        mItems1.add("筛查州市");
        for (AreaInfo areaInfo: GreenDaoManager.queryAreaLevel(2)) {
            mItems1.add(areaInfo.getName());
        }
        mItems2.add("筛查区县");
        mItems3.add("筛查乡镇");
        // 建立Adapter并且绑定数据源
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems1);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems2);
        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems3);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems4);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems5);
        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems6);
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
                if (!"筛查州市".equals(adapter1.getItem(position))){
                    String name=adapter1.getItem(position);
                    int code=GreenDaoManager.queryArea2(name).get(0).getArea_id();
                    mItems2.clear();
                    mItems2.add("筛查区县");
                    for (AreaInfo areaInfo: GreenDaoManager.queryAreaLevel(3,code)) {
                        mItems2.add(areaInfo.getName());
                    }
                    adapter2.notifyDataSetChanged();
                    mItems3.clear();
                    mItems3.add("筛查乡镇");
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
                if (!"筛查区县".equals(adapter2.getItem(position))){
                    String name=adapter2.getItem(position);
                    int code=GreenDaoManager.queryArea3(name).get(0).getArea_id();
                    mItems3.clear();
                    mItems3.add("筛查乡镇");
                    for (AreaInfo areaInfo: GreenDaoManager.queryAreaLevel(4,code)) {
                        mItems3.add(areaInfo.getName());
                    }
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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dateShow.setLayoutManager(new LinearLayoutManager(this));
        rcSearchAdapter=new RcSearchAdapter(this,mDisasterPoints);
        rcSearchAdapter.setOnItemClickListener(new RcSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Log.d("limeng","6");
            }
        });
        dateShow.setAdapter(rcSearchAdapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDisasterPoints.clear();
                mDisasterPoints.add(new DisasterPoint(1l,"1","1","1","1","1","1","1","1","","大型","滑坡","自然","**滑坡"));
                mDisasterPoints.add(new DisasterPoint(1l,"1","1","1","1","1","1","1","1","","大型","滑坡","自然","**滑坡"));
                rcSearchAdapter.notifyDataSetChanged();
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
