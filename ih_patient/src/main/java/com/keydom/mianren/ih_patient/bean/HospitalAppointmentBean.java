package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @author 顿顿
 * @date 20/9/3 14:37
 * @des
 */
public class HospitalAppointmentBean implements Serializable {
    private static final long serialVersionUID = -3870001787308234974L;
    private int surgeonId;
    private int state;
    private String id;
    private String appointmentTime;
    private String bed;
    private String surgeonName;
    private String anesthetistId;
    private String anesthetistName;
    private String patientId;
    private String patientName;
    private String age;
    private String lastMenstrualPeriodTime;
    private String expectedDateOfConfinement;
    private String embryoNumber;
    private String phoneNumber;
    private String createTime;
    private String registerUserId;
    private String eleCardNumber;
    private String deptName;
    private String deptId;
    private String inHospitalTime;

    public int getSurgeonId() {
        return surgeonId;
    }

    public void setSurgeonId(int surgeonId) {
        this.surgeonId = surgeonId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getSurgeonName() {
        return surgeonName;
    }

    public void setSurgeonName(String surgeonName) {
        this.surgeonName = surgeonName;
    }

    public String getAnesthetistId() {
        return anesthetistId;
    }

    public void setAnesthetistId(String anesthetistId) {
        this.anesthetistId = anesthetistId;
    }

    public String getAnesthetistName() {
        return anesthetistName;
    }

    public void setAnesthetistName(String anesthetistName) {
        this.anesthetistName = anesthetistName;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getEmbryoNumber() {
        return embryoNumber;
    }

    public void setEmbryoNumber(String embryoNumber) {
        this.embryoNumber = embryoNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(String registerUserId) {
        this.registerUserId = registerUserId;
    }

    public String getEleCardNumber() {
        return eleCardNumber;
    }

    public void setEleCardNumber(String eleCardNumber) {
        this.eleCardNumber = eleCardNumber;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getInHospitalTime() {
        return inHospitalTime;
    }

    public void setInHospitalTime(String inHospitalTime) {
        this.inHospitalTime = inHospitalTime;
    }
}
