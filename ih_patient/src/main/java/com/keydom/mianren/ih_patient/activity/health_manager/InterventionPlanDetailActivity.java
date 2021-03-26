package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.InterventionPlanDetailController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.InterventionPlanDetailView;

import org.jetbrains.annotations.Nullable;

/**
 * @author 顿顿
 * @date 20/3/4 10:54
 * @des 干预方案
 */
public class InterventionPlanDetailActivity extends BaseControllerActivity<InterventionPlanDetailController> implements InterventionPlanDetailView {

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, InterventionPlanDetailActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_intervention_plan_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
    }

}
