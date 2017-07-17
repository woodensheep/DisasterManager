package com.nandi.disastermanager;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
    private boolean llAreaState = false;
    private boolean llDataState = false;
    private int llMoreState = -1;
    private int llMoreStateBefore = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setListeners();
    }

    private void setListeners() {
        ivAreaBack.setOnClickListener(this);
        ivDataBack.setOnClickListener(this);
        llRainfall.setOnClickListener(this);
        llDangerpoint.setOnClickListener(this);
        llStaff.setOnClickListener(this);
        llEquipment.setOnClickListener(this);
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
                llMoreStateBefore=llMoreState;
                llMoreState=1;
                setRainfallMore();
                break;
            case R.id.ll_dangerpoint:
                llMoreStateBefore=llMoreState;
                llMoreState=2;
                setRainfallMore();
                break;
            case R.id.ll_staff:
                llMoreStateBefore=llMoreState;
                llMoreState=3;
                setRainfallMore();
                break;
            case R.id.ll_equipment:
                llMoreStateBefore=llMoreState;
                llMoreState=4;
                setRainfallMore();
                break;

        }
    }

    private void setRainfallMore() {
        rgRainfall.clearCheck();
        rgDangerpoint.clearCheck();
        rgStaff.clearCheck();
        rgEquipment.clearCheck();
        switch (llMoreState){
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


        if(llMoreState!=llMoreStateBefore){
            Log.d("limeng","llMoreState:"+llMoreState+"\n"+"llMoreStateBefore:"+llMoreStateBefore);
            ObjectAnimator animator3=null;
            ObjectAnimator animator4=null;
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
            if(animator3!=null) {
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
            if(animator4!=null) {
                animator4.setDuration(100);
                animator4.start();
            }
        }

    }

    private void setMoreState(){
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
