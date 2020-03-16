package com.keydom.mianren.ih_patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.keydom.ih_common.utils.CalculateTimeUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.PhysicalExaCommentsInfo;

import java.util.List;
/**
 * 体检套餐适配器
 */
public class PhysicalExaCommentsAdapter extends RecyclerView.Adapter<PhysicalExaCommentsAdapter.VH> {
    private Context context;
    /**
     * 数据集合
     */
    private List<PhysicalExaCommentsInfo> dataList;
    /**
     * 构造方法
     */
    public PhysicalExaCommentsAdapter(Context context, List<PhysicalExaCommentsInfo> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.physical_exa_comments_item, parent, false);
        return new PhysicalExaCommentsAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Glide.with(context).load(dataList.get(position).getCriticsImage()).into(holder.comment_item_logo);
        holder.comment_item_userName.setText(dataList.get(position).getCriticsName());
        holder.comment_item_time.setText(CalculateTimeUtils.CalculateTime(dataList.get(position).getCommentTime()));
        holder.comment_item_content.setText(dataList.get(position).getMyCommentContexxt());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        private TextView comment_item_userName,comment_item_time,comment_item_content;
        private CircleImageView comment_item_logo;
        public VH(View v) {
            super(v);
            comment_item_userName=v.findViewById(R.id.comment_item_userName);
            comment_item_time=v.findViewById(R.id.comment_item_time);
            comment_item_content=v.findViewById(R.id.comment_item_content);
            comment_item_logo=v.findViewById(R.id.comment_item_logo);
        }
    }
}
