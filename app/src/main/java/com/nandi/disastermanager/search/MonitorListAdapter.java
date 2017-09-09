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

import static com.nandi.disastermanager.R.id.tv_monitor_4;
import static com.nandi.disastermanager.R.id.tv_monitor_8;
import static com.nandi.disastermanager.R.id.tv_monitor_9;

/**
 * Created by qingsong on 2017/9/8.
 * 监测点列表适配器
 */

public class MonitorListAdapter extends RecyclerView.Adapter<MonitorListAdapter.ViewHolderA>{
    private Context mContext;
    private MonitorListData mMonitorListData;
    private MonitorListAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(MonitorListAdapter.OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public MonitorListAdapter(Context context,MonitorListData mMonitorListData) {
        mContext = context;
        this.mMonitorListData =  mMonitorListData;
    }


    public interface OnItemClickListener{
        void onItemClick(View view);
    }
    @Override
    public MonitorListAdapter.ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处动态加载ViewHolder的布局文件并返回holder
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_monitorlist_gz,parent, false);
        MonitorListAdapter.ViewHolderA holderA = new MonitorListAdapter.ViewHolderA(view);
        return holderA;
    }

    @Override
    public void onBindViewHolder(MonitorListAdapter.ViewHolderA holder, int position) {
        if(position==0){
            holder.tv_1.setText("监测点编号");
            holder.tv_2.setText("监测点名称");
            holder.tv_3.setText("监测时间");
            holder.tv_4.setText(" ");
            holder.tv_4.setBackground(null);
            holder.view_line.setVisibility(View.VISIBLE);

        }else{
            holder.tv_1.setText(mMonitorListData.getData().getResult().get(position-1).getID());
            holder.tv_2.setText(mMonitorListData.getData().getResult().get(position-1).getNAME());
            holder.tv_3.setText(mMonitorListData.getData().getResult().get(position-1).getTime());
            holder.tv_4.setText("查看详情");

        }
    }

    @Override
    public int getItemCount() {
        //生成的item的数量
        return mMonitorListData.getData().getResult().size()+1;
    }

    //Item的ViewHolder以及item内部布局控件的id绑定
    class ViewHolderA extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_1;
        TextView tv_2;
        TextView tv_3;
        TextView tv_4;
        View view_line;

        public ViewHolderA(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_1 = (TextView) itemView.findViewById(R.id.tv_monitor_1);
            tv_2 = (TextView) itemView.findViewById(R.id.tv_monitor_2);
            tv_3 = (TextView) itemView.findViewById(R.id.tv_monitor_3);
            tv_4 = (TextView) itemView.findViewById(R.id.tv_monitor_4);
            view_line = itemView.findViewById(R.id.view_line);
            tv_4.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case tv_monitor_4:
                    Intent intent =  new Intent(mContext,MonitorDataActivity.class);
                    mContext.startActivity(intent);
                    break;
                default:

                    break;
            }


        }
    }

}
