package com.nandi.disastermanager.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.nandi.disastermanager.R;
import com.nandi.disastermanager.search.entity.DisasterPoint;

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

    /**数据**/
    private List<DisasterPoint> mDisasterPoints=new ArrayList<>();
    /**recycleView适配器**/
    RcSearchAdapter rcSearchAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_gz);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        final String[] mItems1 = getResources().getStringArray(R.array.search_type_1);
        final String[] mItems2 = getResources().getStringArray(R.array.search_type_1);
        final String[] mItems3 = getResources().getStringArray(R.array.search_type_1);
        final String[] mItems4 = getResources().getStringArray(R.array.search_type_4);
        final String[] mItems5 = getResources().getStringArray(R.array.search_type_5);
        final String[] mItems6 = getResources().getStringArray(R.array.search_type_6);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems4);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems5);
        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems6);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        sp4.setAdapter(adapter4);
        sp5.setAdapter(adapter5);
        sp6.setAdapter(adapter6);
        sp4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dateShow.setLayoutManager(new LinearLayoutManager(this));
        rcSearchAdapter=new RcSearchAdapter(this,mDisasterPoints);
        dateShow.setAdapter(rcSearchAdapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDisasterPoints.clear();
                mDisasterPoints.add(new DisasterPoint(1l,"1","1","1","1","1","1","大型","滑坡","自然","**滑坡"));
                mDisasterPoints.add(new DisasterPoint(2l,"2","2","2","2","2","2","小型","滑坡","自然","**滑坡"));
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
