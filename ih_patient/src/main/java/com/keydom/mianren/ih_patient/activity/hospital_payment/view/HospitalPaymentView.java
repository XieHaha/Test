package com.keydom.mianren.ih_patient.activity.hospital_payment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.HospitalRecordRootBean;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;

import java.util.List;

/**
 * @date 20/2/26 10:47
 * @des 住院预缴金
 */
public interface HospitalPaymentView extends BaseView {

    String getMedicalCardNumber();

    MedicalCardInfo getMedicalCardInfo();

    /**
     * 数据源
     */
    void fillHospitalPaymentData(HospitalRecordRootBean data);

    void getAllCardSuccess(List<MedicalCardInfo> data);

}
