package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 医生排班实体
 */
public class DoctorSchedulingBean implements Serializable {
    @JSONField(name = "date")
    private String date;
    @JSONField(name = "weekDay")
    private String weekDay;
    @JSONField(name = "describe")
    private String describe;
    @JSONField(name = "registrationFee")
    private String registrationFee;
    @JSONField(name = "number")
    private String number;
    @JSONField(name = "hosiptalName")
    private String hosiptalName;
    @JSONField(name = "deptName")
    private String deptName;

    @JSONField(name = "deptId")
    private String deptId;


    @JSONField(name = "registerBackNo")
    private int registerBackNo;


    public int getRegisterBackNo() {
        return registerBackNo;
    }

    public void setRegisterBackNo(int registerBackNo) {
        this.registerBackNo = registerBackNo;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(String registrationFee) {
        this.registrationFee = registrationFee;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHosiptalName() {
        return hosiptalName;
    }

    public void setHosiptalName(String hosiptalName) {
        this.hosiptalName = hosiptalName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
