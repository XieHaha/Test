package com.keydom.ih_patient.activity.nursing_service.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.NursingProjectInfo;
import com.keydom.ih_patient.constant.TypeEnum;

import java.util.List;

/**
 * 护理服务view
 */
public interface NursingView extends BaseView {

    /**
     * 获取护理项目成功
     */
    void getNursingProjectSuccess(List<NursingProjectInfo> dataList, TypeEnum typeEnum);

    /**
     * 获取失败
     */
    void getNursingProjectFailed(String errMsg);
}
