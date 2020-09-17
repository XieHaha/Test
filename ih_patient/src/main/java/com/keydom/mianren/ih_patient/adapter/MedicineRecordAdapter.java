package com.keydom.mianren.ih_patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.PrescriptionRecordDrugBean;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：处方药品适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class MedicineRecordAdapter extends RecyclerView.Adapter<MedicineRecordAdapter.ViewHolder> {


    private Context context;
    private List<PrescriptionRecordDrugBean> data;

    /**
     * 构造方法
     */
    public MedicineRecordAdapter(Context context, List<PrescriptionRecordDrugBean> data) {
        this.context = context;
        this.data = data;

    }

    public Context getContext() {
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.prescription_list_item, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        PrescriptionRecordDrugBean bean = data.get(position);
        holder.medicineNum.setText((position + 1) + "、");
        holder.medicineName.setText(bean.getDrugName());
        holder.medicineSpecifications.setText(bean.getSpec());
        holder.medicineAmount.setText(bean.getQuantity() + bean.getDispensingUnit());
        holder.useOnce.setText("用法:" + bean.getFrequency());
        holder.useMethod.setText(bean.getUsage());
        holder.times.setText(bean.getFrequency());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView medicineNum, medicineName, medicineSpecifications, medicineAmount,
                useOnce, useMethod, times;

        public ViewHolder(View itemView) {
            super(itemView);
            medicineName = (TextView) itemView.findViewById(R.id.medicine_name);
            medicineNum = (TextView) itemView.findViewById(R.id.medicine_num);
            medicineSpecifications = (TextView) itemView.findViewById(R.id.medicine_specifications);
            medicineAmount = (TextView) itemView.findViewById(R.id.medicine_amount);
            useOnce = (TextView) itemView.findViewById(R.id.use_once);
            useMethod = (TextView) itemView.findViewById(R.id.use_method);
            times = (TextView) itemView.findViewById(R.id.times);

        }
    }
}
