package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 体检套餐实体
 */
public class ExaminationInfo {

    @JSONField(name = "id")
    private long id;
    @JSONField(name = "applyNumber")
    private String applyNumber;
    @JSONField(name = "hospitalCode")
    private String hospitalCode;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "outpatientNumber")
    private String outpatientNumber;
    @JSONField(name = "clinicalDiagnosis")
    private String clinicalDiagnosis;
    @JSONField(name = "clinicalSign")
    private String clinicalSign;
    @JSONField(name = "sign")
    private String sign;
    @JSONField(name = "inspectProjectId")
    private long inspectProjectId;
    @JSONField(name = "inspectProjectName")
    private String projectName;
    @JSONField(name = "execDept")
    private String execDept;
    @JSONField(name = "remark")
    private String remark;
    @JSONField(name = "state")
    private long state;
    @JSONField(name = "appointmentTime")
    private String appointmentTime;
    @JSONField(name = "subclassName")
    private String subclassName;
    @JSONField(name = "hospitalName")
    private String hospitalName;
    @JSONField(name = "hospitalAreaCode")
    private String hospitalAreaCode;
    @JSONField(name = "hospitalAreaList")
    private String hospitalAreaList;
    @JSONField(name = "hospitalAreaName")
    private String hospitalAreaName;
    @JSONField(name = "checkTime")
    private String checkTime;
    @JSONField(name = "number")
    private String number;
    @JSONField(name = "serialNumber")
    private String serialNumber;
    @JSONField(name = "checkSite")
    private String checkSite;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApplyNumber() {
        return applyNumber;
    }

    public void setApplyNumber(String applyNumber) {
        this.applyNumber = applyNumber;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutpatientNumber() {
        return outpatientNumber;
    }

    public void setOutpatientNumber(String outpatientNumber) {
        this.outpatientNumber = outpatientNumber;
    }

    public String getClinicalDiagnosis() {
        return clinicalDiagnosis;
    }

    public void setClinicalDiagnosis(String clinicalDiagnosis) {
        this.clinicalDiagnosis = clinicalDiagnosis;
    }

    public String getClinicalSign() {
        return clinicalSign;
    }

    public void setClinicalSign(String clinicalSign) {
        this.clinicalSign = clinicalSign;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public long getInspectProjectId() {
        return inspectProjectId;
    }

    public void setInspectProjectId(long inspectProjectId) {
        this.inspectProjectId = inspectProjectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getExecDept() {
        return execDept;
    }

    public void setExecDept(String execDept) {
        this.execDept = execDept;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getSubclassName() {
        return subclassName;
    }

    public void setSubclassName(String subclassName) {
        this.subclassName = subclassName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAreaCode() {
        return hospitalAreaCode;
    }

    public void setHospitalAreaCode(String hospitalAreaCode) {
        this.hospitalAreaCode = hospitalAreaCode;
    }

    public String getHospitalAreaList() {
        return hospitalAreaList;
    }

    public void setHospitalAreaList(String hospitalAreaList) {
        this.hospitalAreaList = hospitalAreaList;
    }

    public String getHospitalAreaName() {
        return hospitalAreaName;
    }

    public void setHospitalAreaName(String hospitalAreaName) {
        this.hospitalAreaName = hospitalAreaName;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCheckSite() {
        return checkSite;
    }

    public void setCheckSite(String checkSite) {
        this.checkSite = checkSite;
    }
}
