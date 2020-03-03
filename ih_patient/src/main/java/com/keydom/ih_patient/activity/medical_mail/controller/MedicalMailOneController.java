package com.keydom.ih_patient.activity.medical_mail.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.medical_mail.view.MedicalMailOneView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;

/**
 * 病案邮寄-身份认证
 */
public class MedicalMailOneController extends ControllerImpl<MedicalMailOneView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_select_visit:
                break;
            case R.id.layout_front:
                ToastUtil.showMessage(getContext(), "ceshi ");
                break;
            case R.id.layout_back:
                break;
            case R.id.layout_hand:
                break;
            case R.id.tv_next:
                EventBus.getDefault().post(new Event(EventType.MEDICAL_STEP_ONE, ""));
                break;
            default:
                break;
        }
    }
}
