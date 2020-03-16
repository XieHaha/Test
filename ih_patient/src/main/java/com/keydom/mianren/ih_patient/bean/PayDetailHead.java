package com.keydom.mianren.ih_patient.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.mianren.ih_patient.adapter.PayDetailAdapter;

/**
 * created date: 2019/1/15 on 15:37
 * des:缴费头部
 */
public class PayDetailHead implements MultiItemEntity {
    @Override
    public int getItemType() {
        return PayDetailAdapter.HEAD;
    }
}
