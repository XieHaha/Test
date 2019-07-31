package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 位置信息
 */
public class LocationInfo implements Serializable {

    /**
     * id : 7
     * userId : 17
     * isdefault : 1
     * phone : 18080021273
     * addressName : 我是你大爷
     * address : 红红火火吧
     * provinceCode : 510000
     * provinceName : 四川省
     * cityCode : 510100
     * cityName : 成都市
     * areaCode : 510104
     * areaName : 锦江区
     * remark :
     */

    @JSONField(name = "id")
    private long id;
    @JSONField(name = "userId")
    private long userId;
    @JSONField(name = "isdefault")
    private int isdefault;
    @JSONField(name = "phone")
    private String phone;
    @JSONField(name = "addressName")
    private String addressName;
    @JSONField(name = "address")
    private String address;
    @JSONField(name = "provinceCode")
    private String provinceCode;
    @JSONField(name = "provinceName")
    private String provinceName;
    @JSONField(name = "cityCode")
    private String cityCode;
    @JSONField(name = "cityName")
    private String cityName;
    @JSONField(name = "areaCode")
    private String areaCode;
    @JSONField(name = "areaName")
    private String areaName;
    @JSONField(name = "remark")
    private String remark;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(int isdefault) {
        this.isdefault = isdefault;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
