package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.login.LoginActivity;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.ChooseMedicalCardActivity;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.DoctorIndexActivity;
import com.keydom.mianren.ih_patient.bean.DoctorSchedulingBean;
import com.keydom.mianren.ih_patient.constant.Global;

import java.util.List;

/**
 * 医生排班适配器
 */
public class DoctorSchedulAdapter extends RecyclerView.Adapter<DoctorSchedulAdapter.VH> {
    private DoctorIndexActivity context;
    private List<DoctorSchedulingBean> dataList;
    private String doctorName;
    private long doctorId;

    public DoctorSchedulAdapter(DoctorIndexActivity context, List<DoctorSchedulingBean> dataList, String doctorName, long doctorId) {
        this.context = context;
        this.dataList = dataList;
        this.doctorName = doctorName;
        this.doctorId = doctorId;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_register_schedule_item, parent, false);
        return new DoctorSchedulAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorSchedulAdapter.VH holder, final int position) {
        holder.schedule_date_tv.setText(dataList.get(position).getDate());
        holder.schedule_department_tv.setText(dataList.get(position).getDeptName());
        holder.schedule_time_tv.setText(dataList.get(position).getWeekDay() + "     " + dataList.get(position).getDescribe());
        holder.hospital_area_tv.setText(dataList.get(position).getHosiptalName());
        holder.register_price_tv.setText("¥" + dataList.get(position).getRegistrationFee());
        holder.register_remaining_tv.setText("余" + dataList.get(position).getNumber());
        if ("0".equals(dataList.get(position).getNumber())) {
            holder.doctor_order_tv.setText("无号");
        } else {
            holder.doctor_order_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Global.getUserId() == -1) {
                        new GeneralDialog(context, "该功能需要登录才能使用，是否立即登录？", new GeneralDialog.OnCloseListener() {
                            @Override
                            public void onCommit() {
                                LoginActivity.start(context);
                            }
                        }).setTitle("提示").setCancel(false).setPositiveButton("登录").show();
                    } else
                        ChooseMedicalCardActivity.start(context, dataList.get(position), doctorName, doctorId, dataList.get(position).getDescribe(), dataList.get(position).getDeptId(), dataList.get(position).getRegisterBackNo());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView schedule_date_tv, schedule_department_tv, schedule_time_tv, hospital_area_tv, register_price_tv, register_remaining_tv, doctor_order_tv;

        public VH(View v) {
            super(v);
            schedule_date_tv = v.findViewById(R.id.schedule_date_tv);
            schedule_department_tv = v.findViewById(R.id.schedule_department_tv);
            schedule_time_tv = v.findViewById(R.id.schedule_time_tv);
            hospital_area_tv = v.findViewById(R.id.hospital_area_tv);
            register_price_tv = v.findViewById(R.id.register_price_tv);
            register_remaining_tv = v.findViewById(R.id.register_remaining_tv);
            doctor_order_tv = v.findViewById(R.id.doctor_order_tv);
        }
    }
}
