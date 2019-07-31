package com.keydom.ih_patient.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_patient.adapter.PayDetailAdapter;

/**
 * created date: 2019/1/15 on 15:37
 * des:缴费下划线
 */
public class PayDetailLine implements MultiItemEntity {
    @Override
    public int getItemType() {
        return PayDetailAdapter.LINE;
    }
}
