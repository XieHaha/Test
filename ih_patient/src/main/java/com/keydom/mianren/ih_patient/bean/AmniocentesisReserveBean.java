package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @date 20/3/12 10:22
 * @des 羊水穿刺预约
 */
public class AmniocentesisReserveBean implements Serializable {
    private static final long serialVersionUID = 6138688710245943682L;

    private String birthday;
    private String endMensesTime;
    private String expectedBirthTime;
    private String familyMemberName;
    private String familyMemberPhone;
    private String familyAddress;
    private String idCard;
    private String name;
    private String reason;
    private String referralHospital;
    private String surgeryTime;
    private String telephone;
    private String smsCode;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEndMensesTime() {
        return endMensesTime;
    }

    public void setEndMensesTime(String endMensesTime) {
        this.endMensesTime = endMensesTime;
    }

    public String getExpectedBirthTime() {
        return expectedBirthTime;
    }

    public void setExpectedBirthTime(String expectedBirthTime) {
        this.expectedBirthTime = expectedBirthTime;
    }

    public String getFamilyMemberName() {
        return familyMemberName;
    }

    public void setFamilyMemberName(String familyMemberName) {
        this.familyMemberName = familyMemberName;
    }

    public String getFamilyMemberPhone() {
        return familyMemberPhone;
    }

    public void setFamilyMemberPhone(String familyMemberPhone) {
        this.familyMemberPhone = familyMemberPhone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReferralHospital() {
        return referralHospital;
    }

    public void setReferralHospital(String referralHospital) {
        this.referralHospital = referralHospital;
    }

    public String getSurgeryTime() {
        return surgeryTime;
    }

    public void setSurgeryTime(String surgeryTime) {
        this.surgeryTime = surgeryTime;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFamilyAddress() {
        return familyAddress;
    }

    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
