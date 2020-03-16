package com.keydom.mianren.ih_patient.activity.hospital_payment.view;

import com.keydom.ih_common.base.BaseView;

import java.util.List;

/**
 * @date 20/2/26 10:47
 * @des 住院预缴金
 */
public interface HospitalPaymentView extends BaseView {

    /**
     * 数据源
     */
    void fillHospitalPaymentData(List<String> data);

}
