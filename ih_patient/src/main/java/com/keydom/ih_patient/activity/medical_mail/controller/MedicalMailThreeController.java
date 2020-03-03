package com.keydom.ih_patient.activity.medical_mail.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.medical_mail.view.MedicalMailThreeView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;

/**
 * 病案邮寄-收件信息
 */
public class MedicalMailThreeController extends ControllerImpl<MedicalMailThreeView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_next:
                EventBus.getDefault().post(new Event(EventType.MEDICAL_STEP_THREE, ""));
                break;
            default:
                break;
        }
    }
}
