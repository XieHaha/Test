package com.keydom.ih_patient.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_patient.adapter.DoctorOrNurseDetailAdapter;

/**
 * created date: 2019/1/3 on 15:59
 * des:医生描述
 */
public class DoctorTextItem implements MultiItemEntity {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return DoctorOrNurseDetailAdapter.TYPE_TEXT;
    }
}
