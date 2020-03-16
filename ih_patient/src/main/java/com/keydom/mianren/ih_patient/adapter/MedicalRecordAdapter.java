package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.MedicalRecordBean;

import java.util.List;

/**
 * created date: 2019/1/4 on 15:44
 * des:处方记录适配器
 */
public class MedicalRecordAdapter extends BaseQuickAdapter<MedicalRecordBean,BaseViewHolder>{
    public MedicalRecordAdapter(@Nullable List<MedicalRecordBean> data) {
        super(R.layout.item_medical_record,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MedicalRecordBean item) {
//        type 0诊疗 1咨询
        helper.setText(R.id.time,item.getConsultTime()+"")
                .setText(R.id.content,item.getDiagnosis()+"")
                .setText(R.id.type,item.getType()==0?"诊疗":"咨询");

    }
}
