package com.nandi.disastermanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nandi.disastermanager.R;
import com.nandi.disastermanager.entity.SearchPerson;

/**
 * Created by lemon on 2017/7/25.
 */

public class RcSearchPersonAdapter extends RecyclerView.Adapter<RcSearchPersonAdapter.ViewHolderA>{
    private Context mContext;
    private SearchPerson mSearchPerson;
    private OnItemClickListener mOnItemClickListener;
    public RcSearchPersonAdapter(Context context, SearchPerson searchPerson) {
        mContext = context;
        mSearchPerson =  searchPerson;
    }
    public interface OnItemClickListener{
        void onItemClick(View view);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }
    @Override
    public ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处动态加载ViewHolder的布局文件并返回holder
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_search_place,parent, false);
        ViewHolderA holderA = new ViewHolderA(view);
        return holderA;
    }

    @Override
    public void onBindViewHolder(ViewHolderA holder, int position) {
        if (position==0) {
            holder.tv_1.setText("姓名");
            holder.tv_2.setText("类型");
            holder.tv_3.setText("电话");
        }else {
            holder.tv_1.setText(mSearchPerson.getData().get(position-1).getName());
            holder.tv_2.setText(mSearchPerson.getData().get(position-1).getType_name());
            holder.tv_3.setText(mSearchPerson.getData().get(position-1).getPhone());
        }
    }

    @Override
    public int getItemCount() {
        //生成的item的数量
        return mSearchPerson.getData().size()+1;
    }

    //Item的ViewHolder以及item内部布局控件的id绑定
    class ViewHolderA extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_1;
        TextView tv_2;
        TextView tv_3;
        TextView tv_4;
        public ViewHolderA(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_1 = (TextView) itemView.findViewById(R.id.tv_search_1);
            tv_2 = (TextView) itemView.findViewById(R.id.tv_search_2);
            tv_3 = (TextView) itemView.findViewById(R.id.tv_search_3);
            tv_4 = (TextView) itemView.findViewById(R.id.tv_search_4);
        }
        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view);
            }
        }
    }

}
