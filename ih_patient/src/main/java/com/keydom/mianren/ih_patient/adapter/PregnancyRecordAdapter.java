package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.PregnancyRecordBean;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import java.util.List;

/**
 * des:门诊病历记录适配器
 *
 * @author 顿顿
 */
public class PregnancyRecordAdapter extends BaseQuickAdapter<PregnancyRecordBean, BaseViewHolder> {
    public PregnancyRecordAdapter(@Nullable List<PregnancyRecordBean> data) {
        super(R.layout.item_pregnancy_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PregnancyRecordBean item) {
        helper.setText(R.id.pregnancy_record_date_tv, DateUtils.transDate(item.getCheckDate(),DateUtils.YYYY_MM_DD_HH_MM_SS,DateUtils.YYYY_MM_DD_CH))
                .setText(R.id.pregnancy_record_week_tv, "孕" + item.getPregnancyWeek() + "周");
    }
}
