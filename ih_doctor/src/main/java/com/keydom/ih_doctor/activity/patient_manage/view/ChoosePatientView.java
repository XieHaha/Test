package com.keydom.ih_doctor.activity.patient_manage.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.ImPatientInfo;

import java.util.List;
import java.util.Map;

public interface ChoosePatientView extends BaseView {
    /**
     * 获取所有患者列表成功
     *
     * @param list 患者列表
     */
    void getUserListSuccess(List<ImPatientInfo> list);

    /**
     * 获取患者失败
     *
     * @param errMsg 失败提示信息
     */
    void getUserListFailed(String errMsg);

    /**
     * 获取患者请求参数
     *
     * @return 请求参数
     */
    Map<String, Object> getListMap();
}
