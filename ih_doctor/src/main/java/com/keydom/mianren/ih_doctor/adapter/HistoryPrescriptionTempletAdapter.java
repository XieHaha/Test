package com.keydom.mianren.ih_doctor.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.DoctorPrescriptionDetailBean;
import com.keydom.mianren.ih_doctor.bean.ICD10Bean;

import java.util.List;

public class HistoryPrescriptionTempletAdapter extends BaseQuickAdapter<DoctorPrescriptionDetailBean, BaseViewHolder> {
    public HistoryPrescriptionTempletAdapter(@Nullable List<DoctorPrescriptionDetailBean> data) {
        super(R.layout.history_prescription_templet_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoctorPrescriptionDetailBean item) {
        helper.setText(R.id.template_time_tv, item.getTime())
                .setText(R.id.template_idc_tv, getIcd(item));
    }

    private String getIcd(DoctorPrescriptionDetailBean item) {
        StringBuilder builder = new StringBuilder();
        List<ICD10Bean> beans = item.getIdcItems();
        if (beans != null && beans.size() > 0) {
            for (int i = 0; i < beans.size(); i++) {
                ICD10Bean bean = beans.get(i);
                builder.append(bean.getName());
                if (beans.size() - 1 > i) {
                    builder.append(",");
                }
            }
            return builder.toString();
        } else {
            return "";
        }
    }
}
