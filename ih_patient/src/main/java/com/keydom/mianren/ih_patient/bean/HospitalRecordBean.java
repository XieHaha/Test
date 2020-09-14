package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @author 顿顿
 * @date 20/9/14 14:16
 * @des 住院预缴列表
 */
public class HospitalRecordBean implements Serializable {
    private static final long serialVersionUID = -8963922354560903177L;
    private  String orderId;
    private  String hisOrderId;
    private  String hisRechargeDate;
    private  String merchOrderNo;
    private  String refundOrderId;
    private  String isAllowRefund;
    private  String payMoney;
    private  String totalMoney;
    private  String refundMoney;
    private  String orderMemo;
    private  String hisOrderState;
    private  String channelId;
    private  String chargeType;
    private  String orderType;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getHisOrderId() {
        return hisOrderId;
    }

    public void setHisOrderId(String hisOrderId) {
        this.hisOrderId = hisOrderId;
    }

    public String getHisRechargeDate() {
        return hisRechargeDate;
    }

    public void setHisRechargeDate(String hisRechargeDate) {
        this.hisRechargeDate = hisRechargeDate;
    }

    public String getMerchOrderNo() {
        return merchOrderNo;
    }

    public void setMerchOrderNo(String merchOrderNo) {
        this.merchOrderNo = merchOrderNo;
    }

    public String getRefundOrderId() {
        return refundOrderId;
    }

    public void setRefundOrderId(String refundOrderId) {
        this.refundOrderId = refundOrderId;
    }

    public String getIsAllowRefund() {
        return isAllowRefund;
    }

    public void setIsAllowRefund(String isAllowRefund) {
        this.isAllowRefund = isAllowRefund;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(String refundMoney) {
        this.refundMoney = refundMoney;
    }

    public String getOrderMemo() {
        return orderMemo;
    }

    public void setOrderMemo(String orderMemo) {
        this.orderMemo = orderMemo;
    }

    public String getHisOrderState() {
        return hisOrderState;
    }

    public void setHisOrderState(String hisOrderState) {
        this.hisOrderState = hisOrderState;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
