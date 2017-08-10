package com.nandi.disastermanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nandi.disastermanager.R;

import java.util.List;

/**
 * Created by lemon on 2017/7/25.
 */

public class RCDisasterPhotoAdapter  extends RecyclerView.Adapter<RCDisasterPhotoAdapter.ViewHolderA>{
    private Context mContext;
    private List<String> mList;

    public RCDisasterPhotoAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处动态加载ViewHolder的布局文件并返回holder
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_disaster_photo,parent, false);
        ViewHolderA holderA = new ViewHolderA(view);
        return holderA;
    }

    @Override
    public void onBindViewHolder(ViewHolderA holder, int position) {
        //此处设置Item中view的数据
        Glide.with(mContext)
                .load(Integer.parseInt(mList.get(position)))
                .placeholder(R.mipmap.downloading)
                .thumbnail(0.1f)
                .error(R.mipmap.download_pass)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        //生成的item的数量
        return mList.size();
    }

    //Item的ViewHolder以及item内部布局控件的id绑定
    class ViewHolderA extends RecyclerView.ViewHolder{

        ImageView imageView;
        public ViewHolderA(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_disaster_photo);
        }
    }

}
