package com.keydom.mianren.ih_doctor.activity.patient_manage.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.ImPatientInfo;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;

import java.util.List;

public interface ChoosePatientView extends BaseView {
    /**
     * 获取所有患者列表成功
     *
     * @param list 患者列表
     */
    void getUserListSuccess(List<ImPatientInfo> list, TypeEnum typeEnum);

    /**
     * 获取患者失败
     *
     * @param errMsg 失败提示信息
     */
    void getUserListFailed(String errMsg);


}
