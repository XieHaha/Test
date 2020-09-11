package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @author 顿顿
 * @date 20/9/11 16:28
 * @des 住院清单列表
 */
public class HospitalCheckBean implements Serializable {
    private static final long serialVersionUID = 4802023881930801114L;

    private String date;
    private String fee;
    private String doctor;
    private String dept;
    private String deptStation;
    private String expenseTypeCode;
    private String expenseTypeName;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
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

    public String getDeptStation() {
        return deptStation;
    }

    public void setDeptStation(String deptStation) {
        this.deptStation = deptStation;
    }

    public String getExpenseTypeCode() {
        return expenseTypeCode;
    }

    public void setExpenseTypeCode(String expenseTypeCode) {
        this.expenseTypeCode = expenseTypeCode;
    }

    public String getExpenseTypeName() {
        return expenseTypeName;
    }

    public void setExpenseTypeName(String expenseTypeName) {
        this.expenseTypeName = expenseTypeName;
    }
}
