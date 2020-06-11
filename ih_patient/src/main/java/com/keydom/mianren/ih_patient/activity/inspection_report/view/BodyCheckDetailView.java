package com.keydom.mianren.ih_patient.activity.inspection_report.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.InspectionDetailBean;

/**
 * 检验报告单view
 */
public interface BodyCheckDetailView extends BaseView {

    /**
     * 获取检查报告单详情
     */
    void getBodyCheckDetailSuccess(InspectionDetailBean bodyCheckDetailInfo);

    /**
     * 获取失败
     */
    void getBodyCheckDetailFailed(String errMsg);
}
