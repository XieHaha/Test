package com.keydom.mianren.ih_patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.entity.LogisticsEntity;
import com.keydom.mianren.ih_patient.utils.CommUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class LogisticDetailAdapter extends RecyclerView.Adapter<LogisticDetailAdapter.VH> {

    private Context context;
    private List<LogisticsEntity> mDatas=new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private LogisticDetailContentAdapter logisticDetailContentAdapter;


    public LogisticDetailAdapter(Context context) {
        this.context = context;
        //this.mDatas = data;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_logistic_dtail, viewGroup, false);
        return new LogisticDetailAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        if (!CommUtil.isEmpty(mDatas.get(i).getTitle())) {
            vh.mTvTitle.setText(mDatas.get(i).getTitle());
        }

        if (i == 0) {
            vh.mTvTitle.setTextColor(context.getResources().getColor(R.color.other_login_color));
            vh.mImage.setImageResource(R.drawable.bg_dots_blue);
        }
        if (i == mDatas.size() - 1) {
            vh.mView.setVisibility(View.GONE);
        }

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(v);
                }
            }
        });
        LinearLayoutManager layoutmanager = new LinearLayoutManager(context);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置RecyclerView 布局
        vh.mRecyclerView.setLayoutManager(layoutmanager);
        logisticDetailContentAdapter = new LogisticDetailContentAdapter(context);
        vh.mRecyclerView.setAdapter(logisticDetailContentAdapter);
        if (!CommUtil.isEmpty(mDatas.get(i).getInfoList())) {
            logisticDetailContentAdapter.setList(mDatas.get(i).getInfoList());
        }
    }

    @Override
    public int getItemCount() {
        return (null == mDatas) ? 0 : mDatas.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        ImageView mImage;
        TextView mTvTitle;
        RecyclerView mRecyclerView;
        View mView;


        public VH(View v) {
            super(v);
            mTvTitle = v.findViewById(R.id.tv_title);
            mRecyclerView = v.findViewById(R.id.recycler_content);
            mView = v.findViewById(R.id.view);
            mImage = v.findViewById(R.id.image);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(View v);
    }

    public void setList(List<LogisticsEntity> list) {
        mDatas.clear();
        Logger.e("5="+mDatas);
        if (null != list) {
            mDatas.addAll(list);
        }
        notifyDataSetChanged();
    }
}
