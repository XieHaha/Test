package com.keydom.ih_patient.bean.dto;

import java.io.Serializable;

/**
 * @date 20/3/4 15:29
 * @des 无痛分娩预约 dto
 */
public class MedicinePainlessLaborDto implements Serializable {
    private static final long serialVersionUID = 9185506991153769450L;
    private int age;
    private int embryoNumber;
    private long hospitalId;
    private long patientId;
    private long registerUserId;

    private String appointmentDate;
    private String eleCardNumber;
    private String expectedDateOfConfinement;
    private String lastMenstrualPeriodTime;
    private String patientName;
    private String phoneNumber;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getEmbryoNumber() {
        return embryoNumber;
    }

    public void setEmbryoNumber(int embryoNumber) {
        this.embryoNumber = embryoNumber;
    }

    public long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(long registerUserId) {
        this.registerUserId = registerUserId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getEleCardNumber() {
        return eleCardNumber;
    }

    public void setEleCardNumber(String eleCardNumber) {
        this.eleCardNumber = eleCardNumber;
    }

    public String getExpectedDateOfConfinement() {
        return expectedDateOfConfinement;
    }

    public void setExpectedDateOfConfinement(String expectedDateOfConfinement) {
        this.expectedDateOfConfinement = expectedDateOfConfinement;
    }

    public String getLastMenstrualPeriodTime() {
        return lastMenstrualPeriodTime;
    }

    public void setLastMenstrualPeriodTime(String lastMenstrualPeriodTime) {
        this.lastMenstrualPeriodTime = lastMenstrualPeriodTime;
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
}
