package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.HealthSummaryBean;

import java.util.List;

/**
 * 干预方案
 *
 * @author 顿顿
 */
public class HealthSummaryAdapter extends BaseQuickAdapter<HealthSummaryBean,
        BaseViewHolder> {

    public HealthSummaryAdapter(@Nullable List<HealthSummaryBean> data) {
        super(R.layout.iten_health_summary, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HealthSummaryBean item) {
        helper.setText(R.id.item_health_summary_title_tv, "年度健康报告")
                .setText(R.id.item_health_summary_doctor_tv, "医生")
                .setText(R.id.item_health_summary_content_tv, "内容内容内容内容内容内容内容内容内容内容");
    }

}
