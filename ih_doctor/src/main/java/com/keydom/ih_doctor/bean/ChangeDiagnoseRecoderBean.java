package com.keydom.ih_doctor.bean;

import java.io.Serializable;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：转诊记录对象
 * @Author：song
 * @Date：18/12/7 上午10:04
 * 修改人：xusong
 * 修改时间：18/12/7 上午10:04
 */
public class ChangeDiagnoseRecoderBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String referralExplain;
    private String patientName;
    private int patientSex;
    private int patientAge;
    private String diseaseData;
    private String dept;
    private String doctor;
    private String jobTitle;
    private String referralTime;
    private String changeInfoDept;
    private String changeInfoJobTitle;
    private String changeInfoDoctor;
    private int state;
    private String content;
    private String adept;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReferralExplain() {
        return referralExplain;
    }

    public void setReferralExplain(String referralExplain) {
        this.referralExplain = referralExplain;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(int patientSex) {
        this.patientSex = patientSex;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
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

    public String getReferralTime() {
        return referralTime;
    }

    public void setReferralTime(String referralTime) {
        this.referralTime = referralTime;
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

    public String getChangeInfoDoctor() {
        return changeInfoDoctor;
    }

    public void setChangeInfoDoctor(String changeInfoDoctor) {
        this.changeInfoDoctor = changeInfoDoctor;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdept() {
        return adept;
    }

    public void setAdept(String adept) {
        this.adept = adept;
    }
}
