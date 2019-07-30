package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 挂号订单实体
 */
public class DiagnosesOrderBean implements Serializable {

    /**
     * id : 11
     * name : 兵
     * sex : 1
     * age : 22
     * avatar : group1/M00/00/09/rBAEA1wjZFGAXtyYAAJrs4FR1_Q985.png
     * applyTime : 2018-12-12 10:31:41
     * conditionDesc :
     * conditionData :
     * pastMedicalHistory : 颈部手术,家族病史1,久坐,过敏史11
     * inquisitionType : 0
     * state : 2
     * doctorCode : 00001C00001
     * doctorName : 松22
     * deptName : 科室二0
     * phoneNumber : 18280365191
     * userCode : 19
     */

    @JSONField(name = "id")
    private long id;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "sex")
    private int sex;
    @JSONField(name = "age")
    private int age;
    @JSONField(name = "avatar")
    private String avatar;
    @JSONField(name = "applyTime")
    private String applyTime;
    @JSONField(name = "conditionDesc")
    private String conditionDesc;
    @JSONField(name = "conditionData")
    private String conditionData;
    @JSONField(name = "pastMedicalHistory")
    private String pastMedicalHistory;
    @JSONField(name = "inquisitionType")
    private int inquisitionType;
    @JSONField(name = "state")
    private int state;
    @JSONField(name = "doctorCode")
    private String doctorCode;
    @JSONField(name = "doctorName")
    private String doctorName;
    @JSONField(name = "deptName")
    private String deptName;
    @JSONField(name = "phoneNumber")
    private String phoneNumber;
    @JSONField(name = "userCode")
    private String userCode;

    @JSONField(name = "fee")
    private double fee;
    @JSONField(name = "type")
    private int type;

    @JSONField(name = "isSubOrderUnPay")
    private int isSubOrderUnPay;

    private int waitInquiryCount;


    @JSONField(name = "refundState")
    private int refundState;

    public int getRefundState() {
        return refundState;
    }

    public void setRefundState(int refundState) {
        this.refundState = refundState;
    }

    public int getIsSubOrderUnPay() {
        return isSubOrderUnPay;
    }

    public void setIsSubOrderUnPay(int isSubOrderUnPay) {
        this.isSubOrderUnPay = isSubOrderUnPay;
    }

    public int getWaitInquiryCount() {
        return waitInquiryCount;
    }

    public void setWaitInquiryCount(int waitInquiryCount) {
        this.waitInquiryCount = waitInquiryCount;
    }

    @JSONField(name = "waitInquiryCount")


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
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

    public String getPastMedicalHistory() {
        return pastMedicalHistory;
    }

    public void setPastMedicalHistory(String pastMedicalHistory) {
        this.pastMedicalHistory = pastMedicalHistory;
    }

    public int getInquisitionType() {
        return inquisitionType;
    }

    public void setInquisitionType(int inquisitionType) {
        this.inquisitionType = inquisitionType;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
