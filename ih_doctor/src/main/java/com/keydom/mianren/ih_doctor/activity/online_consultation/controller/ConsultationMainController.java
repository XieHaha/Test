package com.keydom.mianren.ih_doctor.activity.online_consultation.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationMainView;

/**
 * @date 20/4/29 19:45
 * @des
 */
public class ConsultationMainController extends ControllerImpl<ConsultationMainView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.consultation_main_apply_tv:
                getView().tabApplyView();
                break;
            case R.id.consultation_main_history_tv:
                getView().tabHistoryView();
                break;
            default:
                break;
        }
    }
}
