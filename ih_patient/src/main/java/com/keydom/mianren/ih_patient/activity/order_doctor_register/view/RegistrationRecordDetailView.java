package com.keydom.mianren.ih_patient.activity.order_doctor_register.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.RegistrationRecordInfo;

/**
 * 预约详情view
 */
public interface RegistrationRecordDetailView extends BaseView {
    /**
     * 获取订单详情
     */
    void getRegistrationRecordDetailSuccess(RegistrationRecordInfo registrationRecordInfo);

    /**
     * 获取失败
     */
    void getRegistrationRecordDetailFailed(String errMsg);
}
