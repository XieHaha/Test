package com.keydom.ih_patient.bean;

import java.io.Serializable;

public class IdCardInfo implements Serializable {
    private static final long serialVersionUID = -9101443527983655077L;


    /**
     * name : 殷悦
     * sex : 1
     * nation : nation_0
     * birthDate : 1996-06-05 00:00:00
     * cardType : null
     * cardNumber : 321102199606050024
     * cardValidBegin : null
     * cardValidEnd : null
     * cardTypeCode : null
     * detailAddress : null
     * provinceCode : 510000
     * cityCode : 510100
     * areaCode : 510106
     */

    private String name;
    private String sex;
    private String nation;
    private String birthDate;
    private String cardType;
    private String cardNumber;
    private String cardValidBegin;
    private String cardValidEnd;
    private String cardTypeCode;
    private String detailAddress;
    private String provinceCode;
    private String cityCode;
    private String areaCode;
    private String cardImage;
    private String cardImageBack;
    private String nationName;

    public String getCardImage() {
        return cardImage;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }

    public String getCardImageBack() {
        return cardImageBack;
    }

    public void setCardImageBack(String cardImageBack) {
        this.cardImageBack = cardImageBack;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
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

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardValidBegin() {
        return cardValidBegin;
    }

    public void setCardValidBegin(String cardValidBegin) {
        this.cardValidBegin = cardValidBegin;
    }

    public String getCardValidEnd() {
        return cardValidEnd;
    }

    public void setCardValidEnd(String cardValidEnd) {
        this.cardValidEnd = cardValidEnd;
    }

    public String getCardTypeCode() {
        return cardTypeCode;
    }

    public void setCardTypeCode(String cardTypeCode) {
        this.cardTypeCode = cardTypeCode;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
