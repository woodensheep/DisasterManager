package com.nandi.disastermanager.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nandi.disastermanager.R;
import com.nandi.disastermanager.search.entity.DisasterPoint;

import java.util.List;

import static com.nandi.disastermanager.R.id.tv_search_5;
import static com.nandi.disastermanager.R.id.tv_search_6;
import static com.nandi.disastermanager.R.id.tv_search_7;


/**
 * Created by lemon on 2017/7/25.
 */

public class RcSearchAdapter extends RecyclerView.Adapter<RcSearchAdapter.ViewHolderA>{
    private Context mContext;
    private List<DisasterPoint> mdisasterPoints;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public RcSearchAdapter(Context context, List<DisasterPoint> disasterPoints) {
        mContext = context;
        mdisasterPoints =  disasterPoints;
    }


    public interface OnItemClickListener{
        void onItemClick(View view);
    }
    @Override
    public ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处动态加载ViewHolder的布局文件并返回holder
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_search_gz,parent, false);
        ViewHolderA holderA = new ViewHolderA(view);
        return holderA;
    }

    @Override
    public void onBindViewHolder(ViewHolderA holder, int position) {
        if(position==0){

            holder.tv_1.setText("隐患点编号");
            holder.tv_2.setText("名称");
            holder.tv_3.setText("等级");
            holder.tv_4.setText("诱发因素");
            holder.tv_5.setVisibility(View.INVISIBLE);
            holder.tv_6.setVisibility(View.INVISIBLE);
            holder.tv_7.setVisibility(View.INVISIBLE);
            holder.view_line.setVisibility(View.VISIBLE);
            holder.itemView.setTag(-1L);
        }else{
            holder.tv_1.setText(mdisasterPoints.get(position-1).getDisasterCode());
            holder.tv_2.setText(mdisasterPoints.get(position-1).getDisasterName());
            holder.tv_3.setText(mdisasterPoints.get(position-1).getThreatLevel());
            holder.tv_4.setText(mdisasterPoints.get(position-1).getInducement());
            holder.tv_5.setVisibility(View.VISIBLE);
            holder.tv_5.setTag(mdisasterPoints.get(position-1).getId());
            holder.tv_6.setVisibility(View.VISIBLE);
            holder.tv_6.setTag(mdisasterPoints.get(position-1).getId());
            holder.tv_7.setVisibility(View.VISIBLE);
            holder.tv_7.setTag(mdisasterPoints.get(position-1).getId());
            holder.view_line.setVisibility(View.GONE);
            holder.itemView.setTag(mdisasterPoints.get(position-1).getId());
        }
    }

    @Override
    public int getItemCount() {
        //生成的item的数量
        return mdisasterPoints.size()+1;
    }

    //Item的ViewHolder以及item内部布局控件的id绑定
    class ViewHolderA extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_1;
        TextView tv_2;
        TextView tv_3;
        TextView tv_4;
        TextView tv_5;
        TextView tv_6;
        TextView tv_7;
        View view_line;
        public ViewHolderA(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_1 = (TextView) itemView.findViewById(R.id.tv_search_1);
            tv_2 = (TextView) itemView.findViewById(R.id.tv_search_2);
            tv_3 = (TextView) itemView.findViewById(R.id.tv_search_3);
            tv_4 = (TextView) itemView.findViewById(R.id.tv_search_4);
            tv_5 = (TextView) itemView.findViewById(tv_search_5);
            tv_6 = (TextView) itemView.findViewById(tv_search_6);
            tv_7 = (TextView) itemView.findViewById(tv_search_7);
            view_line = (View) itemView.findViewById(R.id.view_line);

            tv_5.setOnClickListener(this);
            tv_6.setOnClickListener(this);
            tv_7.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case tv_search_5:

                    Intent intent1 =  new Intent(mContext,DetailDataActivity.class);
                    intent1.putExtra("id", (Long)view.getTag());
                    mContext.startActivity(intent1);
                    break;
                case tv_search_6:
                    Intent intent =  new Intent(mContext,MonitorListActivity.class);
                    intent.putExtra("id", (Long)view.getTag());
                    mContext.startActivity(intent);
                    break;
                case tv_search_7:
                    Intent intent2 =  new Intent(mContext,NavigationActivity.class);
                    intent2.putExtra("id", (Long)view.getTag());
                    mContext.startActivity(intent2);
                    break;
                default:
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view);
                    }
                    break;
            }


        }
    }

}
