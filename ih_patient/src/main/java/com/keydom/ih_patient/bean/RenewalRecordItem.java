package com.keydom.ih_patient.bean;

import java.io.Serializable;

public class RenewalRecordItem implements Serializable {


    /**
     * id : 392e0807fc6141aca236b9c4c2a95c54
     * vipCardId : aec3402192e3454d9860470c902f1630
     * renewalTime : 2020-01-11 16:14:40
     * renewalAmount : 1000
     * paymentMethod : 2
     * createTime : 2020-01-11 16:14:40
     */

    private String id;
    private String vipCardId;
    private String renewalTime;
    private double renewalAmount;
    private String paymentMethod;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVipCardId() {
        return vipCardId;
    }

    public void setVipCardId(String vipCardId) {
        this.vipCardId = vipCardId;
    }

    public String getRenewalTime() {
        return renewalTime;
    }

    public void setRenewalTime(String renewalTime) {
        this.renewalTime = renewalTime;
    }

    public double getRenewalAmount() {
        return renewalAmount;
    }

    public void setRenewalAmount(double renewalAmount) {
        this.renewalAmount = renewalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
