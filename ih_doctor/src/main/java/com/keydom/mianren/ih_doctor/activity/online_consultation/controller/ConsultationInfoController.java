package com.keydom.mianren.ih_doctor.activity.online_consultation.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationInfoView;

/**
 * @date 20/4/8 14:39
 * @des 会诊室-病历资料
 */
public class ConsultationInfoController extends ControllerImpl<ConsultationInfoView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.consultation_info_apply_layout:
                getView().onApplyInfoSelect();
                break;
            case R.id.consultation_info_medical_layout:
                getView().onMedicalSelect();
                break;
            case R.id.consultation_info_report_layout:
                getView().onReportInfoSelect();
                break;
            case R.id.consultation_info_video_layout:
                getView().onVideoSelect();
                break;
            default:
                break;
        }
    }
}
