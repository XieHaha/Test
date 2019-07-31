package com.keydom.ih_patient.activity.nursing_service.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.MedicalCardInfo;

import java.util.List;

/**
 * 选择服务对象view
 */
public interface NursingChoosePatientView extends BaseView {
    /**
     * 获取就诊卡成功
     */
    void getAllCardSuccess(List<MedicalCardInfo> dataList);

    /**
     * 获取失败
     */
    void getAllCardFailed(String errMsg);
}
