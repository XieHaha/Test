package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.view.LifestyleDataView;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 生活数据
 */
public class LifestyleDataController extends ControllerImpl<LifestyleDataView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lifestyle_data_select_sure_tv:
                break;
            default:
                break;
        }
    }
}
