package com.keydom.mianren.ih_patient.activity.nursing_service.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.NursingProjectInfo;

/**
 * 护理服务项目详情view
 */
public interface NursingProjectDetailView extends BaseView {

    /**
     * 获取护理项目详情回调
     */
    void getNursingProjectDetailSuccess(NursingProjectInfo nursingProjectInfo);

    /**
     * 获取项目失败
     */
    void getNursingProjectDetailFailed(String errMsg);
}
