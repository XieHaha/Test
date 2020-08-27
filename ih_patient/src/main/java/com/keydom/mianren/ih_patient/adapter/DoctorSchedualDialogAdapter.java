package com.keydom.mianren.ih_patient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.DoctorScheduling;
import com.keydom.mianren.ih_patient.constant.FunctionIndex;

import java.util.List;

/**
 * 产检预约排班医生适配器
 *
 * @author 顿顿
 */
public class DoctorSchedualDialogAdapter extends RecyclerView.Adapter<DoctorSchedualDialogAdapter.VH> implements FunctionIndex {
    private Context context;
    private List<DoctorScheduling> doctorSchedulings;

    /**
     * 构造方法
     */
    public DoctorSchedualDialogAdapter(Context context, List<DoctorScheduling> doctorSchedulings) {
        this.context = context;
        this.doctorSchedulings = doctorSchedulings;
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView doctorName, week, day;

        public VH(View v) {
            super(v);
            doctorName = v.findViewById(R.id.dialog_doctor_name_tv);
            week = v.findViewById(R.id.dialog_week_tv);
            day = v.findViewById(R.id.dialog_day_tv);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_schedual_dialog,
                        parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH vh, @SuppressLint("RecyclerView") final int position) {
        DoctorScheduling bean = doctorSchedulings.get(position);
        vh.doctorName.setText(bean.getDoctors());
        vh.week.setText(bean.getWeekName());
        vh.day.setText(bean.getTimeFrame());
    }

    @Override
    public int getItemCount() {
        return doctorSchedulings.size();
    }
}
