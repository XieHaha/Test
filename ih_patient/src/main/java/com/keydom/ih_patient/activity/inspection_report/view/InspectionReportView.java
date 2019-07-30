package com.keydom.ih_patient.activity.inspection_report.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.MedicalCardInfo;

import java.util.List;

/**
 * 检验报告view
 */
public interface InspectionReportView extends BaseView {
    /**
     * 展示就诊卡弹框
     */
    void showCardPopupWindow();

    /**
     * 获取就诊卡列表回调
     */
    void fillDataList(List<MedicalCardInfo> dataList);

    /**
     * 获取就诊卡失败
     */
    void fillDataListFailed(String errMsg);
}
