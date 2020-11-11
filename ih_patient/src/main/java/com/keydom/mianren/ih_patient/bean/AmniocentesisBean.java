package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @date 20/3/31 17:03
 * @des 羊水穿刺预约
 */
public class AmniocentesisBean implements Serializable {
    private static final long serialVersionUID = -1260107197565905595L;

    private int id;
    private int fetusNum;
    private String surgeryTime;
    private String name;
    private String idCard;
    private String birthday;
    private String telephone;
    private String endMensesTime;
    private String expectedBirthTime;
    private String familyMemberName;
    private String familyMemberPhone;
    private String familyAddress;
    private String referralHospital;
    private String reason;
    private String createDate;
    private String updateDate;
    private String state;
    private String hivAttribute;
    private String rhAttribute;
    private String isUltrasonicException;
    private String isHypertension;
    private String isGlycuresis;
    private String syphilis;
    private String nt;
    private String headLength;
    private String ultrasonicDate;
    private String refusedReason;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFetusNum() {
        return fetusNum;
    }

    public void setFetusNum(int fetusNum) {
        this.fetusNum = fetusNum;
    }

    public String getSurgeryTime() {
        return surgeryTime;
    }

    public void setSurgeryTime(String surgeryTime) {
        this.surgeryTime = surgeryTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public String getFamilyAddress() {
        return familyAddress;
    }

    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }

    public String getReferralHospital() {
        return referralHospital;
    }

    public void setReferralHospital(String referralHospital) {
        this.referralHospital = referralHospital;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHivAttribute() {
        return hivAttribute;
    }

    public void setHivAttribute(String hivAttribute) {
        this.hivAttribute = hivAttribute;
    }

    public String getRhAttribute() {
        return rhAttribute;
    }

    public void setRhAttribute(String rhAttribute) {
        this.rhAttribute = rhAttribute;
    }

    public String getIsUltrasonicException() {
        return isUltrasonicException;
    }

    public void setIsUltrasonicException(String isUltrasonicException) {
        this.isUltrasonicException = isUltrasonicException;
    }

    public String getIsHypertension() {
        return isHypertension;
    }

    public void setIsHypertension(String isHypertension) {
        this.isHypertension = isHypertension;
    }

    public String getIsGlycuresis() {
        return isGlycuresis;
    }

    public void setIsGlycuresis(String isGlycuresis) {
        this.isGlycuresis = isGlycuresis;
    }

    public String getSyphilis() {
        return syphilis;
    }

    public void setSyphilis(String syphilis) {
        this.syphilis = syphilis;
    }

    public String getNt() {
        return nt;
    }

    public void setNt(String nt) {
        this.nt = nt;
    }

    public String getHeadLength() {
        return headLength;
    }

    public void setHeadLength(String headLength) {
        this.headLength = headLength;
    }

    public String getUltrasonicDate() {
        return ultrasonicDate;
    }

    public void setUltrasonicDate(String ultrasonicDate) {
        this.ultrasonicDate = ultrasonicDate;
    }

    public String getRefusedReason() {
        return refusedReason;
    }

    public void setRefusedReason(String refusedReason) {
        this.refusedReason = refusedReason;
    }
}
