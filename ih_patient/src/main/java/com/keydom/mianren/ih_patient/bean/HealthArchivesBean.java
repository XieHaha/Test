package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 顿顿
 * @date 21/3/30 10:58
 * @des 健康档案
 */
public class HealthArchivesBean implements Serializable {
    private static final long serialVersionUID = -8481671311289763733L;
    private String id;
    private String patientId;
    private long registerUserId;
    private String age;
    private String bmi;
    private String height;
    private int isDel;
    private int isDrink;
    private int isSmoke;
    private String weight;
    private String address;
    private String allergyHistory;
    private String area;
    private String areaCode;
    private String avatar;
    private String birthDate;
    private String cityCode;
    private String createTime;
    private String drinkDegree;
    private String drinkMl;
    private String drinkRemark;
    private String drinkYear;
    private String familyHistory;
    private String fertilityStatus;
    private String geneticHistory;
    private String homeAddress;
    private String idCard;
    private String maritalHistory;
    private String medicalHistory;
    private String name;
    private String nameAlpha;
    private String nation;
    private String patientHisCode;
    private String personHistory;
    private String phoneNumber;
    private String presentHistory;
    private String professionalCategory;
    private String provinceCode;
    private String smokeAmount;
    private String sex;
    private String smokeDegree;
    private String smokeRemark;
    private String smokeYear;
    private String surgeryHistory;
    private String userPhoneNumber;
    private String workUnits;
    private List<PatientRelativesBean> patientRelatives;
    private List<PatientSurgeryHistoryBean> patientSurgeryHistories;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public long getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(long registerUserId) {
        this.registerUserId = registerUserId;
    }


    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public int getIsDrink() {
        return isDrink;
    }

    public void setIsDrink(int isDrink) {
        this.isDrink = isDrink;
    }

    public int getIsSmoke() {
        return isSmoke;
    }

    public void setIsSmoke(int isSmoke) {
        this.isSmoke = isSmoke;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAllergyHistory() {
        return allergyHistory;
    }

    public void setAllergyHistory(String allergyHistory) {
        this.allergyHistory = allergyHistory;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDrinkDegree() {
        return drinkDegree;
    }

    public void setDrinkDegree(String drinkDegree) {
        this.drinkDegree = drinkDegree;
    }

    public String getDrinkMl() {
        return drinkMl;
    }

    public void setDrinkMl(String drinkMl) {
        this.drinkMl = drinkMl;
    }

    public String getDrinkRemark() {
        return drinkRemark;
    }

    public void setDrinkRemark(String drinkRemark) {
        this.drinkRemark = drinkRemark;
    }

    public String getDrinkYear() {
        return drinkYear;
    }

    public void setDrinkYear(String drinkYear) {
        this.drinkYear = drinkYear;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    public String getFertilityStatus() {
        return fertilityStatus;
    }

    public void setFertilityStatus(String fertilityStatus) {
        this.fertilityStatus = fertilityStatus;
    }

    public String getGeneticHistory() {
        return geneticHistory;
    }

    public void setGeneticHistory(String geneticHistory) {
        this.geneticHistory = geneticHistory;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMaritalHistory() {
        return maritalHistory;
    }

    public void setMaritalHistory(String maritalHistory) {
        this.maritalHistory = maritalHistory;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameAlpha() {
        return nameAlpha;
    }

    public void setNameAlpha(String nameAlpha) {
        this.nameAlpha = nameAlpha;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPatientHisCode() {
        return patientHisCode;
    }

    public void setPatientHisCode(String patientHisCode) {
        this.patientHisCode = patientHisCode;
    }

    public String getPersonHistory() {
        return personHistory;
    }

    public void setPersonHistory(String personHistory) {
        this.personHistory = personHistory;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPresentHistory() {
        return presentHistory;
    }

    public void setPresentHistory(String presentHistory) {
        this.presentHistory = presentHistory;
    }

    public String getProfessionalCategory() {
        return professionalCategory;
    }

    public void setProfessionalCategory(String professionalCategory) {
        this.professionalCategory = professionalCategory;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getSmokeAmount() {
        return smokeAmount;
    }

    public void setSmokeAmount(String smokeAmount) {
        this.smokeAmount = smokeAmount;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSmokeDegree() {
        return smokeDegree;
    }

    public void setSmokeDegree(String smokeDegree) {
        this.smokeDegree = smokeDegree;
    }

    public String getSmokeRemark() {
        return smokeRemark;
    }

    public void setSmokeRemark(String smokeRemark) {
        this.smokeRemark = smokeRemark;
    }

    public String getSmokeYear() {
        return smokeYear;
    }

    public void setSmokeYear(String smokeYear) {
        this.smokeYear = smokeYear;
    }

    public String getSurgeryHistory() {
        return surgeryHistory;
    }

    public void setSurgeryHistory(String surgeryHistory) {
        this.surgeryHistory = surgeryHistory;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getWorkUnits() {
        return workUnits;
    }

    public void setWorkUnits(String workUnits) {
        this.workUnits = workUnits;
    }

    public List<PatientRelativesBean> getPatientRelatives() {
        return patientRelatives;
    }

    public void setPatientRelatives(List<PatientRelativesBean> patientRelatives) {
        this.patientRelatives = patientRelatives;
    }

    public List<PatientSurgeryHistoryBean> getPatientSurgeryHistories() {
        return patientSurgeryHistories;
    }

    public void setPatientSurgeryHistories(List<PatientSurgeryHistoryBean> patientSurgeryHistories) {
        this.patientSurgeryHistories = patientSurgeryHistories;
    }
}
