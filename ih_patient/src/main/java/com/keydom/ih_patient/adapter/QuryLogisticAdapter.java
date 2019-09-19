package com.keydom.ih_patient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.logistic.QueryLogisticActivity;
import com.keydom.ih_patient.bean.entity.LogisticsEntity;

import java.util.ArrayList;
import java.util.List;

public class QuryLogisticAdapter extends RecyclerView.Adapter<QuryLogisticAdapter.VH> {

    private QueryLogisticActivity context;
    private List<LogisticsEntity> mDatas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public QuryLogisticAdapter(QueryLogisticActivity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_query_logistic, viewGroup, false);
        return new QuryLogisticAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.mTvTitle.setText(mDatas.get(i).getCarrier());
        vh.mTvOrder.setText(mDatas.get(i).getWaybill());
        vh.mTvDate.setText(mDatas.get(i).getStatus().getName());
        //todo 圖片
        String mImageUrl = new StringBuilder().append(Const.RELEASE_HOST).append(mDatas.get(i).getImgAddress()).toString();
        GlideUtils.load(vh.mImage,mImageUrl,R.drawable.bg_default_photo,R.drawable.bg_default_photo,false,null);
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(mDatas.get(i).getWaybill());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        ImageView mImage;
        TextView mTvTitle, mTvOrder, mTvDate;


        public VH(View v) {
            super(v);
            mTvTitle = v.findViewById(R.id.tv_title);
            mTvOrder = v.findViewById(R.id.tv_order);
            mTvDate = v.findViewById(R.id.tv_date);
            mImage = v.findViewById(R.id.image_icon);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(String v);
    }

    public void setList(List<LogisticsEntity> list) {
        mDatas.clear();
        if (null != list) {
            mDatas.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addList(List<LogisticsEntity> list) {
        if (null != list) {
            mDatas.addAll(list);
        }
        notifyDataSetChanged();
    }
}
