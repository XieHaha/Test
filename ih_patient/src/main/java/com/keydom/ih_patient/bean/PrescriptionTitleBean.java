package com.keydom.ih_patient.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_patient.adapter.PrescriptionListAdapter;

import java.util.List;

/**
 * created date: 2019/1/18 on 10:40
 * des:电子处方标题
 */
public class PrescriptionTitleBean extends AbstractExpandableItem<PrescriptionBean> implements MultiItemEntity {
    private String value;
    private List<PrescriptionBean> items;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<PrescriptionBean> getItems() {
        return items;
    }

    public void setItems(List<PrescriptionBean> items) {
        this.items = items;
    }

    @Override
    public int getItemType() {
        return PrescriptionListAdapter.HEAD;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
