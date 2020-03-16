package com.keydom.mianren.ih_patient.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.mianren.ih_patient.adapter.DoctorOrNurseDetailAdapter;

/**
 * created date: 2019/1/3 on 15:58
 * des:医生标题
 */
public class DoctorHeadItem extends AbstractExpandableItem<DoctorTextItem> implements MultiItemEntity {
    private boolean isExpand;
    private String title;

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return DoctorOrNurseDetailAdapter.TYPE_HEAD;
    }
}
