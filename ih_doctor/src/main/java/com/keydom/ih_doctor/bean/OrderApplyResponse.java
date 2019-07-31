package com.keydom.ih_doctor.bean;

import java.math.BigDecimal;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：订单申请返回对象
 * @Author：song
 * @Date：19/1/16 下午5:06
 * 修改人：xusong
 * 修改时间：19/1/16 下午5:06
 */
public class OrderApplyResponse {
    private long id;
    private String name;
    private BigDecimal fee=BigDecimal.ZERO;

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
