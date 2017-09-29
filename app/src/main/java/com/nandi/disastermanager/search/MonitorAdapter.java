package com.nandi.disastermanager.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nandi.disastermanager.MyApplication;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.search.entity.MonitorData;
import com.nandi.disastermanager.search.entity.MonitorPoint;

import java.util.List;


/**
 * Created by qingsong on 2017/9/8.
 */

public class MonitorAdapter extends RecyclerView.Adapter<MonitorAdapter.ViewHolderA> {
    private Context mContext;
    private MonitorData mMonitorData;
    public MonitorAdapter.OnItemClickListener mOnItemClickListener;
    private int count;
    private List<MonitorPoint> monitorPoints;
    public MonitorAdapter(Context context, List monitorPoints) {
        mContext = context;
        this.monitorPoints = monitorPoints;
    }


    @Override
    public MonitorAdapter.ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处动态加载ViewHolder的布局文件并返回holder
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_monitor_gz, parent, false);
        MonitorAdapter.ViewHolderA holderA = new MonitorAdapter.ViewHolderA(view);
        return holderA;
    }

    @Override
    public void onBindViewHolder(MonitorAdapter.ViewHolderA holder, final int position) {

            holder.tv_1.setText(monitorPoints.get(position).getMonitorId());
            holder.tv_2.setText(monitorPoints.get(position).getName());
            holder.tv_3.setText(monitorPoints.get(position).getTime());
            holder.tv_5.setText(monitorPoints.get(position).getMonitorData()+"");
            holder.tv_8.setText("查看图片");

        if (mOnItemClickListener != null) {
            holder.tv_8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //生成的item的数量
        return monitorPoints.size();
    }

    //Item的ViewHolder以及item内部布局控件的id绑定
    class ViewHolderA extends RecyclerView.ViewHolder {

        TextView tv_1;
        TextView tv_2;
        TextView tv_3;
        TextView tv_5;
        TextView tv_8;
        View view_lin;

        public ViewHolderA(View itemView) {
            super(itemView);
            tv_1 = (TextView) itemView.findViewById(R.id.tv_monitor_1);
            tv_2 = (TextView) itemView.findViewById(R.id.tv_monitor_2);
            tv_3 = (TextView) itemView.findViewById(R.id.tv_monitor_3);
            tv_5 = (TextView) itemView.findViewById(R.id.tv_monitor_5);
            tv_8 = (TextView) itemView.findViewById(R.id.tv_monitor_8);

            view_lin = itemView.findViewById(R.id.view_line);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(MonitorAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

}
