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
import com.nandi.disastermanager.utils.NoDoubleClickListener;

import java.util.List;

import static com.nandi.disastermanager.R.id.tv_search_5;
import static com.nandi.disastermanager.R.id.tv_search_6;
import static com.nandi.disastermanager.R.id.tv_search_7;
import static com.nandi.disastermanager.R.id.tv_search_8;


/**
 * Created by lemon on 2017/7/25.
 */

public class RcSearchAdapter extends RecyclerView.Adapter<RcSearchAdapter.ViewHolderA> {
    private Context mContext;
    private List<DisasterPoint> mdisasterPoints;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public RcSearchAdapter(Context context, List<DisasterPoint> disasterPoints) {
        mContext = context;
        mdisasterPoints = disasterPoints;
    }


    public interface OnItemClickListener {
        void onItemClick(View view);
        void onViewClick(int  position);
    }

    @Override
    public ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处动态加载ViewHolder的布局文件并返回holder
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_search_gz, parent, false);
        ViewHolderA holderA = new ViewHolderA(view);
        return holderA;
    }

    @Override
    public void onBindViewHolder(ViewHolderA holder, final int position) {

        holder.tv_1.setText(mdisasterPoints.get(position).getDisasterNum());
        holder.tv_2.setText(mdisasterPoints.get(position).getDisasterName());
        holder.tv_3.setText(mdisasterPoints.get(position).getDisasterGrade());
        holder.tv_4.setText(mdisasterPoints.get(position).getMajorIncentives());
        holder.tv_5.setVisibility(View.VISIBLE);
        holder.tv_5.setTag(mdisasterPoints.get(position).getId());
        holder.tv_6.setVisibility(View.VISIBLE);
        holder.tv_6.setTag(mdisasterPoints.get(position).getId());
        holder.tv_7.setVisibility(View.VISIBLE);
        holder.tv_7.setTag(mdisasterPoints.get(position).getId());
        if (mOnItemClickListener != null) {
            holder.tv_8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onViewClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //生成的item的数量
        return mdisasterPoints.size();
    }

    //Item的ViewHolder以及item内部布局控件的id绑定
    class ViewHolderA extends RecyclerView.ViewHolder {

        TextView tv_1;
        TextView tv_2;
        TextView tv_3;
        TextView tv_4;
        TextView tv_5;
        TextView tv_6;
        TextView tv_7;
        TextView tv_8;
        View view_line;

        public ViewHolderA(View itemView) {
            super(itemView);
            tv_1 = (TextView) itemView.findViewById(R.id.tv_search_1);
            tv_2 = (TextView) itemView.findViewById(R.id.tv_search_2);
            tv_3 = (TextView) itemView.findViewById(R.id.tv_search_3);
            tv_4 = (TextView) itemView.findViewById(R.id.tv_search_4);
            tv_5 = (TextView) itemView.findViewById(tv_search_5);
            tv_6 = (TextView) itemView.findViewById(tv_search_6);
            tv_7 = (TextView) itemView.findViewById(tv_search_7);
            tv_8 = (TextView) itemView.findViewById(R.id.tv_search_8);
            view_line = (View) itemView.findViewById(R.id.view_line);

            tv_5.setOnClickListener(noDoubleClickListener);
            tv_6.setOnClickListener(noDoubleClickListener);
            tv_7.setOnClickListener(noDoubleClickListener);
        }

        NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                switch (view.getId()) {
                    case tv_search_5:

                        Intent intent1 = new Intent(mContext, DetailDataActivity.class);
                        intent1.putExtra("id", (Long) view.getTag());
                        mContext.startActivity(intent1);
                        break;
                    case tv_search_6:
                        Intent intent = new Intent(mContext, MonitorListActivity.class);
                        intent.putExtra("id", (Long) view.getTag());
                        mContext.startActivity(intent);
                        break;
                    case tv_search_7:
                        Intent intent2 = new Intent(mContext, NavigationActivity.class);
                        intent2.putExtra("id", (Long) view.getTag());
                        mContext.startActivity(intent2);
                        break;
                    default:
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClick(view);
                        }
                        break;
                }
            }
        };
    }

}
