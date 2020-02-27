package com.keydom.ih_patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.ih_patient.R;

import java.util.List;

/**
 * 病案邮寄
 */
public class MedicalMailOrderAdapter extends RecyclerView.Adapter<MedicalMailOrderAdapter.VH> {
    private Context context;
    private List<String> dataList;

    /**
     * 构造方法
     */
    public MedicalMailOrderAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medical_mail_order
                        , parent, false);
        return new MedicalMailOrderAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView tvName, tvBed, tvDoctor, tvNotice, tvAnesthetist, tvDate, tvStatus;

        public VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_name);
            tvBed = v.findViewById(R.id.tv_bed);
            tvDoctor = v.findViewById(R.id.tv_doctor);
            tvNotice = v.findViewById(R.id.tv_notice);
            tvAnesthetist = v.findViewById(R.id.tv_anesthetist);
            tvDate = v.findViewById(R.id.tv_date);
            tvStatus = v.findViewById(R.id.tv_status);
        }
    }
}
