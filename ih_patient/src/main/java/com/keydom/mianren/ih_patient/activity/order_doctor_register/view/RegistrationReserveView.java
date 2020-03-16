package com.keydom.mianren.ih_patient.activity.order_doctor_register.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.ReserveSelectDepartBean;

import java.util.Date;

/**
 * @date 20/3/5 16:46
 * @des 挂号预约view
 */
public interface RegistrationReserveView extends BaseView {
    long getCurrentUserId();

    void setVisitDate(Date visitDate);

    String getVisitDate();

    /**
     * 1、上午   2、下午  3、晚上
     */
    void setSelectTimeInterVal(int timeInterVal);

    /**
     * 数据集合
     */
    ReserveSelectDepartBean getReserveSelectDepart();

    long getDoctorId();
}
