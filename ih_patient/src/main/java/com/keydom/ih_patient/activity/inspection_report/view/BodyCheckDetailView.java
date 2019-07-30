package com.keydom.ih_patient.activity.inspection_report.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.BodyCheckDetailInfo;

/**
 * 检验报告单view
 */
public interface BodyCheckDetailView extends BaseView {

    /**
     * 获取检查报告单详情
     */
    void getBodyCheckDetailSuccess(BodyCheckDetailInfo bodyCheckDetailInfo);

    /**
     * 获取失败
     */
    void getBodyCheckDetailFailed(String errMsg);
}
