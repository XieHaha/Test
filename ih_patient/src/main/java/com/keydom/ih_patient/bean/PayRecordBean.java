package com.keydom.ih_patient.bean;

import java.math.BigDecimal;

/**
 * created date: 2019/1/15 on 16:45
 * des:缴费记录实体类
 */
public class PayRecordBean {
    private long hospitalId;
    private String patientNumber;
    private String documentNo;
    private String name;
    private String date;
    private BigDecimal sumFee;
    private BigDecimal shouldFee;
    private String payRegister;
    private BigDecimal socialSecurityReimbursement;
    private BigDecimal selfPayment;
    private int recordState;//诊间缴费类型0问诊 1检查 2检验 3处方 4挂号 8
    private boolean isSelect;
    private int type;
    private String eleCardNumber;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEleCardNumber() {
        return eleCardNumber;
    }

    public void setEleCardNumber(String eleCardNumber) {
        this.eleCardNumber = eleCardNumber;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getSumFee() {
        return sumFee;
    }

    public void setSumFee(BigDecimal sumFee) {
        this.sumFee = sumFee;
    }

    public BigDecimal getShouldFee() {
        return shouldFee;
    }

    public void setShouldFee(BigDecimal shouldFee) {
        this.shouldFee = shouldFee;
    }

    public String getPayRegister() {
        return payRegister;
    }

    public void setPayRegister(String payRegister) {
        this.payRegister = payRegister;
    }

    public BigDecimal getSocialSecurityReimbursement() {
        return socialSecurityReimbursement;
    }

    public void setSocialSecurityReimbursement(BigDecimal socialSecurityReimbursement) {
        this.socialSecurityReimbursement = socialSecurityReimbursement;
    }

    public BigDecimal getSelfPayment() {
        return selfPayment;
    }

    public void setSelfPayment(BigDecimal selfPayment) {
        this.selfPayment = selfPayment;
    }

    public int getRecordState() {
        return recordState;
    }

    public void setRecordState(int recordState) {
        this.recordState = recordState;
    }
}
