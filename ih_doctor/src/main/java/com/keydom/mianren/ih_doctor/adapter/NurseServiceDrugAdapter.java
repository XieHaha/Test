package com.keydom.mianren.ih_doctor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.MaterialBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：护理服务耗材适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class NurseServiceDrugAdapter extends RecyclerView.Adapter<NurseServiceDrugAdapter.ViewHolder> {

    private Context mContext;
    private List<MaterialBean> data;

    public NurseServiceDrugAdapter(List<MaterialBean> data, Context context) {
        this.mContext = context;
        this.data = data;

    }

    @Override
    public NurseServiceDrugAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.finish_nurse_service_drug_item, parent, false);
        return new NurseServiceDrugAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NurseServiceDrugAdapter.ViewHolder holder, final int position) {
        MaterialBean bean = data.get(position);
        holder.drug_num.setText((position + 1) + "、");
        holder.drug_name.setText(bean.getName());
        holder.drug_specifications.setText(bean.getSpec());
        holder.drug_quantity.setText(String.valueOf(bean.getMaterialAmount()) + bean.getUnitName());
        holder.drug_fee.setText(String.valueOf(bean.getUnivalent().multiply(new BigDecimal(bean.getMaterialAmount()))));
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView drug_num, drug_name, drug_specifications, drug_quantity, drug_fee;

        public ViewHolder(View itemView) {
            super(itemView);
            drug_num = itemView.findViewById(R.id.drug_num);
            drug_name = itemView.findViewById(R.id.drug_name);
            drug_specifications = itemView.findViewById(R.id.drug_specifications);
            drug_quantity = itemView.findViewById(R.id.drug_quantity);
            drug_fee = itemView.findViewById(R.id.drug_fee);
        }
    }
}
