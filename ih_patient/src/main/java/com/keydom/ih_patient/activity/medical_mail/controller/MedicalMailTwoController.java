package com.keydom.ih_patient.activity.medical_mail.controller;

import android.text.TextUtils;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.medical_mail.view.MedicalMailTwoView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.MedicalMailApplyBean;
import com.keydom.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;

/**
 * 病案邮寄-邮寄申请
 */
public class MedicalMailTwoController extends ControllerImpl<MedicalMailTwoView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_next:
                onNext();
                break;
            default:
                break;
        }
    }

    private void onNext() {
        if (TextUtils.isEmpty(getView().getName())) {
            ToastUtil.showMessage(getContext(), "患者姓名不能为空");
            return;
        }
        MedicalMailApplyBean bean = getView().getApplyData();
        bean.setPatientName(getView().getName());
        EventBus.getDefault().post(new Event(EventType.MEDICAL_STEP_TWO, bean));
    }
}
