package com.keydom.mianren.ih_patient.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.mianren.ih_patient.adapter.NursingOrderServiceAdapter;

/**
 * created date: 2018/12/20 on 15:08
 * des:护理头部item
 */
public class NursingOrderServiceHead implements MultiItemEntity {

    private String title;

    public NursingOrderServiceHead(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int getItemType() {
        return NursingOrderServiceAdapter.TYPE_HEAD;
    }
}
