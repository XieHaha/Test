package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @date 20/3/4 17:13
 * @des 无痛分娩预约
 */
public class PainlessDeliveryBean implements Serializable {
    private static final long serialVersionUID = -6924784896437675534L;

    private String age;
    private int embryoNumber;
    private int isConfirm;
    private int isDel;
    private String id;
    private String patientId;
    private String patientName;
    private String lastMenstrualPeriodTime;
    private String expectedDateOfConfinement;
    private String phoneNumber;
    private String appointmentDate;
    private String registerUserId;
    private String createTime;
    private String eleCardNumber;
    private String hospitalId;
    private String confirmTime;


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getEmbryoNumber() {
        return embryoNumber;
    }

    public void setEmbryoNumber(int embryoNumber) {
        this.embryoNumber = embryoNumber;
    }

    public int getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(int isConfirm) {
        this.isConfirm = isConfirm;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLastMenstrualPeriodTime() {
        return lastMenstrualPeriodTime;
    }

    public void setLastMenstrualPeriodTime(String lastMenstrualPeriodTime) {
        this.lastMenstrualPeriodTime = lastMenstrualPeriodTime;
    }

    public String getExpectedDateOfConfinement() {
        return expectedDateOfConfinement;
    }

    public void setExpectedDateOfConfinement(String expectedDateOfConfinement) {
        this.expectedDateOfConfinement = expectedDateOfConfinement;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(String registerUserId) {
        this.registerUserId = registerUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEleCardNumber() {
        return eleCardNumber;
    }

    public void setEleCardNumber(String eleCardNumber) {
        this.eleCardNumber = eleCardNumber;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }
}
