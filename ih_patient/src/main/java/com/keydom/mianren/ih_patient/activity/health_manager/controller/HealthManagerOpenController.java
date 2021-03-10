package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthManagerOpenView;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.ChoosePatientActivity;
import com.keydom.mianren.ih_patient.constant.Const;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 健康管理
 */
public class HealthManagerOpenController extends ControllerImpl<HealthManagerOpenView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.health_manager_open_tv:
            case R.id.health_manager_open_now_layout:
                if (getView().isSelected()) {
                    ChoosePatientActivity.start(getContext(), Const.PATIENT_TYPE_ALL, false);
                } else {
                    ToastUtil.showMessage(getContext(), "请仔细阅读健康管理服务知情同意书");
                }
                break;
            case R.id.health_manager_select_layout:
                getView().setSelect();
                break;
            case R.id.health_manager_protocol_tv:
                ToastUtil.showMessage(getContext(), "协议");
                break;
            default:
                break;
        }
    }
}
