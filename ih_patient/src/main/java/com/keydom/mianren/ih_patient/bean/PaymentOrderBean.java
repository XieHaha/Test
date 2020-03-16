package com.keydom.mianren.ih_patient.bean;

/**
 * created date: 2019/1/18 on 16:44
 * des:缴费记录详情实体
 */
public class PaymentOrderBean {
    private double fee;
    private String orderNumber;
    private long orderId;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
