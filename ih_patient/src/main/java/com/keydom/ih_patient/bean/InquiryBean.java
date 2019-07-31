package com.keydom.ih_patient.bean;

import java.io.Serializable;

/**
 * 问诊详情
 */
public class InquiryBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id : 11
     * name : 兵
     * sex : 1
     * age : 22
     * avatar : group1/M00/00/09/rBAEA1wjZFGAXtyYAAJrs4FR1_Q985.png
     * applyTime : null
     * conditionDesc :
     * conditionData :
     * pastMedicalHistory : null
     * inquisitionType : null
     * state : 2
     * doctorCode : null
     * doctorName : null
     * deptName : null
     * phoneNumber : 18280365191
     * userCode : null
     */

    private long id;
    private String name;
    private int sex;
    private int age;
    private String avatar;
    private String applyTime;
    private String conditionDesc;
    private String conditionData;
    private String pastMedicalHistory;
    /**
     * 0 图文问诊 1视频问诊
     */
    private int inquisitionType;
    /**
     * 0 问诊 1咨询
     */
    private int type;
    /**
     * 4转诊 2问诊中 3 不通过
     * <p>
     * 返回state 0未支付 1 待接收 2问诊中 3审核不通过 4 待转诊确认 5医生发起结束问诊 6 等待医生开处置建议或者处方  8 待评价 9完成 -1已取消
     */
    private int state;
    /**
     * 转诊状态<br>
     * -1患者拒绝 -2医生拒绝 0等待患者确认 1等待医生确认 2已完成
     */
    private int referralState;
    private String doctorCode;
    private String doctorName;
    private String deptName;
    private String phoneNumber;
    private String userCode;
    private float referralFee;
    private long referralId;
    /**
     * 问诊持续时长
     */
    private Float duration;

    /**
     * 未接诊订单失效时长
     */
    private Float doctorAcceptTime;

    /**
     * 问诊开始时间
     */
    private String beginTime;
    /**
     * 问诊结束时间
     */
    private String endTime;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public int getReferralState() {
        return referralState;
    }

    public void setReferralState(int referralState) {
        this.referralState = referralState;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public float getReferralFee() {
        return referralFee;
    }

    public void setReferralFee(float referralFee) {
        this.referralFee = referralFee;
    }

    public long getReferralId() {
        return referralId;
    }

    public void setReferralId(long referralId) {
        this.referralId = referralId;
    }

    public Float getDoctorAcceptTime() {
        return doctorAcceptTime;
    }

    public void setDoctorAcceptTime(Float doctorAcceptTime) {
        this.doctorAcceptTime = doctorAcceptTime;
    }

    public Float getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "InquiryBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", avatar='" + avatar + '\'' +
                ", applyTime='" + applyTime + '\'' +
                ", conditionDesc='" + conditionDesc + '\'' +
                ", conditionData='" + conditionData + '\'' +
                ", pastMedicalHistory='" + pastMedicalHistory + '\'' +
                ", inquisitionType=" + inquisitionType +
                ", type=" + type +
                ", state=" + state +
                ", referralState=" + referralState +
                ", doctorCode='" + doctorCode + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userCode='" + userCode + '\'' +
                ", referralFee=" + referralFee +
                ", referralId=" + referralId +
                ", duration=" + duration +
                ", doctorAcceptTime=" + doctorAcceptTime +
                ", beginTime='" + beginTime + '\'' +
                '}';
    }
}
