package com.keydom.mianren.ih_patient.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.HealthSummaryBean;

/**
 * @author 顿顿
 * @date 20/3/4 10:55
 * @des 健康报告详情
 */
public interface HealthSummaryDetailView extends BaseView {
    void requestSummaryDetailSuccess(HealthSummaryBean summaryBean);

    String getPatientId();
}
