package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.HealthSummaryActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.InterventionPlanActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.LifestyleMainActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.view.ChronicDiseaseMainView;
import com.keydom.mianren.ih_patient.view.HealthDataEditDialog;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 慢病管理
 */
public class ChronicDiseaseMainController extends ControllerImpl<ChronicDiseaseMainView> implements View.OnClickListener {
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
            case R.id.disease_main_eat_record_layout:
                LifestyleMainActivity.start(getContext(), LifestyleMainActivity.LIFESTYLE_DIET);
                break;
            case R.id.disease_main_sleep_record_layout:
                LifestyleMainActivity.start(getContext(), LifestyleMainActivity.LIFESTYLE_SLEEP);
                break;
            case R.id.disease_main_sports_record_layout:
                LifestyleMainActivity.start(getContext(), LifestyleMainActivity.LIFESTYLE_SPORTS);
                break;
            case R.id.disease_main_intervention_plan_layout:
                InterventionPlanActivity.start(getContext());
                break;
            case R.id.disease_main_health_summary_layout:
                HealthSummaryActivity.start(getContext());
                break;
            default:
                break;
        }
    }
}
