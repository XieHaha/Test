package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.get_drug.GetDrugActivity;
import com.keydom.mianren.ih_patient.bean.entity.GetDrugEntity;

import java.util.ArrayList;
import java.util.List;

public class GetDrugAdapter extends RecyclerView.Adapter<GetDrugAdapter.VH> {

    private GetDrugActivity context;
    private List<GetDrugEntity> mDatas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private String mAcquireMedicine = null;

    public GetDrugAdapter(GetDrugActivity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_get_drug, viewGroup, false);
        return new GetDrugAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.mTvTitle.setText(mDatas.get(i).getPaystatus().getName());
        vh.mTvOrder.setText(mDatas.get(i).getPrescriptionId());
        vh.mTvDate.setText(mDatas.get(i).getOrderTime());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
/*                    mAcquireMedicine = mDatas.get(i).getAcquireMedicine();
                    //todo  修改成运单号
                    String mWayBill = mDatas.get(i).getPrescriptionId();
                    String acquireMedicineCode = mDatas.get(i).getAcquireMedicineCode();*/
                    mOnItemClickListener.onClick(mDatas.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null == mDatas) ? 0 : mDatas.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView mTvTitle, mTvOrder, mTvDate;


        public VH(View v) {
            super(v);
            mTvTitle = v.findViewById(R.id.tv_title);
            mTvOrder = v.findViewById(R.id.tv_order);
            mTvDate = v.findViewById(R.id.tv_date);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(GetDrugEntity getDrugEntity);
    }

    public void setList(List<GetDrugEntity> list) {
        mDatas.clear();
        if (null != list) {
            mDatas.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addList(List<GetDrugEntity> list) {
        if (null != list) {
            mDatas.addAll(list);
        }
        notifyDataSetChanged();
    }
}
