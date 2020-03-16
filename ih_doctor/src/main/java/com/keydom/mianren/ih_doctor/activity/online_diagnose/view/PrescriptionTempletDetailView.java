package com.keydom.mianren.ih_doctor.activity.online_diagnose.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.PrescriptionDrugDetailBean;

public interface PrescriptionTempletDetailView extends BaseView {

    /**
     * 获取处方详情成功
     *
     * @param list 处方详情数据
     */
    void getTempletDetailListSuccess(PrescriptionDrugDetailBean list);

    /**
     * 获取处方详情失败
     *
     * @param errMsg 失败提示信息
     */
    void getTempletDetailListFailed(String errMsg);
}
