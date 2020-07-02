package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @date 20/4/27 11:00
 * @des 会诊
 */
public class ConsultationBean implements Serializable {
    private static final long serialVersionUID = -227101634146254979L;

    private int level;
    private int isMyOrder;
    /**
     * 申请人id
     */
    private String applicantId;
    private String applicationId;
    private String registerImage;
    private String patientId;
    private String patientName;
    private String patientGender;
    private String patientAge;
    private String eleCardNumber;
    private String visitTime;
    private String applyTime;
    private String mdtTime;
    private String tid;
    private String recordId;
    private String userOrderId;
    private ArrayList<String> doctorCode;
    private ArrayList<AuditInfoBean> auditInfo;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIsMyOrder() {
        return isMyOrder;
    }

    public void setIsMyOrder(int isMyOrder) {
        this.isMyOrder = isMyOrder;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getRegisterImage() {
        return registerImage;
    }

    public void setRegisterImage(String registerImage) {
        this.registerImage = registerImage;
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

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getMdtTime() {
        return mdtTime;
    }

    public void setMdtTime(String mdtTime) {
        this.mdtTime = mdtTime;
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

    public ArrayList<String> getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(ArrayList<String> doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(String userOrderId) {
        this.userOrderId = userOrderId;
    }

    public ArrayList<AuditInfoBean> getAuditInfo() {
        return auditInfo;
    }

    public void setAuditInfo(ArrayList<AuditInfoBean> auditInfo) {
        this.auditInfo = auditInfo;
    }
}
