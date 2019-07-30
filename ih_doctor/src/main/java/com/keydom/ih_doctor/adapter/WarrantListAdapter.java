package com.keydom.ih_doctor.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.patient_manage.TentativeDiagnosisActivity;
import com.keydom.ih_doctor.bean.DeptDoctorBean;
import com.keydom.ih_doctor.bean.WarrantDoctorBean;
import com.keydom.ih_doctor.constant.Const;
import java.util.List;

public class WarrantListAdapter extends BaseEmptyAdapter<WarrantDoctorBean> {

    private List<WarrantDoctorBean> selectList;
    private Context context;

    public WarrantListAdapter(Context context, List<WarrantDoctorBean> data) {
        super(data, context);
        this.selectList = selectList;
        this.context=context;


    }


    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.select_doctor_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((WarrantListAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView doctorIcon;
        public TextView doctorName, doctorJob, editTv;

        public ViewHolder(View itemView) {
            super(itemView);
            doctorIcon = itemView.findViewById(R.id.doctor_icon);
            doctorName = itemView.findViewById(R.id.doctor_name);
            doctorJob = itemView.findViewById(R.id.doctor_job);
            editTv = itemView.findViewById(R.id.select_tv);

        }

        public void bind(final int position) {
            editTv.setBackground(mContext.getResources().getDrawable(R.drawable.doctor_unselect_bg));
            editTv.setTextColor(mContext.getResources().getColor(R.color.white));
            editTv.setText("编辑");
            final WarrantDoctorBean bean = mDatas.get(position);
            GlideUtils.load(doctorIcon, Const.IMAGE_HOST + bean.getImage(), 0, 0, false, null);

            doctorJob.setText(bean.getJobTitleName());
            doctorName.setText(bean.getName());
            editTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TentativeDiagnosisActivity.start(context,TentativeDiagnosisActivity.TYPEUPDATE,bean.getHospitalUserId());
                }
            });
        }
    }

}
