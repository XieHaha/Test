package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * 检验报告对象类
 */
public class InspectionRecordBean implements Serializable {
    private static final long serialVersionUID = 3819113681767656411L;

    private String reportID;
    private String reportType;
    private String applyTime;
    private String publishTime;
    private String patientName;
    private String resou;
    private String orderDept;
    private String orderDoc;
    private String sex;
    private String age;
    private String itemName;
    private String state;
    private String notes;

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getResou() {
        return resou;
    }

    public void setResou(String resou) {
        this.resou = resou;
    }

    public String getOrderDept() {
        return orderDept;
    }

    public void setOrderDept(String orderDept) {
        this.orderDept = orderDept;
    }

    public String getOrderDoc() {
        return orderDoc;
    }

    public void setOrderDoc(String orderDoc) {
        this.orderDoc = orderDoc;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
