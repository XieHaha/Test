package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @date 20/6/12 14:03
 * @des 产检预约项目
 */
public class CheckProjectRootBean implements Serializable {
    private static final long serialVersionUID = 5482199173003538016L;
    private String respCode;
    private String respMessage;
    private String cardNo;
    private String patientName;
    private String sex;
    private String age;
    private String phoneNumber;
    private List<CheckProjectBean> item;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<CheckProjectBean> getItem() {
        return item;
    }

    public void setItem(List<CheckProjectBean> item) {
        this.item = item;
    }
}
