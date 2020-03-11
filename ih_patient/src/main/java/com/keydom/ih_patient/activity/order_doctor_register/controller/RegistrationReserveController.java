package com.keydom.ih_patient.activity.order_doctor_register.controller;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.diagnose_user_manager.ManageUserSelectActivity;
import com.keydom.ih_patient.activity.order_doctor_register.ChooseDoctorActivity;
import com.keydom.ih_patient.activity.order_doctor_register.OrderDoctorRegisterActivity;
import com.keydom.ih_patient.activity.order_doctor_register.view.RegistrationReserveView;
import com.keydom.ih_patient.bean.ReserveSelectDepartBean;

/**
 * @date 20/3/5 16:46
 * @des 挂号预约
 */
public class RegistrationReserveController extends ControllerImpl<RegistrationReserveView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_visit:
                ManageUserSelectActivity.start(getContext(), getView().getCurrentUserId());
                break;
            case R.id.layout_date:
                KeyboardUtils.hideSoftInput((Activity) getContext());
                TimePickerView pvTime = new TimePickerBuilder(getContext(),
                        (date, v1) -> getView().setVisitDate(date)).build();
                pvTime.show();
                break;
            case R.id.layout_depart:
                if (TextUtils.isEmpty(getView().getVisitDate())) {
                    ToastUtil.showMessage(getContext(), R.string.txt_visit_date_hint);
                    return;
                }
                OrderDoctorRegisterActivity.start(getContext(), true);
                break;
            case R.id.layout_doctor:
                ReserveSelectDepartBean bean = getView().getReserveSelectDepart();
                if (bean == null) {
                    ToastUtil.showMessage(getContext(), R.string.txt_depart_hint);
                    return;
                } else {
                    ChooseDoctorActivity.start(getContext(), bean, getView().getVisitDate(),
                            getView().getDoctorId(), true);
                }
                break;
            case R.id.tv_morning:
                getView().setSelectTimeInterVal(1);
                break;
            case R.id.tv_afternoon:
                getView().setSelectTimeInterVal(2);
                break;
            case R.id.tv_night:
                getView().setSelectTimeInterVal(3);
                break;
            case R.id.tv_next:
                break;
            default:
                break;
        }
    }
}
