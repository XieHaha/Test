package com.keydom.mianren.ih_patient.activity.order_doctor_register.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_common.bean.DoctorInfo;

/**
 * 医生详情view
 */
public interface DoctorIndexView extends BaseView {
    /**
     * 获取详情成功
     */
    void getDoctorDetailSuccess(DoctorInfo doctorInfo);

    /**
     * 获取失败
     */
    void getDoctorDetailFailed(String errMsg);


}
