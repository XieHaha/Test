package com.keydom.mianren.ih_patient.activity.child_health.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_patient.activity.child_health.view.ChildHealthDetailView;

/**
 * @date 20/2/27 11:39
 * @des 儿童保健预约详情控制器
 */
public class ChildHealthDetailController extends ControllerImpl<ChildHealthDetailView> {

    /**
     * 获取儿童保健详情
     */
    public void getChildHealthDetail() {

        getView().bindDetailData("");
    }

}
