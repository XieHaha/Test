package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @author 顿顿
 * @date 20/9/11 17:27
 * @des 住院信息
 */
public class HospitalInfoBean implements Serializable {
    private static final long serialVersionUID = -7235685200100291722L;
    private String dischargeTime;
    private String wardNo;
    private String inHospitalNo;
    private String deptCode;
    private String deptName;
    private String bedNo;
    private String inHospitalDate;
    private String inHospitalDays;
    private String totalFee;
    private String balance;
    private String isInHospital;

    public String getDischargeTime() {
        return dischargeTime;
    }

    public void setDischargeTime(String dischargeTime) {
        this.dischargeTime = dischargeTime;
    }

    public String getWardNo() {
        return wardNo;
    }

    public void setWardNo(String wardNo) {
        this.wardNo = wardNo;
    }

    public String getInHospitalNo() {
        return inHospitalNo;
    }

    public void setInHospitalNo(String inHospitalNo) {
        this.inHospitalNo = inHospitalNo;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getInHospitalDate() {
        return inHospitalDate;
    }

    public void setInHospitalDate(String inHospitalDate) {
        this.inHospitalDate = inHospitalDate;
    }

    public String getInHospitalDays() {
        return inHospitalDays;
    }

    public void setInHospitalDays(String inHospitalDays) {
        this.inHospitalDays = inHospitalDays;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getIsInHospital() {
        return isInHospital;
    }

    public void setIsInHospital(String isInHospital) {
        this.isInHospital = isInHospital;
    }
}
