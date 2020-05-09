package com.keydom.mianren.ih_doctor.bean;

import com.keydom.mianren.ih_doctor.utils.CalculateTimeUtils;

import java.io.Serializable;
import java.util.Date;

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
    private long patientId;
    private String name;
    private int sex;
    private String age;
    private String avatar;
    private String userAvatar;
    private String applyTime;
    private String conditionDesc;
    private String conditionData;
    private String pastMedicalHistory;
    private String cardNumber;
    private String groupTid;
    private int isVip;
    /**
     * 0 图文问诊 1视频问诊 2护理，21护理服务项目子单，22护理服务耗材子单 3体检预约 4挂号 5 检查 6 检验 7诊缴费 8处方 9合并,10外延处方,11、分诊
     */
    private String orderType;

    public String getGroupTid() {
        return groupTid;
    }

    public void setGroupTid(String groupTid) {
        this.groupTid = groupTid;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

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
     * 11分诊中。13、待会诊，14、会诊结束
     */
    private int state;
    /**
     * 判断是否显示开具处方和处置建议按钮 0可以开具  1不可开具
     */
    private int isSuggest;
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
    private long prescriptionId;
    private String remark;
    /**
     * 问诊持续时长
     */
    private Float duration;

    /**
     * 是问诊单的情况下，能否开具处方<br>
     * 0不能 1能
     */
    private int isReturnVisit;

    /**
     * 支付方式  4、预付费
     */
    private int payType;

    public int getIsReturnVisit() {
        return isReturnVisit;
    }

    public void setIsReturnVisit(int isReturnVisit) {
        this.isReturnVisit = isReturnVisit;
    }

    public long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 未接诊订单失效时长
     */
    private Float doctorAcceptTime;

    /**
     * 问诊开始时间
     */
    private String beginTime;

    /**
     * 1转诊生成的单子，0患者端申请生成的单子
     */
    private int source;

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getBeginTime() {
        if (beginTime == null || "".equals(beginTime)) {
            return CalculateTimeUtils.getYMDTime(new Date());
        }
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public Float getDuration() {
        return duration;
    }

    public int getReferralState() {
        return referralState;
    }

    public void setReferralState(int referralState) {
        this.referralState = referralState;
    }

    public int getIsSuggest() {
        return isSuggest;
    }

    public void setIsSuggest(int isSuggest) {
        this.isSuggest = isSuggest;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public Float getDoctorAcceptTime() {
        return doctorAcceptTime;
    }

    public void setDoctorAcceptTime(Float doctorAcceptTime) {
        this.doctorAcceptTime = doctorAcceptTime;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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
