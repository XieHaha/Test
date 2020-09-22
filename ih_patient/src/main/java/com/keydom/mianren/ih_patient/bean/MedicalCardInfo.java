package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * 就诊卡
 */
public class MedicalCardInfo implements Serializable {

    private static final long serialVersionUID = -460434702513807292L;
    private String name;
    private String sex;
    private String age;
    private String hospital;
    private String qrCode;
    private String birthday;
    private String bindTime;
    private String eleCardNumber;
    private String entCardNumber;
    private String phoneNumber;
    private String idCard;
    private int state;
    private int cardState;
    private int cardType;
    private String hospitalId;
    private String hospitalName;
    private String releaseTime;
    private String contact;
    private String contactPhone;
    private String relationship;
    private String socialNumber;
    private String registerUserId;
    private String patientHisCode;
    private String hisCardType;
    private String healthCardId;
    private int isUnbind;
    private String id;

    public int getCardState() {
        return cardState;
    }

    public void setCardState(int cardState) {
        this.cardState = cardState;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getSocialNumber() {
        return socialNumber;
    }

    public void setSocialNumber(String socialNumber) {
        this.socialNumber = socialNumber;
    }

    public String getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(String registerUserId) {
        this.registerUserId = registerUserId;
    }

    public String getPatientHisCode() {
        return patientHisCode;
    }

    public void setPatientHisCode(String patientHisCode) {
        this.patientHisCode = patientHisCode;
    }

    public String getHisCardType() {
        return hisCardType;
    }

    public void setHisCardType(String hisCardType) {
        this.hisCardType = hisCardType;
    }

    public String getHealthCardId() {
        return healthCardId;
    }

    public void setHealthCardId(String healthCardId) {
        this.healthCardId = healthCardId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsUnbind() {
        return isUnbind;
    }

    public void setIsUnbind(int isUnbind) {
        this.isUnbind = isUnbind;
    }

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}
