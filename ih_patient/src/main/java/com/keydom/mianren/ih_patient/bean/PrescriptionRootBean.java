package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @date 20/9/17 13:34
 * @des
 */
public class PrescriptionRootBean implements Serializable {
    private static final long serialVersionUID = -3603223345130802138L;

    private String cardNo;
    private String cardType;
    private String patientId;
    private String patientName;
    private String sex;
    private String age;
    private String address;
    private String phoneNumber;
    private List<PrescriptionRecordBean> item;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<PrescriptionRecordBean> getItem() {
        return item;
    }

    public void setItem(List<PrescriptionRecordBean> item) {
        this.item = item;
    }
}
