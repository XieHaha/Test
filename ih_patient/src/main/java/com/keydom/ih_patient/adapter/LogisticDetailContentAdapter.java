package com.keydom.ih_patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.logistic.LogisticDetailActivity;
import com.keydom.ih_patient.bean.entity.LogisticsEntity;
import com.keydom.ih_patient.bean.entity.pharmacy.infoEntity;

import java.util.ArrayList;
import java.util.List;

public class LogisticDetailContentAdapter extends RecyclerView.Adapter<LogisticDetailContentAdapter.VH> {

    private Context context;
    private List<infoEntity> mDatas=new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;


    public LogisticDetailContentAdapter(Context context) {
        this.context = context;
        //this.mDatas = data;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_logistic_dtail_content, viewGroup, false);
        return new LogisticDetailContentAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.mTvContent.setText(mDatas.get(i).getAcceptStation());
        vh.mTvDate.setText(mDatas.get(i).getAcceptTime());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null == mDatas) ? 0 : mDatas.size();
    }

    public class VH extends RecyclerView.ViewHolder {

        TextView mTvContent, mTvDate;

        public VH(View v) {
            super(v);
            mTvContent = v.findViewById(R.id.tv_content);
            mTvDate = v.findViewById(R.id.tv_date);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(View v);
    }

    public void setList(List<infoEntity> list) {
        mDatas.clear();
        if (null != list) {
            mDatas.addAll(list);
        }
        notifyDataSetChanged();
    }
}
