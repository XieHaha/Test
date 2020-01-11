package com.keydom.ih_patient.bean;

import java.io.Serializable;

public class VIPDetailBean implements Serializable {


    private static final long serialVersionUID = 3940101522395378355L;

    /**
     * id : aec3402192e3454d9860470c902f1630
     * registerUserId : 1215472604022857729
     * cardTypeId : null
     * hospitalId : 1214731980118188034
     * cardNumber : null
     * cardHolder : 陈勤勤
     * phone : 13982177590
     * idCard : 513030199502123644
     * prepaidAmount : 20000
     * surplusAmount : 20000
     * address : null
     * contractTime : 2020-01-10 17:13:19
     * startTime : 2020-01-10 17:13:19
     * endTime : 2021-01-10 17:13:19
     * cardStatus : 0
     * untyingTime : null
     * refundStatus : 0
     * renewalTime : null
     * renewalAmount : null
     * paymentMethod : 2
     * createTime : 2020-01-10 17:13:19
     * updateTime : null
     * isDel : 0
     */

    private String id;
    private String registerUserId;
    private String cardTypeId;
    private String hospitalId;
    private String cardNumber;
    private String cardHolder;
    private String phone;
    private String idCard;
    private double prepaidAmount;
    private double surplusAmount;
    private String address;
    private String contractTime;
    private String startTime;
    private String endTime;
    private int cardStatus;
    private String untyingTime;
    private int refundStatus;
    private String renewalTime;
    private double renewalAmount;
    private int paymentMethod;
    private String createTime;
    private String updateTime;
    private int isDel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(String registerUserId) {
        this.registerUserId = registerUserId;
    }

    public String getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(String cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public double getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(double prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }

    public double getSurplusAmount() {
        return surplusAmount;
    }

    public void setSurplusAmount(double surplusAmount) {
        this.surplusAmount = surplusAmount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContractTime() {
        return contractTime;
    }

    public void setContractTime(String contractTime) {
        this.contractTime = contractTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(int cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getUntyingTime() {
        return untyingTime;
    }

    public void setUntyingTime(String untyingTime) {
        this.untyingTime = untyingTime;
    }

    public int getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(int refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRenewalTime() {
        return renewalTime;
    }

    public void setRenewalTime(String renewalTime) {
        this.renewalTime = renewalTime;
    }

    public double getRenewalAmount() {
        return renewalAmount;
    }

    public void setRenewalAmount(double renewalAmount) {
        this.renewalAmount = renewalAmount;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }
}
