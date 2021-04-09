package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.im.ImClient;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthConsultantDetailView;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 健康管理咨询师
 */
public class HealthConsultantDetailController extends ControllerImpl<HealthConsultantDetailView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.consultant_detail_next_tv) {
            ImClient.startConversation(mContext, getView().getConsultantBean().getUserCode(), null);
        }
    }
}
