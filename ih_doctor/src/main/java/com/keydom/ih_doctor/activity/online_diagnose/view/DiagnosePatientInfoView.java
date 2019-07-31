package com.keydom.ih_doctor.activity.online_diagnose.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.DiagnosePatientInfoBean;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface DiagnosePatientInfoView extends BaseView {
    /**
     * 成功获取就诊人信息
     *
     * @param bean 就诊人资料对象
     */
    void getPatientInfoSuccess(DiagnosePatientInfoBean bean);

    /**
     * 获取就诊人信息失败
     *
     * @param errMsg 失败提示信息
     */
    void getPatientInfoFailed(String errMsg);

}