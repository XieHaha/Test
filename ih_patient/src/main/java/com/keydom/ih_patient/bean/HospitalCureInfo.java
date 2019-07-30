package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 医院处方
 */
public class HospitalCureInfo{

    @JSONField(name = "id")
    private long id;
    @JSONField(name = "number")
    private String number;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "sex")
    private String sex;
    @JSONField(name = "admissionDept")
    private String admissionDept;
    @JSONField(name = "issuingDoctor")
    private String issuingDoctor;
    @JSONField(name = "issuingTime")
    private String issuingTime;
    @JSONField(name = "state")
    private int state;
    @JSONField(name = "isOverdue")
    private int isOverdue;
    @JSONField(name = "phoneNumber")
    private String phoneNumber;
    @JSONField(name = "admissionNumber")
    private String admissionNumber;
    @JSONField(name = "applyTime")
    private String applyTime;
    @JSONField(name = "contactPhone")
    private String contactPhone;
    @JSONField(name = "district")
    private String district;
    @JSONField(name = "address")
    private String address;
    @JSONField(name = "admissionTime")
    private String admissionTime;
    @JSONField(name = "healthType")
    private int healthType;
    @JSONField(name = "healthNumber")
    private String healthNumber;
    @JSONField(name = "healthSource")
    private String healthSource;
    @JSONField(name = "settlementType")
    private int settlementType;
    @JSONField(name = "reportTime")
    private String reportTime;
    @JSONField(name = "reportAddress")
    private String reportAddress;
    @JSONField(name = "reportNotice")
    private String reportNotice;
    @JSONField(name = "isPunctual")
    private int isPunctual;
    @JSONField(name = "noPunctualReason")
    private String noPunctualReason;
    @JSONField(name = "remark")
    private String remark;
    @JSONField(name = "expectedBedTime")
    private String expectedBedTime;
    @JSONField(name = "approvalTime")
    private String approvalTime;
    @JSONField(name = "confirmationTime")
    private String confirmationTime;

    public String getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(String approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getConfirmationTime() {
        return confirmationTime;
    }

    public void setConfirmationTime(String confirmationTime) {
        this.confirmationTime = confirmationTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAdmissionDept() {
        return admissionDept;
    }

    public void setAdmissionDept(String admissionDept) {
        this.admissionDept = admissionDept;
    }

    public String getIssuingDoctor() {
        return issuingDoctor;
    }

    public void setIssuingDoctor(String issuingDoctor) {
        this.issuingDoctor = issuingDoctor;
    }

    public String getIssuingTime() {
        return issuingTime;
    }

    public void setIssuingTime(String issuingTime) {
        this.issuingTime = issuingTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(int isOverdue) {
        this.isOverdue = isOverdue;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(String admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdmissionTime() {
        return admissionTime;
    }

    public void setAdmissionTime(String admissionTime) {
        this.admissionTime = admissionTime;
    }

    public int getHealthType() {
        return healthType;
    }

    public void setHealthType(int healthType) {
        this.healthType = healthType;
    }

    public String getHealthNumber() {
        return healthNumber;
    }

    public void setHealthNumber(String healthNumber) {
        this.healthNumber = healthNumber;
    }

    public String getHealthSource() {
        return healthSource;
    }

    public void setHealthSource(String healthSource) {
        this.healthSource = healthSource;
    }

    public int getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(int settlementType) {
        this.settlementType = settlementType;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getReportAddress() {
        return reportAddress;
    }

    public void setReportAddress(String reportAddress) {
        this.reportAddress = reportAddress;
    }

    public String getReportNotice() {
        return reportNotice;
    }

    public void setReportNotice(String reportNotice) {
        this.reportNotice = reportNotice;
    }

    public int getIsPunctual() {
        return isPunctual;
    }

    public void setIsPunctual(int isPunctual) {
        this.isPunctual = isPunctual;
    }

    public String getNoPunctualReason() {
        return noPunctualReason;
    }

    public void setNoPunctualReason(String noPunctualReason) {
        this.noPunctualReason = noPunctualReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExpectedBedTime() {
        return expectedBedTime;
    }

    public void setExpectedBedTime(String expectedBedTime) {
        this.expectedBedTime = expectedBedTime;
    }
}
