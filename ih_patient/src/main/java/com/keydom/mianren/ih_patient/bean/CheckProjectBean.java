package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @date 20/6/12 14:05
 * @des
 */
public class CheckProjectBean implements Serializable {
    private static final long serialVersionUID = 5783480138285713229L;
    private String applyDoctorCode;
    private String applyDoctorName;
    private String applyDateTime;
    private String deptCode;
    private String deptName;
    private String cateName;
    private String cateType;
    private String cateCode;
    private String specimenName;
    private String doctorAdviceName;
    private String doctorAdviceCode;
    private List<CheckProjectSubBean> detailItem;

    public String getApplyDoctorCode() {
        return applyDoctorCode;
    }

    public void setApplyDoctorCode(String applyDoctorCode) {
        this.applyDoctorCode = applyDoctorCode;
    }

    public String getApplyDoctorName() {
        return applyDoctorName;
    }

    public void setApplyDoctorName(String applyDoctorName) {
        this.applyDoctorName = applyDoctorName;
    }

    public String getApplyDateTime() {
        return applyDateTime;
    }

    public void setApplyDateTime(String applyDateTime) {
        this.applyDateTime = applyDateTime;
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

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCateType() {
        return cateType;
    }

    public void setCateType(String cateType) {
        this.cateType = cateType;
    }

    public String getCateCode() {
        return cateCode;
    }

    public void setCateCode(String cateCode) {
        this.cateCode = cateCode;
    }

    public String getSpecimenName() {
        return specimenName;
    }

    public void setSpecimenName(String specimenName) {
        this.specimenName = specimenName;
    }

    public String getDoctorAdviceName() {
        return doctorAdviceName;
    }

    public void setDoctorAdviceName(String doctorAdviceName) {
        this.doctorAdviceName = doctorAdviceName;
    }

    public String getDoctorAdviceCode() {
        return doctorAdviceCode;
    }

    public void setDoctorAdviceCode(String doctorAdviceCode) {
        this.doctorAdviceCode = doctorAdviceCode;
    }

    public List<CheckProjectSubBean> getDetailItem() {
        return detailItem;
    }

    public void setDetailItem(List<CheckProjectSubBean> detailItem) {
        this.detailItem = detailItem;
    }
}
