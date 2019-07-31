package com.keydom.ih_patient.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * created date: 2019/1/16 on 10:43
 * des:缴费详情
 */
public class PayRecordDetailBean {
    private String hospitalName;
    private String patientNumber;
    private String name;
    private BigDecimal sumFee;
    private String guideInfo;
    private List<PayDetailTitle> projectList;
    private BigDecimal fee;
    private String orderNumber;

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSumFee() {
        return sumFee;
    }

    public void setSumFee(BigDecimal sumFee) {
        this.sumFee = sumFee;
    }

    public String getGuideInfo() {
        return guideInfo;
    }

    public void setGuideInfo(String guideInfo) {
        this.guideInfo = guideInfo;
    }

    public List<PayDetailTitle> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<PayDetailTitle> projectList) {
        this.projectList = projectList;
    }
}
