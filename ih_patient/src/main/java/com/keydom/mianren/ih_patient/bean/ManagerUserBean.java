package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * created date: 2018/12/15 on 14:05
 * des:就诊人实体
 */
public class ManagerUserBean implements Serializable{

    /**
     * id : 12
     * registerUserId : 19
     * name : 张大爷
     * sex : 0
     * userImage : group1/M00/00/01/rBAEA1wPfP-ATcYrAAm_kOy9DpQ619.jpg
     * cardId : 169997666554457888
     * birthday : 2018-12-13
     * phone : 18080021273
     * address : 四川 成都
     * pastMedicalHistory : 普鲁卡因,阿司匹林,青霉素,头孢类抗生素,迪卡因,无,熬夜,久坐,常饮酒,吸烟,白癜风,过敏性疾病,癫痫病,脑出血,糖尿病,心脏病,高血压,无,烧伤,头部外伤,骨折,背部手术,胸部手术,四肢手术,颈部手术,颅脑手术,无,已生育,怀孕期,备孕期,未生育,丧偶,离异,未婚,已婚
     */

    private long id;
    private long registerUserId;
    private String name;
    private String sex;
    private String userImage;
    private String cardId;
    private String birthday;
    private String phone;
    private String address;
    private String pastMedicalHistory;
    private boolean sexIsChoose;
    private int managerState;//1:新增 2:修改
    private String maritalHistory;//婚姻史1
    private String surgeryHistory;//手术史1
    private String familyHistory;//家族史1
    private String personHistory;// 个人史1
    private String allergyHistory;//过敏史1
    private String area;
    private String age;


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getMaritalHistory() {
        return maritalHistory;
    }

    public void setMaritalHistory(String maritalHistory) {
        this.maritalHistory = maritalHistory;
    }

    public String getSurgeryHistory() {
        return surgeryHistory;
    }

    public void setSurgeryHistory(String surgeryHistory) {
        this.surgeryHistory = surgeryHistory;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    public String getPersonHistory() {
        return personHistory;
    }

    public void setPersonHistory(String personHistory) {
        this.personHistory = personHistory;
    }

    public String getAllergyHistory() {
        return allergyHistory;
    }

    public void setAllergyHistory(String allergyHistory) {
        this.allergyHistory = allergyHistory;
    }

    public int getManagerState() {
        return managerState;
    }

    public void setManagerState(int managerState) {
        this.managerState = managerState;
    }

    public boolean isSexIsChoose() {
        return sexIsChoose;
    }

    public void setSexIsChoose(boolean sexIsChoose) {
        this.sexIsChoose = sexIsChoose;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(long registerUserId) {
        this.registerUserId = registerUserId;
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

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPastMedicalHistory() {
        return pastMedicalHistory;
    }

    public void setPastMedicalHistory(String pastMedicalHistory) {
        this.pastMedicalHistory = pastMedicalHistory;
    }
}
