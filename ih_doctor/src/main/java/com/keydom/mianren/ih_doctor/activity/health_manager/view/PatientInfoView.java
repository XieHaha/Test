package com.keydom.mianren.ih_doctor.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;

/**
 * @date 20/3/13 11:25
 * @des 患者信息
 */
public interface PatientInfoView extends BaseView {

    void requestSuccess(String data);

    void requestFailed(String data);
}
