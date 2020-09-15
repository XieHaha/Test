package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.PregnancyRecordBean;

import java.util.List;

/**
 * des:门诊病历记录适配器
 *
 * @author 顿顿
 */
public class PregnancyRecordAdapter extends BaseQuickAdapter<PregnancyRecordBean, BaseViewHolder> {
    public PregnancyRecordAdapter(@Nullable List<PregnancyRecordBean> data) {
        super(R.layout.item_medical_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PregnancyRecordBean item) {
    }
}
