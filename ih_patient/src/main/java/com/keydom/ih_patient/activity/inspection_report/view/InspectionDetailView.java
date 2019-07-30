package com.keydom.ih_patient.activity.inspection_report.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.InspectionDetailInof;

/**
 * 检验报告单view
 */
public interface InspectionDetailView extends BaseView {
    /**
     * 获取检验报告单详情
     */
    void getInspectionDetailSuccess(InspectionDetailInof inspectionDetailInof);

    /**
     * 获取失败
     */
    void getInspectionDetailFailed(String errMsg);
}
