package com.keydom.ih_patient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.prescription.ChoosePharmacyActivity;
import com.keydom.ih_patient.bean.entity.pharmacy.PharmacyBean;
import com.keydom.ih_patient.utils.CommUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class ChoosePharmacyAdapter extends RecyclerView.Adapter<ChoosePharmacyAdapter.VH> {

    private ChoosePharmacyActivity context;
    private List<PharmacyBean> mDatas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    private ArrayList<Integer> isCheck = new ArrayList<>();


    public ChoosePharmacyAdapter(ChoosePharmacyActivity context) {
        this.context = context;
        //this.mDatas = mData;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_choose_pharmacy, viewGroup, false);
        return new ChoosePharmacyAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {

        if (!CommUtil.isEmpty(mDatas)) {
            Logger.e("dddd=" + mDatas);
            vh.mTvName.setText(mDatas.get(i).getDrugstore());
            vh.mTvInfo.setText(mDatas.get(i).getDrugstoreAddress());
            vh.mTvFee.setText(mDatas.get(i).getSumFee()+"元");
            vh.mTvDisstance.setText(CommUtil.getDisplayDistance(Integer.parseInt(String.valueOf(mDatas.get(i).getDistance()))));
            Logger.e("dddd=" + mDatas.get(i).getDistance());
        }
        if (isCheck.contains(i)) {
            vh.mCheckBox.setChecked(true);
        } else {
            vh.mCheckBox.setChecked(false);
        }


        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {

                    mOnItemClickListener.onClick(mDatas.get(i));
                }
                if (isCheck.contains(i)) {
                    vh.mCheckBox.setClickable(false);
                    notifyDataSetChanged();
                } else {
                    isCheck.clear();
                    isCheck.add(i);
                    vh.mCheckBox.setChecked(true);
                    notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return(null == mDatas) ? 0 : mDatas.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView mTvName, mTvInfo, mTvFee, mTvDisstance;
        private CheckBox mCheckBox;

        public VH(View v) {
            super(v);
            mTvName = v.findViewById(R.id.tv_name);
            mTvInfo = v.findViewById(R.id.tv_info);
            mTvFee = v.findViewById(R.id.tv_fee);
            mTvDisstance = v.findViewById(R.id.tv_distance);
            mCheckBox = v.findViewById(R.id.image_select);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(PharmacyBean pharmacyBean);
    }

    public void setList(List<PharmacyBean> list) {
        mDatas.clear();
        if (null != list) {
            mDatas.addAll(list);
        }
        notifyDataSetChanged();
    }
}
