package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 病历详情
 */
public class InspectionDetailBean implements Serializable {
    private static final long serialVersionUID = 6756322096538063422L;
    private String reportTitle;
    private String clinicCard;
    private String userType;
    private String hosBedNo;
    private String signingTime;
    private String checker;
    private String barCode;
    private String sex;
    private String patientName;
    private String sampleNumber;
    private String sampleType;
    private String applicationDepartment;
    private String age;
    private String submissionTime;
    private String remark;
    private String reportTime;
    private String inspector;
    private String itemNum;
    private String clinicNo;
    private String hosUserNo;
    private List<CheckoutResultListBean> dataS;

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getClinicCard() {
        return clinicCard;
    }

    public void setClinicCard(String clinicCard) {
        this.clinicCard = clinicCard;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getHosBedNo() {
        return hosBedNo;
    }

    public void setHosBedNo(String hosBedNo) {
        this.hosBedNo = hosBedNo;
    }

    public String getSigningTime() {
        return signingTime;
    }

    public void setSigningTime(String signingTime) {
        this.signingTime = signingTime;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getSampleNumber() {
        return sampleNumber;
    }

    public void setSampleNumber(String sampleNumber) {
        this.sampleNumber = sampleNumber;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getApplicationDepartment() {
        return applicationDepartment;
    }

    public void setApplicationDepartment(String applicationDepartment) {
        this.applicationDepartment = applicationDepartment;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(String submissionTime) {
        this.submissionTime = submissionTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public String getClinicNo() {
        return clinicNo;
    }

    public void setClinicNo(String clinicNo) {
        this.clinicNo = clinicNo;
    }

    public String getHosUserNo() {
        return hosUserNo;
    }

    public void setHosUserNo(String hosUserNo) {
        this.hosUserNo = hosUserNo;
    }

    public List<CheckoutResultListBean> getDataS() {
        return dataS;
    }

    public void setDataS(List<CheckoutResultListBean> dataS) {
        this.dataS = dataS;
    }
}
