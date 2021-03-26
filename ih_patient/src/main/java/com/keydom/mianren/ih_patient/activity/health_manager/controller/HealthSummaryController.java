package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_patient.activity.health_manager.InterventionPlanDetailActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthSummaryView;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 健康总结
 */
public class HealthSummaryController extends ControllerImpl<HealthSummaryView> implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        InterventionPlanDetailActivity.start(getContext());
    }
}
