package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisDetailView;

/**
 * @date 20/3/11 15:26
 * @des 羊水穿刺预约详情
 */
public class AmniocentesisDetailController extends ControllerImpl<AmniocentesisDetailView> {

    /**
     * 获取羊水穿刺预约详情
     */
    public void getAmniocentesisDetail() {
        getView().onAmniocentesisDetailSuccess();
    }
}
