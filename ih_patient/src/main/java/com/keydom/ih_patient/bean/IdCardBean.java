package com.keydom.ih_patient.bean;


import java.io.Serializable;

public class IdCardBean implements Serializable {
    private static final long serialVersionUID = 2934306757835724924L;

    private int direction;
    private int wordsResultNumber;
    private String address;
    private String idNumber;
    private String birthday;
    private String name;
    private String gender;
    private String ethnic;
    private String idCardSide;
    private String riskType;
    private String imageStatus;
    private String signDate;
    private String expiryDate;
    private String issueAuthority;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getStringsResultNumber() {
        return wordsResultNumber;
    }

    public void setStringsResultNumber(int wordsResultNumber) {
        this.wordsResultNumber = wordsResultNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }

    public String getIdCardSide() {
        return idCardSide;
    }

    public void setIdCardSide(String idCardSide) {
        this.idCardSide = idCardSide;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getIssueAuthority() {
        return issueAuthority;
    }

    public void setIssueAuthority(String issueAuthority) {
        this.issueAuthority = issueAuthority;
    }
}
