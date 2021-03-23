package com.keydom.mianren.ih_patient.activity.chronic_disease.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.chronic_disease.view.LifestyleMainView;
import com.keydom.mianren.ih_patient.view.HealthDataEditDialog;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 生活记录
 */
public class LifestyleMainController extends ControllerImpl<LifestyleMainView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.disease_main_data_hint_tv:
                HealthDataEditDialog dialog = new HealthDataEditDialog(getContext(),
                        new HealthDataEditDialog.OnCommitListener() {
                            @Override
                            public void backHealthManager() {

                            }

                            @Override
                            public void backHome() {

                            }
                        });
                dialog.show();
                break;
            case R.id.lifestyle_bottom_cancel_tv:
                break;
            case R.id.lifestyle_bottom_submit_tv:
                break;
            default:
                break;
        }
    }
}
