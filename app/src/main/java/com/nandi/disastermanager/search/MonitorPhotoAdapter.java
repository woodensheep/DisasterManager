package com.nandi.disastermanager.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.search.entity.MonitorData;
import com.nandi.disastermanager.search.entity.MonitorPhoto;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import okhttp3.Call;

import static com.nandi.disastermanager.R.id.tv_monitor_8;
import static com.nandi.disastermanager.R.id.tv_monitor_9;


/**
 * Created by qingsong on 2017/9/8.
 */

public class MonitorPhotoAdapter extends RecyclerView.Adapter<MonitorPhotoAdapter.ViewHolderA> {
    private Context mContext;
    private MonitorPhoto mMonitorData;
    private MonitorPhotoAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(MonitorPhotoAdapter.OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public MonitorPhotoAdapter(Context context, MonitorPhoto mMonitorData) {
        mContext = context;
        this.mMonitorData = mMonitorData;
    }


    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    @Override
    public MonitorPhotoAdapter.ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处动态加载ViewHolder的布局文件并返回holder
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_monitor_photo_gz, parent, false);
        MonitorPhotoAdapter.ViewHolderA holderA = new MonitorPhotoAdapter.ViewHolderA(view);
        return holderA;
    }

    @Override
    public void onBindViewHolder(final MonitorPhotoAdapter.ViewHolderA holder, int position) {
        String url = "http://gzgdm.oss-cn-guizhou-gov.aliyuncs.com/" + mMonitorData.getData().get(position).getUrl();
        try {
            Log.i("TAG",url);
          String s=  URLDecoder.decode(url,"UTF-8");
            Glide.with(mContext)
                    .load(url)
                    .placeholder(R.mipmap.downloading)
                    .thumbnail(0.1f)
                    .error(R.mipmap.download_pass)
                    .into(holder.monitor_photo);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        //生成的item的数量
        return mMonitorData.getData().size();
    }

    //Item的ViewHolder以及item内部布局控件的id绑定
    class ViewHolderA extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView monitor_photo;
        private View view_lian;

        public ViewHolderA(View itemView) {
            super(itemView);
            monitor_photo = (ImageView) itemView.findViewById(R.id.monitor_photo);
            view_lian = itemView.findViewById(R.id.view_line);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener!=null){
                mOnItemClickListener.onItemClick(view);
            }
        }
    }

}
