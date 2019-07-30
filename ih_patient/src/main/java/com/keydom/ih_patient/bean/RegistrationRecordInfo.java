package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 挂号记录
 */
public class RegistrationRecordInfo implements Serializable {
    @JSONField(name = "id")
    private long id;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "doctor")
    private String doctor;
    @JSONField(name = "dept")
    private String dept;
    @JSONField(name = "timeDesc")
    private String timeDesc;
    @JSONField(name = "registrationDate")
    private String registrationDate;
    @JSONField(name = "remark")
    private String remark;
    @JSONField(name = "state")
    private int state;
    @JSONField(name = "address")
    private String address;
    @JSONField(name = "number")
    private String number;
    @JSONField(name = "orderNo")
    private String orderNo;
    @JSONField(name = "weekBackNo")
    private String weekBackNo;
    @JSONField(name = "monthBackNo")
    private String monthBackNo;

    @JSONField(name = "registerBackNo")
    private String registerBackNo;

    @JSONField(name = "refundState")
    private int refundState;

    public int getRefundState() {
        return refundState;
    }

    public void setRefundState(int refundState) {
        this.refundState = refundState;
    }

    public String getRegisterBackNo() {
        return registerBackNo;
    }

    public void setRegisterBackNo(String registerBackNo) {
        this.registerBackNo = registerBackNo;
    }

    public String getWeekBackNo() {
        return weekBackNo;
    }

    public void setWeekBackNo(String weekBackNo) {
        this.weekBackNo = weekBackNo;
    }

    public String getMonthBackNo() {
        return monthBackNo;
    }

    public void setMonthBackNo(String monthBackNo) {
        this.monthBackNo = monthBackNo;
    }

    /**
     * fee : 0.01
     */


    @JSONField(name = "fee")
    private BigDecimal fee;

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

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}
