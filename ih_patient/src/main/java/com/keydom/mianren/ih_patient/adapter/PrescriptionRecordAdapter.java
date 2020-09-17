package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.PrescriptionRecordBean;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import java.util.List;

/**
 * des:处方单记录适配器
 *
 * @author 顿顿
 */
public class PrescriptionRecordAdapter extends BaseQuickAdapter<PrescriptionRecordBean,
        BaseViewHolder> {
    public PrescriptionRecordAdapter(@Nullable List<PrescriptionRecordBean> data) {
        super(R.layout.item_medical_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PrescriptionRecordBean item) {
        helper.setText(R.id.medical_record_date_tv, DateUtils.transDate(item.getCreateTime(),
                DateUtils.YYYY_MM_DD_HH_MM_SS_SLASH, DateUtils.YYYY_MM_DD_CH))
                .setText(R.id.medical_record_week_tv, item.getDeptName());
    }
}
