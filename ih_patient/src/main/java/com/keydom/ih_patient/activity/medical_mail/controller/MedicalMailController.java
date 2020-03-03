package com.keydom.ih_patient.activity.medical_mail.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.medical_mail.view.MedicalMailView;

/**
 * @date 20/3/2 15:08
 * @des 发起病案邮寄控制器
 */
public class MedicalMailController extends ControllerImpl<MedicalMailView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getView().finishPage();
                break;
        }
    }
}
