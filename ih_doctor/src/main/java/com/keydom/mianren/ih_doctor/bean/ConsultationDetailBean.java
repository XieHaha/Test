package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @date 20/4/27 11:35
 * @des 会诊详情
 */
public class ConsultationDetailBean implements Serializable {
    private static final long serialVersionUID = 58440796453966435L;
    private int level;
    /**
     * 0 待会诊，1、已会诊
     */
    private int status;
    /**
     * 0、未接受，1、已接受，-1、申请医生
     */
    private int doctorStatus;
    private String applicationId;
    private String tid;
    private String recordId;
    private String registerUserImage;
    private String patientName;
    private String patientGender;
    private String patientAge;
    private String eleCardNumber;
    private String visitTime;
    private String mdtTime;
    private String endTime;
    private String reasonAndAim;
    private String illnessAbstract;
    private String mainTell;
    private ConsultationDoctorBean applyDoctor;
    private ArrayList<ConsultationDoctorBean> mdtDoctors;
    private ArrayList<String> medicalHistoryImg;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRegisterUserImage() {
        return registerUserImage;
    }

    public void setRegisterUserImage(String registerUserImage) {
        this.registerUserImage = registerUserImage;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getEleCardNumber() {
        return eleCardNumber;
    }

    public void setEleCardNumber(String eleCardNumber) {
        this.eleCardNumber = eleCardNumber;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getMdtTime() {
        return mdtTime;
    }

    public void setMdtTime(String mdtTime) {
        this.mdtTime = mdtTime;
    }

    public String getReasonAndAim() {
        return reasonAndAim;
    }

    public void setReasonAndAim(String reasonAndAim) {
        this.reasonAndAim = reasonAndAim;
    }

    public String getIllnessAbstract() {
        return illnessAbstract;
    }

    public void setIllnessAbstract(String illnessAbstract) {
        this.illnessAbstract = illnessAbstract;
    }

    public ConsultationDoctorBean getApplyDoctor() {
        return applyDoctor;
    }

    public void setApplyDoctor(ConsultationDoctorBean applyDoctor) {
        this.applyDoctor = applyDoctor;
    }

    public ArrayList<ConsultationDoctorBean> getMdtDoctors() {
        return mdtDoctors;
    }

    public void setMdtDoctors(ArrayList<ConsultationDoctorBean> mdtDoctors) {
        this.mdtDoctors = mdtDoctors;
    }

    public ArrayList<String> getMedicalHistoryImg() {
        return medicalHistoryImg;
    }

    public void setMedicalHistoryImg(ArrayList<String> medicalHistoryImg) {
        this.medicalHistoryImg = medicalHistoryImg;
    }

    public String getMainTell() {
        return mainTell;
    }

    public void setMainTell(String mainTell) {
        this.mainTell = mainTell;
    }

    public int getDoctorStatus() {
        return doctorStatus;
    }

    public void setDoctorStatus(int doctorStatus) {
        this.doctorStatus = doctorStatus;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
