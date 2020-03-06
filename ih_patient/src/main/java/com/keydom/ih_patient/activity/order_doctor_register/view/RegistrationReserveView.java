package com.keydom.ih_patient.activity.order_doctor_register.view;

import com.keydom.ih_common.base.BaseView;

import java.util.Date;

/**
 * @date 20/3/5 16:46
 * @des 挂号预约view
 */
public interface RegistrationReserveView extends BaseView {
    long getCurrentUserId();

    void setVisitDate(Date visitDate);
}
