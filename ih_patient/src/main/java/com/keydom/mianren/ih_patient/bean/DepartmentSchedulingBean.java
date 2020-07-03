package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.keydom.ih_common.bean.DoctorInfo;

/**
 * 科室挂号实体
 */
public class DepartmentSchedulingBean {



    @JSONField(name = "hospitalCode")
    private String hospitalCode;
    @JSONField(name = "schedulingDate")
    private String schedulingDate;
    @JSONField(name = "deptCode")
    private String deptCode;
    @JSONField(name = "userCode")
    private String userCode;
    @JSONField(name = "signalNumber")
    private int signalNumber;
    @JSONField(name = "registrationFee")
    private int registrationFee;
    @JSONField(name = "consultationFee")
    private int consultationFee;
    @JSONField(name = "otherCharges")
    private int otherCharges;
    @JSONField(name = "describes")
    private String describes;
    @JSONField(name = "limitNumber")
    private int limitNumber;
    @JSONField(name = "alreadyNumber")
    private int alreadyNumber;
    @JSONField(name = "deptName")
    private String deptName;
    @JSONField(name = "number")
    private String number;
    @JSONField(name = "deptId")
    private String deptId;
    @JSONField(name = "hospitalUserId")
    private String hospitalUserId;
    @JSONField(name = "hUser")
    private DoctorInfo hUser;

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getSchedulingDate() {
        return schedulingDate;
    }

    public void setSchedulingDate(String schedulingDate) {
        this.schedulingDate = schedulingDate;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public int getSignalNumber() {
        return signalNumber;
    }

    public void setSignalNumber(int signalNumber) {
        this.signalNumber = signalNumber;
    }

    public int getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(int registrationFee) {
        this.registrationFee = registrationFee;
    }

    public int getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(int consultationFee) {
        this.consultationFee = consultationFee;
    }

    public int getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(int otherCharges) {
        this.otherCharges = otherCharges;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public int getLimitNumber() {
        return limitNumber;
    }

    public void setLimitNumber(int limitNumber) {
        this.limitNumber = limitNumber;
    }

    public int getAlreadyNumber() {
        return alreadyNumber;
    }

    public void setAlreadyNumber(int alreadyNumber) {
        this.alreadyNumber = alreadyNumber;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getHospitalUserId() {
        return hospitalUserId;
    }

    public void setHospitalUserId(String hospitalUserId) {
        this.hospitalUserId = hospitalUserId;
    }

    public DoctorInfo getHUser() {
        return hUser;
    }

    public void setHUser(DoctorInfo hUser) {
        this.hUser = hUser;
    }


}
