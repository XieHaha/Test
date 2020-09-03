package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.HospitalAppointmentBean;

import java.util.List;

/**
 * 产科住院
 *
 * @author 顿顿
 */
public class ObstetricRecordAdapter extends BaseQuickAdapter<HospitalAppointmentBean,
        BaseViewHolder> {

    public ObstetricRecordAdapter(@Nullable List<HospitalAppointmentBean> data) {
        super(R.layout.item_obstetric_hospital, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HospitalAppointmentBean item) {
        helper.setText(R.id.tv_name, item.getPatientName())
                .setText(R.id.tv_date, item.getAppointmentTime())
                .setText(R.id.tv_bed, item.getBed())
                .setText(R.id.tv_doctor, item.getSurgeonName())
                .setText(R.id.tv_anesthetist, item.getAnesthetistName())
                .setText(R.id.tv_status, item.getState() == 0 ? "未住院" : "已住院");
    }
}
