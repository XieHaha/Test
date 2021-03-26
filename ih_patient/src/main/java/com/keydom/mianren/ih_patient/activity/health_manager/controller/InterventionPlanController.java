package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.InterventionPlanDetailActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.view.InterventionPlanView;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 干预方案
 */
public class InterventionPlanController extends ControllerImpl<InterventionPlanView> implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getView().finishPage();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        InterventionPlanDetailActivity.start(getContext());
    }
}
