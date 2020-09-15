package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 顿顿
 * @date 20/9/15 13:29
 * @des
 */
public class HospitalPaymentBean implements Serializable {
    private static final long serialVersionUID = -3630204653782729460L;
    private BigDecimal fee;
    private int isVip;
    private int orderType;
    private long createTime;
    private String id;
    private String parentId;
    private String orderNumber;
    private String outpatientNumber;
    private String diagnosis;
    private String hospitalId;
    private String deptId;
    private String applyId;
    private String applyTime;
    private String patientId;
    private String doctorId;
    private String changeDoctorId;
    private String doctorCode;
    private String serviceAddress;
    private String payState;
    private String state;
    private String referralState;
    private String updateTime;
    private String conditionDesc;
    private String conditionData;
    private String eleCardNumber;
    private String suggest;
    private String inquisyType;
    private String isDel;
    private String payType;
    private String beginTime;
    private String endTime;
    private String source;
    private String changeNum;
    private String doctorEndInquiryDate;
    private String triageState;
    private String age;
    private String weight;
    private String orderSource;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOutpatientNumber() {
        return outpatientNumber;
    }

    public void setOutpatientNumber(String outpatientNumber) {
        this.outpatientNumber = outpatientNumber;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getChangeDoctorId() {
        return changeDoctorId;
    }

    public void setChangeDoctorId(String changeDoctorId) {
        this.changeDoctorId = changeDoctorId;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReferralState() {
        return referralState;
    }

    public void setReferralState(String referralState) {
        this.referralState = referralState;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    public void setConditionDesc(String conditionDesc) {
        this.conditionDesc = conditionDesc;
    }

    public String getConditionData() {
        return conditionData;
    }

    public void setConditionData(String conditionData) {
        this.conditionData = conditionData;
    }

    public String getEleCardNumber() {
        return eleCardNumber;
    }

    public void setEleCardNumber(String eleCardNumber) {
        this.eleCardNumber = eleCardNumber;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getInquisyType() {
        return inquisyType;
    }

    public void setInquisyType(String inquisyType) {
        this.inquisyType = inquisyType;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(String changeNum) {
        this.changeNum = changeNum;
    }

    public String getDoctorEndInquiryDate() {
        return doctorEndInquiryDate;
    }

    public void setDoctorEndInquiryDate(String doctorEndInquiryDate) {
        this.doctorEndInquiryDate = doctorEndInquiryDate;
    }

    public String getTriageState() {
        return triageState;
    }

    public void setTriageState(String triageState) {
        this.triageState = triageState;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }
}
