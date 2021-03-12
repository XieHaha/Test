package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.HealthArchivesActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthManagerView;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 健康管理
 */
public class HealthManagerController extends ControllerImpl<HealthManagerView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.health_manager_archives_layout:
                HealthArchivesActivity.start(getContext());
                break;
            case R.id.health_manager_report_layout:
                break;
            case R.id.health_manager_online_layout:
                break;
            case R.id.health_manager_cardiovascular_layout:
                break;
            case R.id.health_manager_hypertension_layout:
                break;
            case R.id.health_manager_diabetes_layout:
                break;
            default:
                break;
        }
    }
}
