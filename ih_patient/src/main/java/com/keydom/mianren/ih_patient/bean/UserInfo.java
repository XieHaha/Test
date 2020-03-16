package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 用户信息
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 2346241034764927931L;

    /**
     * id : 1
     * patientNumber : test
     * userAccount : test
     * password : test
     * userName : test
     * userAlphabet : 12
     * userImage : 1245454
     * sex : 1
     * brithDate : 2018-11-07 00:00:00
     * country :
     * nation :
     * idCard :
     * cardImage :
     * address :
     * phoneNumber : 1398229898
     * contact :
     * contactNumber :
     * relationship :
     * wechatAccount :
     * qqAccount :
     * alipayAccount :
     * blogAccount :
     * remark :
     * cardImageBack :
     * blackList : 1
     * provinceCode :
     * cityCode :
     * countryCode :
     * certification :
     */

    @JSONField(name = "id")
    private long id;
    @JSONField(name = "patientNumber")
    private String patientNumber;
    @JSONField(name = "userAccount")
    private String userAccount;
    @JSONField(name = "password")
    private String password;
    @JSONField(name = "userName")
    private String userName;
    @JSONField(name = "userAlphabet")
    private String userAlphabet;
    @JSONField(name = "userImage")
    private String userImage;
    @JSONField(name = "sex")
    private String sex;
    @JSONField(name = "brithDate")
    private String brithDate;
    @JSONField(name = "country")
    private String country;
    @JSONField(name = "nation")
    private String nation;
    @JSONField(name = "idCard")
    private String idCard;
    @JSONField(name = "cardImage")
    private String cardImage;
    @JSONField(name = "address")
    private String address;
    @JSONField(name = "phoneNumber")
    private String phoneNumber;
    @JSONField(name = "contact")
    private String contact;
    @JSONField(name = "contactNumber")
    private String contactNumber;
    @JSONField(name = "relationship")
    private String relationship;
    @JSONField(name = "wechatAccount")
    private String wechatAccount;
    @JSONField(name = "qqAccount")
    private String qqAccount;
    @JSONField(name = "alipayAccount")
    private String alipayAccount;
    @JSONField(name = "blogAccount")
    private String blogAccount;
    @JSONField(name = "remark")
    private String remark;
    @JSONField(name = "cardImageBack")
    private String cardImageBack;
    @JSONField(name = "blackList")
    private String blackList;
    @JSONField(name = "provinceCode")
    private String provinceCode;
    @JSONField(name = "cityCode")
    private String cityCode;
    @JSONField(name = "countryCode")
    private String countryCode;

    //实名认证 0表示是 1表示否
    @JSONField(name = "certification")
    private String certification;

    @JSONField(name = "token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * nationName : 汉族
     * certificationName :
     * provinceName : 四川省
     * cityName : 成都市
     * countyCode :
     * countyName :
     * countryName : 中国
     */

    @JSONField(name = "nationName")
    private String nationName;
    @JSONField(name = "certificationName")
    private String certificationName;
    @JSONField(name = "provinceName")
    private String provinceName;
    @JSONField(name = "cityName")
    private String cityName;
    @JSONField(name = "countyCode")
    private String countyCode;
    @JSONField(name = "countyName")
    private String countyName;
    @JSONField(name = "countryName")
    private String countryName;

    @JSONField(name = "consultTime")
    private String consultTime;

    public String getConsultTime() {
        return consultTime;
    }

    public void setConsultTime(String consultTime) {
        this.consultTime = consultTime;
    }

    /**
     * imToken : d7f9ee65f47f97057d928841bf58cccf
     */

    @JSONField(name = "imToken")
    private String imToken;

    @JSONField(name = "isVip")
    private int isVip = 0; // 0 非会员，1会员

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAlphabet() {
        return userAlphabet;
    }

    public void setUserAlphabet(String userAlphabet) {
        this.userAlphabet = userAlphabet;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBrithDate() {
        return brithDate;
    }

    public void setBrithDate(String brithDate) {
        this.brithDate = brithDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCardImage() {
        return cardImage;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount;
    }

    public String getQqAccount() {
        return qqAccount;
    }

    public void setQqAccount(String qqAccount) {
        this.qqAccount = qqAccount;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public String getBlogAccount() {
        return blogAccount;
    }

    public void setBlogAccount(String blogAccount) {
        this.blogAccount = blogAccount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCardImageBack() {
        return cardImageBack;
    }

    public void setCardImageBack(String cardImageBack) {
        this.cardImageBack = cardImageBack;
    }

    public String getBlackList() {
        return blackList;
    }

    public void setBlackList(String blackList) {
        this.blackList = blackList;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public String getCertificationName() {
        return certificationName;
    }

    public void setCertificationName(String certificationName) {
        this.certificationName = certificationName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getImToken() {
        return imToken;
    }

    public void setImToken(String imToken) {
        this.imToken = imToken;
    }

    public boolean isCertification() {
        return certification.equals("0");
    }

    public void setCertification(boolean isCertification) {
        certification = isCertification ? "0" : "1";
    }

    public boolean isMember() {
        return isVip > 0;
    }

    public int getMember() {
        return isVip;
    }

    public void setMember(int isVip) {
        this.isVip = isVip;
    }
}
