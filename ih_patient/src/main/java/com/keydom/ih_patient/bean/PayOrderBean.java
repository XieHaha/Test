package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;

/**
 * 订单支付实体
 */
public class PayOrderBean {

    /**
     * orderId : 1087270096222699521
     * fee : 30
            */

    @JSONField(name = "orderId")
    private long orderId;
    @JSONField(name = "fee")
    private BigDecimal fee;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}
