package com.keydom.mianren.ih_patient.activity.medical_mail.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.medical_mail.view.MedicalMailCommitView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;

/**
 * 病案邮寄-订单确认
 */
public class MedicalMailCommitController extends ControllerImpl<MedicalMailCommitView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_go_pay:
                EventBus.getDefault().post(new Event(EventType.MEDICAL_STEP_END, ""));
                break;
            default:
                break;
        }
    }
}
