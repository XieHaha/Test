package com.keydom.mianren.ih_doctor.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class BodyCheckDetailInfo {

    @JSONField(name = "hospitalCode")
    private String hospitalCode;
    @JSONField(name = "applyNumber")
    private String applyNumber;
    @JSONField(name = "partientNumber")
    private String partientNumber;
    @JSONField(name = "patientName")
    private String patientName;
    @JSONField(name = "sex")
    private String sex;
    @JSONField(name = "age")
    private String age;
    @JSONField(name = "clinicalSign")
    private String clinicalSign;
    @JSONField(name = "sign")
    private String sign;
    @JSONField(name = "clinicalDiagnosis")
    private String clinicalDiagnosis;
    @JSONField(name = "resultTime")
    private String resultTime;
    @JSONField(name = "reporter")
    private String reporter;
    @JSONField(name = "hospitalDept")
    private String hospitalDept;
    @JSONField(name = "inspectNumber")
    private String inspectNumber;
    @JSONField(name = "inspectName")
    private String inspectName;
    @JSONField(name = "inspectTProjectId")
    private long inspectTProjectId;
    @JSONField(name = "inspectFinding")
    private String inspectFinding;
    @JSONField(name = "impression")
    private String impression;
    @JSONField(name = "suggest")
    private String suggest;
    @JSONField(name = "isPositive")
    private long isPositive;
    @JSONField(name = "inspectionSite")
    private String inspectionSite;
    @JSONField(name = "auditedDoctor")
    private String auditedDoctor;
    @JSONField(name = "imageList")
    private List<String> imageList;

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getApplyNumber() {
        return applyNumber;
    }

    public void setApplyNumber(String applyNumber) {
        this.applyNumber = applyNumber;
    }

    public String getPartientNumber() {
        return partientNumber;
    }

    public void setPartientNumber(String partientNumber) {
        this.partientNumber = partientNumber;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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

    public String getClinicalDiagnosis() {
        return clinicalDiagnosis;
    }

    public void setClinicalDiagnosis(String clinicalDiagnosis) {
        this.clinicalDiagnosis = clinicalDiagnosis;
    }

    public String getResultTime() {
        return resultTime;
    }

    public void setResultTime(String resultTime) {
        this.resultTime = resultTime;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getHospitalDept() {
        return hospitalDept;
    }

    public void setHospitalDept(String hospitalDept) {
        this.hospitalDept = hospitalDept;
    }

    public String getInspectNumber() {
        return inspectNumber;
    }

    public void setInspectNumber(String inspectNumber) {
        this.inspectNumber = inspectNumber;
    }

    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

    public long getInspectTProjectId() {
        return inspectTProjectId;
    }

    public void setInspectTProjectId(long inspectTProjectId) {
        this.inspectTProjectId = inspectTProjectId;
    }

    public String getInspectFinding() {
        return inspectFinding;
    }

    public void setInspectFinding(String inspectFinding) {
        this.inspectFinding = inspectFinding;
    }

    public String getImpression() {
        return impression;
    }

    public void setImpression(String impression) {
        this.impression = impression;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public long getIsPositive() {
        return isPositive;
    }

    public void setIsPositive(long isPositive) {
        this.isPositive = isPositive;
    }

    public String getInspectionSite() {
        return inspectionSite;
    }

    public void setInspectionSite(String inspectionSite) {
        this.inspectionSite = inspectionSite;
    }

    public String getAuditedDoctor() {
        return auditedDoctor;
    }

    public void setAuditedDoctor(String auditedDoctor) {
        this.auditedDoctor = auditedDoctor;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
