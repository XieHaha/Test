package com.keydom.ih_patient.activity.order_doctor_register.controller;

import android.app.Activity;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.diagnose_user_manager.ManageUserNewActivity;
import com.keydom.ih_patient.activity.order_doctor_register.view.RegistrationReserveView;

/**
 * @date 20/3/5 16:46
 * @des 挂号预约
 */
public class RegistrationReserveController extends ControllerImpl<RegistrationReserveView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_visit:
                ManageUserNewActivity.start(getContext(), getView().getCurrentUserId(),
                        ManageUserNewActivity.FROM_SELECT);
                break;
            case R.id.layout_date:
                KeyboardUtils.hideSoftInput((Activity) getContext());
                TimePickerView pvTime = new TimePickerBuilder(getContext(),
                        (date, v1) -> getView().setVisitDate(date)).build();
                pvTime.show();
                break;
            case R.id.layout_depart:
                break;
            case R.id.layout_doctor:
                break;
            case R.id.tv_morning:
                break;
            case R.id.tv_afternoon:
                break;
            case R.id.tv_night:
                break;
            case R.id.tv_next:
                break;
            default:
                break;
        }
    }
}
