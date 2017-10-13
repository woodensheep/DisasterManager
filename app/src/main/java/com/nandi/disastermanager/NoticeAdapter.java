package com.nandi.disastermanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nandi.disastermanager.entity.NoticeInfo;
import com.nandi.disastermanager.search.MonitorAdapter;
import com.nandi.disastermanager.search.entity.MonitorPhoto;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by qingsong on 2017/9/8.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolderA> {
    private Context mContext;
    private NoticeInfo noticeInfo;
    public NoticeAdapter.OnItemClickListener mOnItemClickListener;
    private List<Boolean> isClicks;
    public NoticeAdapter(Context context, NoticeInfo noticeInfo) {
        mContext = context;
        this.noticeInfo = noticeInfo;
        isClicks = new ArrayList<>();
        for(int i = 0;i<noticeInfo.getData().size();i++) {
            if (i==0){
                isClicks.add(true);
            }
            isClicks.add(false);
        }
    }


    @Override
    public NoticeAdapter.ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处动态加载ViewHolder的布局文件并返回holder
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_notice_gz, parent, false);
        NoticeAdapter.ViewHolderA holderA = new NoticeAdapter.ViewHolderA(view);
        return holderA;
    }

    @Override
    public void onBindViewHolder(final NoticeAdapter.ViewHolderA holder, final int position) {
        if (noticeInfo.getData().size()>0){
            holder.textView.setText(noticeInfo.getData().get(noticeInfo.getData().size()-position-1).getTitle());
            if(isClicks.get(position)){
                holder.textView.setTextColor(Color.parseColor("#00a0e9"));
            }else{
                holder.textView.setTextColor(Color.parseColor("#00f3f3"));
            }
            if (mOnItemClickListener != null) {
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i = 0; i <isClicks.size();i++){
                            isClicks.set(i,false);
                        }
                        isClicks.set(position,true);
                        notifyDataSetChanged();
                        mOnItemClickListener.onClick(position);
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        //生成的item的数量
        return noticeInfo.getData().size();
    }

    //Item的ViewHolder以及item内部布局控件的id绑定
    class ViewHolderA extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolderA(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.notice_title);
        }

    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(NoticeAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


}
