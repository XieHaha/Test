package com.keydom.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.diagnose_main.DiagnoseMainActivity;
import com.keydom.ih_patient.activity.health_manager.view.HealthManagerView;

/**
 * @date 20/3/4 10:56
 * @des 健康管理
 */
public class HealthManagerController extends ControllerImpl<HealthManagerView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getView().finishPage();
                break;
            case R.id.iv_online:
                DiagnoseMainActivity.start(getContext());
                break;
            default:
                break;
        }
    }
}
