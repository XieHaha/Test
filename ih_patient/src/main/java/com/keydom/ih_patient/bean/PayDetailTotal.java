package com.keydom.ih_patient.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_patient.adapter.PayDetailAdapter;

/**
 * created date: 2019/1/15 on 15:37
 * des:缴费总金额
 */
public class PayDetailTotal implements MultiItemEntity {
    private String money;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public int getItemType() {
        return PayDetailAdapter.TOTAL;
    }
}
