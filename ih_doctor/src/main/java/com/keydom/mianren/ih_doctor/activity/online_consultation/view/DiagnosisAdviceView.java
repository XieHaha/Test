package com.keydom.mianren.ih_doctor.activity.online_consultation.view;

import android.view.View;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_common.bean.VoiceBean;
import com.keydom.mianren.ih_doctor.bean.ConsultationAdviceBean;

import java.util.List;
import java.util.Map;

/**
 * @date 20/4/9 11:28
 * @des 会诊室-会诊意见
 */
public interface DiagnosisAdviceView extends BaseView {

    boolean isOutConsultationDoctor();

    Map<String, Object> getCommitParams();

    String getConsultationAdvice();

    List<VoiceBean> getConsultationVoiceAdvice();

    long getOrderId();

    View getRoot();

    void onRecordSuccess(VoiceBean bean);

    void commitSuccess();

    void commitFailed(String msg);

    void getConsultationAdviceSuccess(List<ConsultationAdviceBean> data);

    void getConsultationAdviceFailed(String msg);
}
