package com.keydom.ih_patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_doctor_register.DoctorIndexActivity;
import com.keydom.ih_patient.bean.DepartmentSchedulingBean;

import java.util.List;

/**
 * 医生适配器
 */
public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.VH> {
    private Context context;
    private List<DepartmentSchedulingBean> dataList;

    /**
     * 构造方法
     */
    public DoctorAdapter(Context context, List<DepartmentSchedulingBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_register_doctor_item, parent, false);
        return new DoctorAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        if (dataList.get(position).getHUser() != null) {
            holder.doctor_name_tv.setText(dataList.get(position).getHUser().getName() == null || "".equals(dataList.get(position).getHUser().getName()) ? "" : dataList.get(position).getHUser().getName());
            holder.doctor_dsc_tv.setText("擅长:" + dataList.get(position).getHUser().getAdept());
            if (dataList.get(position).getHUser().getJobTitleName() != null && !"".equals(dataList.get(position).getHUser().getJobTitleName()))
                holder.main_doctor_label.setText(dataList.get(position).getHUser().getJobTitleName());
            else
                holder.main_doctor_label.setVisibility(View.GONE);
            if (dataList.get(position).getHUser().getPopularDoctor() == 1) {
                holder.hot_doctor_label.setVisibility(View.VISIBLE);
            } else {
                holder.hot_doctor_label.setVisibility(View.GONE);
            }
            if ("1".equals(dataList.get(position).getNumber())) {
                holder.doctor_ishave_registered_tv.setText("有号");
                holder.doctor_ishave_registered_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DoctorIndexActivity.start(context, dataList.get(position).getHUser());
                    }
                });
            } else {
                holder.doctor_ishave_registered_tv.setText("无号");
                holder.doctor_ishave_registered_tv.setClickable(false);
            }
        }
        GlideUtils.load(holder.order_doctor_img, Const.IMAGE_HOST + dataList.get(position).getHUser().getImage(), R.mipmap.order_doctor_icon, R.mipmap.order_doctor_icon, false, null);


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView doctor_name_tv, main_doctor_label, hot_doctor_label, doctor_dsc_tv, doctor_ishave_registered_tv;
        private CircleImageView order_doctor_img;

        public VH(View v) {
            super(v);
            doctor_name_tv = v.findViewById(R.id.doctor_name_tv);
            main_doctor_label = v.findViewById(R.id.main_doctor_label);
            hot_doctor_label = v.findViewById(R.id.hot_doctor_label);
            doctor_dsc_tv = v.findViewById(R.id.doctor_dsc_tv);
            doctor_ishave_registered_tv = v.findViewById(R.id.doctor_ishave_registered_tv);
            order_doctor_img = v.findViewById(R.id.order_doctor_img);
        }
    }
}
