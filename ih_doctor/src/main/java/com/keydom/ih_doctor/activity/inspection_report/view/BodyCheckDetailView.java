package com.keydom.ih_doctor.activity.inspection_report.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.BodyCheckDetailInfo;

public interface BodyCheckDetailView extends BaseView {

    /**
     * 获取检查报告单详情
     *
     * @param bodyCheckDetailInfo
     */
    void getBodyCheckDetailSuccess(BodyCheckDetailInfo bodyCheckDetailInfo);

    /**
     * 获取检查报告单失败
     *
     * @param errMsg 失败信息
     */
    void getBodyCheckDetailFailed(String errMsg);
}
