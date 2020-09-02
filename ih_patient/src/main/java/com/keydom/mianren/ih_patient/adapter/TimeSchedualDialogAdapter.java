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
import com.keydom.mianren.ih_patient.bean.PregnancyOrderTime;
import com.keydom.mianren.ih_patient.constant.FunctionIndex;
import com.keydom.mianren.ih_patient.view.TimeSchedualDialog;

import java.util.List;

/**
 * 产检预约排班医生适配器
 *
 * @author 顿顿
 */
public class TimeSchedualDialogAdapter extends RecyclerView.Adapter<TimeSchedualDialogAdapter.VH> implements FunctionIndex {
    private Context context;
    private List<PregnancyOrderTime> doctorSchedulings;
    TimeSchedualDialog.OnSelectListener onSelectListener;

    /**
     * 构造方法
     */
    public TimeSchedualDialogAdapter(Context context, List<PregnancyOrderTime> doctorSchedulings,
                                     TimeSchedualDialog.OnSelectListener onSelectListener) {
        this.context = context;
        this.doctorSchedulings = doctorSchedulings;
        this.onSelectListener = onSelectListener;
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView doctorName;

        public VH(View v) {
            super(v);
            doctorName = v.findViewById(R.id.dialog_time_tv);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_schedual_dialog,
                        parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH vh, @SuppressLint("RecyclerView") final int position) {
        PregnancyOrderTime bean = doctorSchedulings.get(position);
        vh.doctorName.setText(bean.getTimeInterval());
        vh.doctorName.setSelected(bean.isSelect());
        vh.doctorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.isSelect() && onSelectListener != null) {
                    onSelectListener.onSelected(bean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorSchedulings.size();
    }
}
