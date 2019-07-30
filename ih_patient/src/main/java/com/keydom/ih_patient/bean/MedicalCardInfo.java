package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 就诊卡
 */
public class MedicalCardInfo implements Serializable {

    @JSONField(name = "name")
    private String name;
    @JSONField(name = "sex")
    private String sex;
    @JSONField(name = "hospital")
    private String hospital;
    @JSONField(name = "qrCode")
    private String qrCode;
    @JSONField(name = "birthday")
    private String birthday;
    @JSONField(name = "bindTime")
    private String bindTime;
    @JSONField(name = "eleCardNumber")
    private String eleCardNumber;
    @JSONField(name = "entCardNumber")
    private String entCardNumber;
    @JSONField(name = "phoneNumber")
    private String phoneNumber;
    @JSONField(name = "idCard")
    private String idCard;
    @JSONField(name = "state")
    private int state;
    @JSONField(name = "cardType")
    private String cardType;

    @JSONField(name = "hospitalName")
    private String hospitalName;

    @JSONField(name = "releaseTime")
    private String releaseTime;


    @JSONField(name = "contact")
    private String contact;


    @JSONField(name = "contactPhone")
    private String contactPhone;


    @JSONField(name = "relationship")
    private String relationship;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBindTime() {
        return bindTime;
    }

    public void setBindTime(String bindTime) {
        this.bindTime = bindTime;
    }

    public String getEleCardNumber() {
        return eleCardNumber;
    }

    public void setEleCardNumber(String eleCardNumber) {
        this.eleCardNumber = eleCardNumber;
    }

    public String getEntCardNumber() {
        return entCardNumber;
    }

    public void setEntCardNumber(String entCardNumber) {
        this.entCardNumber = entCardNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}
