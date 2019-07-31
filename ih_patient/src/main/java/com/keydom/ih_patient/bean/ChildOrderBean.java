package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 子订单实体
 */
public class ChildOrderBean {

    /**
     * orderNumber : F1100962552701140993
     * fee : 0.01
     * isPrescription : 1
     */

    @JSONField(name = "orderNumber")
    private String orderNumber;
    @JSONField(name = "fee")
    private String fee;
    @JSONField(name = "isPrescription")
    private int isPrescription;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public int getIsPrescription() {
        return isPrescription;
    }

    public void setIsPrescription(int isPrescription) {
        this.isPrescription = isPrescription;
    }
}
