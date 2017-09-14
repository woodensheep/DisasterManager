package com.nandi.disastermanager.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nandi.disastermanager.R;
import com.nandi.disastermanager.search.entity.MonitorData;
import com.nandi.disastermanager.search.entity.MonitorListData;

import java.util.List;



/**
 * Created by qingsong on 2017/9/8.
 * 监测点列表适配器
 */

public class MonitorListAdapter extends RecyclerView.Adapter<MonitorListAdapter.ViewHolderA>{
    private Context mContext;
    private MonitorListData mMonitorListData;
    public MonitorListAdapter.OnItemClickListener mOnItemClickListener;
    public MonitorListAdapter(Context context,MonitorListData mMonitorListData) {
        mContext = context;
        this.mMonitorListData =  mMonitorListData;
    }



    @Override
    public MonitorListAdapter.ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处动态加载ViewHolder的布局文件并返回holder
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_monitorlist_gz,parent, false);
        MonitorListAdapter.ViewHolderA holderA = new MonitorListAdapter.ViewHolderA(view);
        return holderA;
    }

    @Override
    public void onBindViewHolder(MonitorListAdapter.ViewHolderA holder, final int position) {

            holder.tv_1.setText(mMonitorListData.getData().getResult().get(position).getID());
            holder.tv_2.setText(mMonitorListData.getData().getResult().get(position).getNAME());
            holder.tv_4.setText("查看详情");
            holder.tv_4.setEnabled(true);
            holder.tv_4.setTag(position);

        if (mOnItemClickListener != null) {
            holder.tv_4.setOnClickListener(new View.OnClickListener() {
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
        return mMonitorListData.getData().getResult().size();
    }

    //Item的ViewHolder以及item内部布局控件的id绑定
    class ViewHolderA extends RecyclerView.ViewHolder {

        TextView tv_1;
        TextView tv_2;
        TextView tv_4;
        View view_line;

        public ViewHolderA(View itemView) {
            super(itemView);
            tv_1 = (TextView) itemView.findViewById(R.id.tv_monitor_1);
            tv_2 = (TextView) itemView.findViewById(R.id.tv_monitor_2);
            tv_4 = (TextView) itemView.findViewById(R.id.tv_monitor_4);
            view_line = itemView.findViewById(R.id.view_line);
        }

    }
    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(MonitorListAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

}
