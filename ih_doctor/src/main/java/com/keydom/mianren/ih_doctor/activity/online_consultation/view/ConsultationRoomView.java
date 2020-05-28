package com.keydom.mianren.ih_doctor.activity.online_consultation.view;

import com.keydom.ih_common.base.BaseView;

import java.util.Map;

public interface ConsultationRoomView extends BaseView {
    void endConsultationSuccess();

    void endConsultationFailed(String msg);

    /**
     * 申请加入会诊提交成功
     */
    void applyJoinConsultationSuccess();

    void applyJoinConsultationFailed(String msg);

    void dealConsultationApplySuccess();

    /**
     * 申请加入会诊参数
     */
    Map<String, String> getApplyParams();

    /**
     * 申请加入会诊参数
     */
    Map<String, Object> getUploadVoiceParams();
}
