package com.keydom.ih_patient.activity.reserve_amniocentesis.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisEvaluateView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;

/**
 * @date 20/3/9 16:09
 * @des 羊水穿刺预约评估
 */
public class AmniocentesisEvaluateController extends ControllerImpl<AmniocentesisEvaluateView> implements IhTitleLayout.OnRightTextClickListener, View.OnClickListener {
    @Override
    public void OnRightTextClick(View v) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.amniocentesis_evaluate_one_fetus_layout:
                getView().onFetusSelect(1);
                break;
            case R.id.amniocentesis_evaluate_two_fetus_layout:
                getView().onFetusSelect(2);
                break;
            case R.id.amniocentesis_evaluate_more_fetus_layout:
                getView().onFetusSelect(3);
                break;
            case R.id.amniocentesis_evaluate_hiv_negative_layout:
                getView().onHivSelect(1);
                break;
            case R.id.amniocentesis_evaluate_hiv_positive_layout:
                getView().onHivSelect(2);
                break;
            case R.id.amniocentesis_evaluate_blood_negative_layout:
                getView().onBloodSelect(1);
                break;
            case R.id.amniocentesis_evaluate_blood_positive_layout:
                getView().onBloodSelect(2);
                break;
            case R.id.amniocentesis_evaluate_ultrasound_yes_layout:
                getView().onUltrasoundSelect(1);
                break;
            case R.id.amniocentesis_evaluate_ultrasound_no_layout:
                getView().onUltrasoundSelect(2);
                break;
            case R.id.amniocentesis_evaluate_hypertension_have_layout:
                getView().onHypertensionSelect(1);
                break;
            case R.id.amniocentesis_evaluate_hypertension_none_layout:
                getView().onHypertensionSelect(2);
                break;
            case R.id.amniocentesis_evaluate_diabetes_have_layout:
                getView().onDiabetesSelect(1);
                break;
            case R.id.amniocentesis_evaluate_diabetes_none_layout:
                getView().onDiabetesSelect(2);
                break;
            case R.id.amniocentesis_evaluate_next_tv:
                EventBus.getDefault().post(new Event(EventType.AMNIOCENTESIS_EVALUATE, null));
                break;
            default:
                break;
        }
    }

}
