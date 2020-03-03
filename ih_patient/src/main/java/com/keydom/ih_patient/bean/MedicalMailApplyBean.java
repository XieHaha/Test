package com.keydom.ih_patient.bean;

import java.io.Serializable;

/**
 * @date 20/3/2 15:27
 * @des 发起病案邮寄 bean
 */
public class MedicalMailApplyBean implements Serializable {
    private static final long serialVersionUID = 1713887449494863054L;

    private String frontUrl;
    private String backUrl;
    private String handUrl;
    private String patientName;
    private String patientIdCard;
    private String patientPhone;

    public String getFrontUrl() {
        return frontUrl;
    }

    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getHandUrl() {
        return handUrl;
    }

    public void setHandUrl(String handUrl) {
        this.handUrl = handUrl;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientIdCard() {
        return patientIdCard;
    }

    public void setPatientIdCard(String patientIdCard) {
        this.patientIdCard = patientIdCard;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }
}
