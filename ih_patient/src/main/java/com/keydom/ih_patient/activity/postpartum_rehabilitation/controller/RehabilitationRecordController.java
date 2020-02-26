package com.keydom.ih_patient.activity.postpartum_rehabilitation.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_patient.activity.postpartum_rehabilitation.view.RehabilitationRecordView;

import java.util.ArrayList;

/**
 * 已缴费控制器
 */
public class RehabilitationRecordController extends ControllerImpl<RehabilitationRecordView> {

    /**
     * 获取产后康复数据
     */
    public void getRehabilitationRecord() {
        getView().fillRehabilitationRecordData(new ArrayList<>());
    }

    /**
     * 关注操作
     */
    public void getFollow() {
        getView().executeFollow();
    }

    /**
     * 点赞操作
     */
    public void getAccolade() {
        getView().executeAccolade();
    }
}
