package com.nandi.disastermanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nandi.disastermanager.R;
import com.nandi.disastermanager.entity.DetailPersonInfo;

/**
 * Created by lemon on 2017/7/25.
 */

public class RcPersonAdapter extends RecyclerView.Adapter<RcPersonAdapter.ViewHolderA>{
    private Context mContext;
    private DetailPersonInfo mDetailPersonInfo;

    public RcPersonAdapter(Context context, DetailPersonInfo detailPersonInfo) {
        mContext = context;
        mDetailPersonInfo =  detailPersonInfo;
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

        String str1 = "暂无",str2= "暂无",str3= "暂无",str4= "暂无";
        switch (position) {
            case 0:
                str1="片区负责人";
                if(mDetailPersonInfo.getData().getAreaAdmin().size()!=0) {
                   // http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=1&path=wansheng/licaibing.jpg
                    str2 = mDetailPersonInfo.getData().getAreaAdmin().get(0).getAdmin_name();
                    str3 = mDetailPersonInfo.getData().getAreaAdmin().get(0).getAdmin_phone();
                    str4 = "http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=1&path="+mDetailPersonInfo.getData().getAreaAdmin().get(0).getAdmin_pic();
                }
                break;
            case 1:
                str1="地环站人员";
                if(mDetailPersonInfo.getData().getDihuanzhan().size()!=0) {
                   // http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=4&path=wansheng/mulianqing.jpg
                    str2 = mDetailPersonInfo.getData().getDihuanzhan().get(0).getName();
                    str3 = mDetailPersonInfo.getData().getDihuanzhan().get(0).getDhz_phone();
                    str4 = "http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=4&path="+mDetailPersonInfo.getData().getDihuanzhan().get(0).getUrl();
                }
                break;
            case 2:
                str1="地质驻守人员";
                if(mDetailPersonInfo.getData().getAreaProfessor().size()!=0) {
                   // http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=2&path=wansheng/fengfan.jpg
                    str2 = mDetailPersonInfo.getData().getAreaProfessor().get(0).getDisname();
                    str3 = mDetailPersonInfo.getData().getAreaProfessor().get(0).getProfessor_phone();
                    str4 ="http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=2&path="+ mDetailPersonInfo.getData().getAreaProfessor().get(0).getDispicture();
                }
                break;
            case 3:
                str1="群测群防人员";
                if(mDetailPersonInfo.getData().getHuman().size()!=0) {
                    //http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=7&path=wansheng/conglinzhen/yangguiyin.jpg
                    str2 = mDetailPersonInfo.getData().getHuman().get(0).getHuman_name();
                    str3 = mDetailPersonInfo.getData().getHuman().get(0).getHuman_phone();
                    str4 = "http://183.230.182.149:18080/springmvc-background/downloadImgOrVideo.do?type=7&path="+mDetailPersonInfo.getData().getHuman().get(0).getHead_url();
                }
                break;
        }
        holder.tv_1.setText(str1);
        holder.tv_3.setText(str2);
        holder.tv_4.setText(str3);
                Glide.with(mContext)
                .load(str4)
                .placeholder(R.mipmap.downloading)
                .thumbnail(0.1f)
                .error(R.mipmap.download_pass)
                .into(holder.iv_2);
    }

    @Override
    public int getItemCount() {
        //生成的item的数量
        return 4;
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
