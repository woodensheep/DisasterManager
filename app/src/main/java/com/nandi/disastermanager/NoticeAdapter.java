package com.nandi.disastermanager;

import android.content.Context;
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


/**
 * Created by qingsong on 2017/9/8.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolderA> {
    private Context mContext;
    private NoticeInfo noticeInfo;
    public NoticeAdapter.OnItemClickListener mOnItemClickListener;

    public NoticeAdapter(Context context, NoticeInfo noticeInfo) {
        mContext = context;
        this.noticeInfo = noticeInfo;
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
        holder.textView.setText(noticeInfo.getData().get(position).getTitle());
        if (mOnItemClickListener != null) {
            holder.textView.setOnClickListener(new View.OnClickListener() {
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
