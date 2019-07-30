package com.keydom.ih_doctor.activity.inspection_report.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.InspectionDetailInof;

public interface InspectionDetailView extends BaseView {
    /**
     * 获取检验报告单详情
     *
     * @param inspectionDetailInof
     */
    void getInspectionDetailSuccess(InspectionDetailInof inspectionDetailInof);

    /**
     * 获取检验报告单详情失败
     *
     * @param errMsg 失败信息
     */
    void getInspectionDetailFailed(String errMsg);
}
