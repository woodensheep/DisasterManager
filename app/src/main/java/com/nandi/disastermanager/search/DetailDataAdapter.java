package com.nandi.disastermanager.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nandi.disastermanager.R;



/**
 * Created by qingsong on 2017/9/8.
 * 隐患点信息适配器
 */

public class DetailDataAdapter extends RecyclerView.Adapter<DetailDataAdapter.ViewHolderA>{
    private Context mContext;
    private String []mArray;
    private String [] arrayLable = {"隐患点名称：","隐患点类型：" ,"隐患点编号："
            ,"所在地：" ,"所在地经度：","所在地纬度："
            ,"详细地址：" ,"主要诱因：","灾情等级："
            ,"受威胁人数：","受威胁财产：","受威胁对象："
            ,"形成时间：","填表时间："
            ,"调查单位：","防治建议：","监测员：","监测人电话："
    };


    public DetailDataAdapter(Context context, String [] array) {
        mContext = context;
        this.mArray =  array;
    }

    @Override
    public DetailDataAdapter.ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处动态加载ViewHolder的布局文件并返回holder
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_detail_gz,parent, false);
        DetailDataAdapter.ViewHolderA holderA = new DetailDataAdapter.ViewHolderA(view);
        return holderA;
    }

    @Override
    public void onBindViewHolder(DetailDataAdapter.ViewHolderA holder, int position) {
        holder.tv_1.setText(arrayLable[position]);
        holder.tv_2.setText(mArray[position]);
        if ("".equals(mArray[position])){
            holder.tv_1.setVisibility(View.GONE);
            holder.tv_2.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        //生成的item的数量
        return arrayLable.length;
    }

    //Item的ViewHolder以及item内部布局控件的id绑定
    class ViewHolderA extends RecyclerView.ViewHolder {

        TextView tv_1;
        TextView tv_2;

        public ViewHolderA(View itemView) {
            super(itemView);
            tv_1 = (TextView) itemView.findViewById(R.id.tv_detail_1);
            tv_2 = (TextView) itemView.findViewById(R.id.tv_detail_2);

        }
    }

}
