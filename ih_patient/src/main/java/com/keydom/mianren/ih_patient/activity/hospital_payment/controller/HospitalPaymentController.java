package com.keydom.mianren.ih_patient.activity.hospital_payment.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_patient.activity.hospital_payment.view.HospitalPaymentView;

import java.util.ArrayList;

/**
 * 住院预缴金
 */
public class HospitalPaymentController extends ControllerImpl<HospitalPaymentView> {

    /**
     * 获取产后康复数据
     */
    public void getHospitalPayment() {
        getView().fillHospitalPaymentData(new ArrayList<>());
    }

}
