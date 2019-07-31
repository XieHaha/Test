package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 医生实体
 */
public class DoctorInfo implements Serializable {
    @JSONField(name = "id")
    private long id;
    @JSONField(name = "hospitalId")
    private long hospitalId;
    @JSONField(name = "hospitalName")
    private String hospitalName;
    @JSONField(name = "deptName")
    private String deptName;
    @JSONField(name = "cityName")
    private String cityName;
    @JSONField(name = "userCode")
    private String userCode;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "sex")
    private String sex;
    @JSONField(name = "idCard")
    private String idCard;
    @JSONField(name = "account")
    private String account;
    @JSONField(name = "password")
    private String password;
    @JSONField(name = "image")
    private String image;
    @JSONField(name = "phoneNumber")
    private String phoneNumber;
    @JSONField(name = "email")
    private String email;
    @JSONField(name = "adept")
    private String adept;
    @JSONField(name = "intro")
    private String intro;
    @JSONField(name = "qualificationsCard")
    private String qualificationsCard;
    @JSONField(name = "urgentContact")
    private String urgentContact;
    @JSONField(name = "urgentContactPhone")
    private String urgentContactPhone;
    @JSONField(name = "state")
    private long state;
    @JSONField(name = "remark")
    private String remark;
    @JSONField(name = "qrcode")
    private String qrcode;
    @JSONField(name = "birthday")
    private String birthday;
    @JSONField(name = "wapUrl")
    private String wapUrl;
    @JSONField(name = "commonSeal")
    private String commonSeal;
    @JSONField(name = "autonymState")
    private long autonymState;
    @JSONField(name = "certificationName")
    private String certificationName;
    @JSONField(name = "jobTitleName")
    private String jobTitleName;
    @JSONField(name = "isFirst")
    private long isFirst;
    @JSONField(name = "jobTitleId")
    private long jobTitleId;
    @JSONField(name = "jobTypeId")
    private long jobTypeId;
    @JSONField(name = "onlineFeeId")
    private long onlineFeeId;
    @JSONField(name = "quartersId")
    private long quartersId;
    @JSONField(name = "educationId")
    private long educationId;
    @JSONField(name = "deptId")
    private long deptId;
    @JSONField(name = "popularDoctor")
    private int popularDoctor;
    @JSONField(name = "isInterrogation")
    private int isInterrogation;

    public int getPopularDoctor() {
        return popularDoctor;
    }

    public void setPopularDoctor(int popularDoctor) {
        this.popularDoctor = popularDoctor;
    }

    public int getIsInterrogation() {
        return isInterrogation;
    }

    public void setIsInterrogation(int isInterrogation) {
        this.isInterrogation = isInterrogation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdept() {
        return adept;
    }

    public void setAdept(String adept) {
        this.adept = adept;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getQualificationsCard() {
        return qualificationsCard;
    }

    public void setQualificationsCard(String qualificationsCard) {
        this.qualificationsCard = qualificationsCard;
    }

    public String getUrgentContact() {
        return urgentContact;
    }

    public void setUrgentContact(String urgentContact) {
        this.urgentContact = urgentContact;
    }

    public String getUrgentContactPhone() {
        return urgentContactPhone;
    }

    public void setUrgentContactPhone(String urgentContactPhone) {
        this.urgentContactPhone = urgentContactPhone;
    }

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getWapUrl() {
        return wapUrl;
    }

    public void setWapUrl(String wapUrl) {
        this.wapUrl = wapUrl;
    }

    public String getCommonSeal() {
        return commonSeal;
    }

    public void setCommonSeal(String commonSeal) {
        this.commonSeal = commonSeal;
    }

    public long getAutonymState() {
        return autonymState;
    }

    public void setAutonymState(long autonymState) {
        this.autonymState = autonymState;
    }

    public String getCertificationName() {
        return certificationName;
    }

    public void setCertificationName(String certificationName) {
        this.certificationName = certificationName;
    }

    public String getJobTitleName() {
        return jobTitleName;
    }

    public void setJobTitleName(String jobTitleName) {
        this.jobTitleName = jobTitleName;
    }

    public long getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(long isFirst) {
        this.isFirst = isFirst;
    }

    public long getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(long jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public long getJobTypeId() {
        return jobTypeId;
    }

    public void setJobTypeId(long jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    public long getOnlineFeeId() {
        return onlineFeeId;
    }

    public void setOnlineFeeId(long onlineFeeId) {
        this.onlineFeeId = onlineFeeId;
    }

    public long getQuartersId() {
        return quartersId;
    }

    public void setQuartersId(long quartersId) {
        this.quartersId = quartersId;
    }

    public long getEducationId() {
        return educationId;
    }

    public void setEducationId(long educationId) {
        this.educationId = educationId;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }
}
