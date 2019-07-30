package com.keydom.ih_doctor.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.NursingProjectInfo;

import java.util.List;

public interface NursingView extends BaseView {

    /**
     * 获取护理服务项目成功
     *
     * @param dataList 护理服务项目
     */
    void getNursingProjectSuccess(List<NursingProjectInfo> dataList);

    /**
     * 获取护理服务项目失败
     *
     * @param errMsg 失败提示信息
     */
    void getNursingProjectFailed(String errMsg);
}
