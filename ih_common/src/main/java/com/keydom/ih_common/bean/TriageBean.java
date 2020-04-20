package com.keydom.ih_common.bean;

import java.io.Serializable;

/**
 * @date 20/4/16 17:36
 * @des 分诊
 */
public class TriageBean implements Serializable {
    private static final long serialVersionUID = -3875881894697736075L;
    private int doctorId;
    private String patientSex;
    private String orderId;
    private String id;
    private String hospitalId;
    private String triageExplain;
    private String patientName;
    private String patientAge;
    private String diseaseData;
    private String dept;
    private String doctorAvatar;
    private String doctor;
    private String jobTitle;
    private String triageTime;
    private String changeInfoDept;
    private String changeInfoJobTitle;
    private String changeInfoDoctorId;
    private String changeInfoDoctorCode;
    private String changeInfoDoctor;
    private String voiceUrl;
    private String groupTid;

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getTriageExplain() {
        return triageExplain;
    }

    public void setTriageExplain(String triageExplain) {
        this.triageExplain = triageExplain;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getDiseaseData() {
        return diseaseData;
    }

    public void setDiseaseData(String diseaseData) {
        this.diseaseData = diseaseData;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDoctorAvatar() {
        return doctorAvatar;
    }

    public void setDoctorAvatar(String doctorAvatar) {
        this.doctorAvatar = doctorAvatar;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getTriageTime() {
        return triageTime;
    }

    public void setTriageTime(String triageTime) {
        this.triageTime = triageTime;
    }

    public String getChangeInfoDept() {
        return changeInfoDept;
    }

    public void setChangeInfoDept(String changeInfoDept) {
        this.changeInfoDept = changeInfoDept;
    }

    public String getChangeInfoJobTitle() {
        return changeInfoJobTitle;
    }

    public void setChangeInfoJobTitle(String changeInfoJobTitle) {
        this.changeInfoJobTitle = changeInfoJobTitle;
    }

    public String getChangeInfoDoctorId() {
        return changeInfoDoctorId;
    }

    public void setChangeInfoDoctorId(String changeInfoDoctorId) {
        this.changeInfoDoctorId = changeInfoDoctorId;
    }

    public String getChangeInfoDoctorCode() {
        return changeInfoDoctorCode;
    }

    public void setChangeInfoDoctorCode(String changeInfoDoctorCode) {
        this.changeInfoDoctorCode = changeInfoDoctorCode;
    }

    public String getChangeInfoDoctor() {
        return changeInfoDoctor;
    }

    public void setChangeInfoDoctor(String changeInfoDoctor) {
        this.changeInfoDoctor = changeInfoDoctor;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public String getGroupTid() {
        return groupTid;
    }

    public void setGroupTid(String groupTid) {
        this.groupTid = groupTid;
    }
}
