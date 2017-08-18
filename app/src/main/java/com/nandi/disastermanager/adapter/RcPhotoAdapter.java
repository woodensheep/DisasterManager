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

public class RcPhotoAdapter extends RecyclerView.Adapter<RcPhotoAdapter.ViewHolderA> {
    private Context mContext;
    private List<String> mList ;
    private onItemClickListener mItemClickListener;
    public RcPhotoAdapter(Context context,List<String> mList) {
        mContext = context;
        this.mList =  mList;
    }

    @Override
    public ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处动态加载ViewHolder的布局文件并返回holder
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_disaster_photo,parent, false);
        ViewHolderA holderA = new ViewHolderA(view);
        return holderA;
    }

    @Override
    public void onBindViewHolder(final ViewHolderA holder, final int position) {
        //此处设置Item中view的数据
        String str4= "暂无";
                if(mList.get(position)!=null&&mList.get(position)!="") {
                // http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=10&path=5001101070140101-q.jpg
                str4 = "http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=10&path=" + mList.get(position);
                }
                Glide.with(mContext)
                .load(str4)
                .placeholder(R.mipmap.downloading)
                .thumbnail(0.1f)
                .error(R.mipmap.download_pass)
                .into(holder.iv);
        holder.iv.setTag(str4);
        if(mItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.OnItemClick(holder.iv,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //生成的item的数量
        return mList.size();
    }

    //Item的ViewHolder以及item内部布局控件的id绑定
    class ViewHolderA extends RecyclerView.ViewHolder{

        ImageView iv;
        public ViewHolderA(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_disaster_photo);
        }

    }

    public void setItemClickListener(onItemClickListener onItemClickListener){
        this.mItemClickListener=onItemClickListener;
    }

    public interface onItemClickListener{
        void OnItemClick(View view ,int position);
    }
}
