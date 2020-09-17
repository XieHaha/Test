package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @date 20/9/17 13:32
 * @des
 */
public class PrescriptionRecordBean implements Serializable {
    private static final long serialVersionUID = -8173097107867902690L;

    private String prescriptionNo;
    private String inquiryNo;
    private String type;
    private String doctorName;
    private String doctorCode;
    private String deptName;
    private String deptCode;
    private String diagnosis;
    private String fee;
    private String createTime;
    private String state;
    private String auditorDoctorName;
    private String auditorDoctorCode;
    private String auditOpinion;
    private String complaint;
    private String dispensingDoctor;
    private String dispensingDoctorCode;
    private String medicalReasonsName;
    private String preHealthCareCode;
    private List<PrescriptionRecordDrugBean> prescriptionItem;

    public String getPrescriptionNo() {
        return prescriptionNo;
    }

    public void setPrescriptionNo(String prescriptionNo) {
        this.prescriptionNo = prescriptionNo;
    }

    public String getInquiryNo() {
        return inquiryNo;
    }

    public void setInquiryNo(String inquiryNo) {
        this.inquiryNo = inquiryNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAuditorDoctorName() {
        return auditorDoctorName;
    }

    public void setAuditorDoctorName(String auditorDoctorName) {
        this.auditorDoctorName = auditorDoctorName;
    }

    public String getAuditorDoctorCode() {
        return auditorDoctorCode;
    }

    public void setAuditorDoctorCode(String auditorDoctorCode) {
        this.auditorDoctorCode = auditorDoctorCode;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getDispensingDoctor() {
        return dispensingDoctor;
    }

    public void setDispensingDoctor(String dispensingDoctor) {
        this.dispensingDoctor = dispensingDoctor;
    }

    public String getDispensingDoctorCode() {
        return dispensingDoctorCode;
    }

    public void setDispensingDoctorCode(String dispensingDoctorCode) {
        this.dispensingDoctorCode = dispensingDoctorCode;
    }

    public String getMedicalReasonsName() {
        return medicalReasonsName;
    }

    public void setMedicalReasonsName(String medicalReasonsName) {
        this.medicalReasonsName = medicalReasonsName;
    }

    public String getPreHealthCareCode() {
        return preHealthCareCode;
    }

    public void setPreHealthCareCode(String preHealthCareCode) {
        this.preHealthCareCode = preHealthCareCode;
    }

    public List<PrescriptionRecordDrugBean> getPrescriptionItem() {
        return prescriptionItem;
    }

    public void setPrescriptionItem(List<PrescriptionRecordDrugBean> prescriptionItem) {
        this.prescriptionItem = prescriptionItem;
    }
}
