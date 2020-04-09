package com.keydom.mianren.ih_doctor.activity.online_consultation.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationAdviceView;

/**
 * @date 20/4/9 11:28
 * @des 会诊室-视频
 */
public class ConsultationAdviceController extends ControllerImpl<ConsultationAdviceView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.consultation_advice_commit_tv:
                commit();
                break;
            default:
                break;
        }
    }

    private void commit() {

    }
}
