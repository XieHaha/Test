package com.keydom.ih_doctor.bean;

import java.io.Serializable;

/**
 * 描述：User
 *
 * @author : xsy
 * @data: 2018年11月16日 8点59分
 */
public class AppHospitalUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /** 医院代码*/
    private String  hospitalCode;
    /**科室代码 */

    private String  deptCode;

    /**人员代码*/
    private String  userCode ;

    /**姓名*/
    private String  name;

    /**性别*/
    private String  sex;

    /**身份证号*/
    private String  idCard;


    /**登录账号*/
    private String  account;

    /**密码*/
    private String  password;

    /**岗位*/
    private String  quarters;

    /**职称*/
    private String  jobTitle;

    /**学历*/
    private String  education;

    /**头像*/
    private String  image;

    /**手机号*/
    private String  phoneNumber;

    /**邮件地址*/
    private String  email;

    /**擅长*/
    private String  adept;

    /**简介*/
    private String  intro;

    /**执业资格证*/
    private String  qualificationsCard;

    /**紧急联系人*/
    private String  urgentContact;

    /**紧急联系人电话*/
    private String  urgentContactPhone;

    /**是否启用*/
    private Integer  state;

    /**备注*/
    private String   remark;

    public AppHospitalUser() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
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

    public String getQuarters() {
        return quarters;
    }

    public void setQuarters(String quarters) {
        this.quarters = quarters;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
