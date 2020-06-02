package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

/**
 * @date 20/5/27 18:13
 * @des 申请加入会诊数据moudle
 */
public class AuditInfoBean implements Serializable {
    private static final long serialVersionUID = 4628163680618667067L;

    private int status;
    private String type;
    private String id;
    private String auditId;
    private String mdtApplicationId;
    private String applyDoctorId;
    private String applyReason;
    private String applyTime;
    private String auditorId;
    private String suggest;
    private String auditTime;
    private String patientName;
    private String applyDoctorName;
    private String applyDoctorCode;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getMdtApplicationId() {
        return mdtApplicationId;
    }

    public void setMdtApplicationId(String mdtApplicationId) {
        this.mdtApplicationId = mdtApplicationId;
    }

    public String getApplyDoctorId() {
        return applyDoctorId;
    }

    public void setApplyDoctorId(String applyDoctorId) {
        this.applyDoctorId = applyDoctorId;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(String auditorId) {
        this.auditorId = auditorId;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getApplyDoctorName() {
        return applyDoctorName;
    }

    public void setApplyDoctorName(String applyDoctorName) {
        this.applyDoctorName = applyDoctorName;
    }

    public String getApplyDoctorCode() {
        return applyDoctorCode;
    }

    public void setApplyDoctorCode(String applyDoctorCode) {
        this.applyDoctorCode = applyDoctorCode;
    }
}
