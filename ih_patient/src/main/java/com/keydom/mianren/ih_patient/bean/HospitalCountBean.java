package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 顿顿
 * @date 20/9/11 17:27
 * @des 住院次数
 */
public class HospitalCountBean implements Serializable {
    private static final long serialVersionUID = -2155850965881493677L;

    private String cardNo;
    private String patientName;
    private String phoneNumber;
    private String idcardNo;
    private List<HospitalInfoBean> item;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public List<HospitalInfoBean> getItem() {
        return item;
    }

    public void setItem(List<HospitalInfoBean> item) {
        this.item = item;
    }
}
