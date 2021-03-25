package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.InterventionPlanBean;

import java.util.List;

/**
 * 干预方案
 *
 * @author 顿顿
 */
public class InterventionPlanAdapter extends BaseQuickAdapter<InterventionPlanBean,
        BaseViewHolder> {

    public InterventionPlanAdapter(@Nullable List<InterventionPlanBean> data) {
        super(R.layout.iten_intervention_plan, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InterventionPlanBean item) {
        helper.setText(R.id.item_intervention_plan_title_tv, "年度干预")
                .setText(R.id.item_intervention_plan_doctor_tv, "医生")
                .setText(R.id.item_intervention_plan_content_tv, "内容内容内容内容内容内容内容内容内容内容")
                .addOnClickListener(R.id.item_intervention_plan_look_tv);
    }

}
