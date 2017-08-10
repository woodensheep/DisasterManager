package com.nandi.disastermanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nandi.disastermanager.R;

import java.util.List;

/**
 * Created by lemon on 2017/7/25.
 */

public class RcPersonAdapter extends RecyclerView.Adapter<RcPersonAdapter.ViewHolderA>{
    private Context mContext;
    private List<String> mList;

    public RcPersonAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处动态加载ViewHolder的布局文件并返回holder
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_person,parent, false);
        ViewHolderA holderA = new ViewHolderA(view);
        return holderA;
    }

    @Override
    public void onBindViewHolder(ViewHolderA holder, int position) {
        //此处设置Item中view的数据
//        Glide.with(mContext)
//                .load(Integer.parseInt(mList.get(position)))
//                .placeholder(R.mipmap.downloading)
//                .thumbnail(0.1f)
//                .error(R.mipmap.download_pass)
//                .into(holder.iv_2);
        holder.tv_1.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        //生成的item的数量
        return mList.size();
    }

    //Item的ViewHolder以及item内部布局控件的id绑定
    class ViewHolderA extends RecyclerView.ViewHolder{

        ImageView iv_2;
        TextView tv_1;
        TextView tv_3;
        TextView tv_4;
        public ViewHolderA(View itemView) {
            super(itemView);

            tv_1 = (TextView) itemView.findViewById(R.id.tv_rc_person_1);
            iv_2 = (ImageView) itemView.findViewById(R.id.iv_rc_person_2);
            tv_3 = (TextView) itemView.findViewById(R.id.tv_rc_person_3);
            tv_4 = (TextView) itemView.findViewById(R.id.tv_rc_person_4);
        }
    }

}
