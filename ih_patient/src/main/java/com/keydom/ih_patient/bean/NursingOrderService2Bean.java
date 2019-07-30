package com.keydom.ih_patient.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_patient.adapter.NursingOrderServiceAdapter;

/**
 * created date: 2018/12/20 on 15:08
 * des:护理服务
 */
public class NursingOrderService2Bean extends AbstractExpandableItem<NursingOrderServiceItem2Bean> implements MultiItemEntity {

    private int frequency;//第几次

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return NursingOrderServiceAdapter.TYPE_LEVEL_4;
    }
}
