package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthManagerView;

/**
 * @date 20/3/4 10:56
 * @des 健康管理
 */
public class HealthManagerController extends ControllerImpl<HealthManagerView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
    }

    public void getHealthManagerData() {
        getView().requestSuccess();
    }
}
