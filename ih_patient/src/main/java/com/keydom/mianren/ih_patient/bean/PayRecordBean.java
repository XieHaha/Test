package com.keydom.mianren.ih_patient.bean;

import java.math.BigDecimal;

/**
 * created date: 2019/1/15 on 16:45
 * des:缴费记录实体类
 */
public class PayRecordBean {

    /**
     *   {
     *     "patientNumber": "d9871661-a4a8-467a-8",
     *     "recordState": 0,
     *     "hospitalId": 1101049670463119400,
     *     "date": "2019-08-08",
     *     "selfPayment": 0.01,
     *     "documentNo": "F1159303599067525122",
     *     "shouldFee": 0.01,
     *     "eleCardNumber": "d9871661-a4a8-467a-8",
     *     "sumFee": 0.01,
     *     "socialSecurityReimbursement": 0,
     *     "name": "殷悦",
     *     "payRegister": "缴费（问诊）",
     *     "type": 2
     *   }
     * */

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

    /**
     * 获取类型说明 诊间缴费类型
     * 0 图文问诊
     * 1视频问诊
     * 2护理
     * 21护理服务项目子单
     * 22护理服务耗材子单
     * 3体检预约
     * 4挂号
     * 5 检查
     * 6 检验
     * 7诊缴费
     * 8 院内处方
     * 9合并
     * 10外延处方
     */
    private int recordState;
    private boolean isSelect;
    private int type;
    private String eleCardNumber;
    private String prescriptionId;
    private boolean isWaiYan;

    public boolean isWaiYan() {
        if(recordState == 10){
            isWaiYan = true;
        }
        return isWaiYan;
    }

    public void setWaiYan(boolean waiYan) {
        isWaiYan = waiYan;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

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
