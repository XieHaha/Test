package com.keydom.mianren.ih_doctor.activity.patient_manage.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.PatientInfoBean;

import java.util.Map;

public interface PatientDatumView extends BaseView {

    /**
     * 获取患者信息请求参数
     *
     * @return 请求参数
     */
    Map<String, Object> getPatientInfoMap();

    /**
     * 获取患者信息成功
     *
     * @param bean 患者信息对象
     */
    void getInfoSuccess(PatientInfoBean bean);

    /**
     * 获取患者信息失败
     *
     * @param errMsg 失败提示信息
     */
    void getInfoFailed(String errMsg);

    /**
     * 更新患者信息成功
     */
    void updatePatientInfoSuccess();

    /**
     * 更新患者信息失败
     *
     * @param errMsg 失败提示信息
     */
    void updatePatientInfoFailed(String errMsg);

    /**
     * 获取备注
     *
     * @return 备注
     */
    String getRemark();


}
