package com.keydom.mianren.ih_doctor.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.NursingProjectInfo;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;

import java.util.List;

public interface NursingView extends BaseView {

    /**
     * 获取护理服务项目成功
     *
     * @param dataList 护理服务项目
     */
    void getNursingProjectSuccess(List<NursingProjectInfo> dataList, TypeEnum typeEnum);

    /**
     * 获取护理服务项目失败
     *
     * @param errMsg 失败提示信息
     */
    void getNursingProjectFailed(String errMsg);
}
