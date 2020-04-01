package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.common_document.CommonDocumentActivity;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisResultView;
import com.keydom.mianren.ih_patient.constant.AmniocentesisProtocol;

/**
 * @date 20/3/11 9:33
 * @des 羊水穿刺预约成功
 */
public class AmniocentesisResultController extends ControllerImpl<AmniocentesisResultView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.amniocentesis_result_help_tv:
                CommonDocumentActivity.start(getContext(), "取号方法及就诊指南", "");
                break;
            case R.id.amniocentesis_result_agree_tv:
                CommonDocumentActivity.start(getContext(), "知情同意书",
                        AmniocentesisProtocol.AMNIOCENTESIS_AGREE_PROTOCOL.getUrl());
                break;
            case R.id.amniocentesis_result_notice_tv:
                CommonDocumentActivity.start(getContext(), "术前注意事项",
                        AmniocentesisProtocol.AMNIOCENTESIS_NOTICE.getUrl());
                break;
            default:
                break;
        }
    }
}
