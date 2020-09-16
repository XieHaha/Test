package com.keydom.mianren.ih_patient.activity.hospital_payment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.HospitalRecordRootBean;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;

import java.util.List;

/**
 * @author 顿顿
 * @date 20/2/26 10:47
 * @des 住院预缴金
 */
public interface HospitalPaymentView extends BaseView {

    String getMedicalCardNumber();

    String getFee();

    MedicalCardInfo getMedicalCardInfo();

    String getInHospitalNo();

    void fillHospitalPaymentData(HospitalRecordRootBean data);

    void fillHospitalPaymentFailed(String error);

    void getAllCardSuccess(List<MedicalCardInfo> data);

    void createOrderSuccess();
}
