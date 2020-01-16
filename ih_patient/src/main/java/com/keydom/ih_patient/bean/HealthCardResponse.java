package com.keydom.ih_patient.bean;

import java.io.Serializable;

public class HealthCardResponse implements Serializable {
    private static final long serialVersionUID = -3145711193492411595L;


    /**
     * address :
     * birthday : 577123200000
     * cellphone : 13541802551
     * ehealthCardId : 792B20808D7E3F9AC3689B06B7417E329AA27DAD9A688225059D979FDDB255E9
     * gender : 2
     * idNo : 513021198804160028
     * idType : 01
     * mindexId : 35b00353a1976777112c160b88eb8680
     * name : 熊兴梅
     * telephone :
     * unit :
     */

    private String address;
    private long birthday;
    private String cellphone;
    private String ehealthCardId;
    private String gender;
    private String idNo;
    private String idType;
    private String mindexId;
    private String name;
    private String telephone;
    private String unit;
    private String ehealthCode;

    public String getEhealthCode() {
        return ehealthCode;
    }

    public void setEhealthCode(String ehealthCode) {
        this.ehealthCode = ehealthCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEhealthCardId() {
        return ehealthCardId;
    }

    public void setEhealthCardId(String ehealthCardId) {
        this.ehealthCardId = ehealthCardId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getMindexId() {
        return mindexId;
    }

    public void setMindexId(String mindexId) {
        this.mindexId = mindexId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
