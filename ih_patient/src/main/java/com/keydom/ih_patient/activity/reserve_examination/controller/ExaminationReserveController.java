package com.keydom.ih_patient.activity.reserve_examination.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_patient.activity.reserve_examination.view.ExaminationReserveView;
import com.keydom.ih_patient.constant.TypeEnum;

/**
 * 检验检查记录
 */
public class ExaminationReserveController extends ControllerImpl<ExaminationReserveView> {

    /**
     * 获取检查预约列表
     */
    public void queryExaminationList(String state, final TypeEnum typeEnum) {
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
    }
}
