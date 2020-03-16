package com.keydom.mianren.ih_patient.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.mianren.ih_patient.adapter.PayDetailAdapter;

/**
 * created date: 2019/1/15 on 15:37
 * des:缴费详情
 */
public class PayDetailContent implements MultiItemEntity {
    private long id;
    private String sonProjectClassificationCode;
    private String sonProjectClassificationName;
    private String sonProjectSpec;
    private String exDep;
    private String company;
    private String unitPrice;
    private String sonProjectSumFee;
    private int number;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSonProjectClassificationCode() {
        return sonProjectClassificationCode;
    }

    public void setSonProjectClassificationCode(String sonProjectClassificationCode) {
        this.sonProjectClassificationCode = sonProjectClassificationCode;
    }

    public String getSonProjectClassificationName() {
        return sonProjectClassificationName;
    }

    public void setSonProjectClassificationName(String sonProjectClassificationName) {
        this.sonProjectClassificationName = sonProjectClassificationName;
    }

    public String getSonProjectSpec() {
        return sonProjectSpec;
    }

    public void setSonProjectSpec(String sonProjectSpec) {
        this.sonProjectSpec = sonProjectSpec;
    }

    public String getExDep() {
        return exDep;
    }

    public void setExDep(String exDep) {
        this.exDep = exDep;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSonProjectSumFee() {
        return sonProjectSumFee;
    }

    public void setSonProjectSumFee(String sonProjectSumFee) {
        this.sonProjectSumFee = sonProjectSumFee;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int getItemType() {
        return PayDetailAdapter.CONTENT;
    }
}
