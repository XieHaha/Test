package com.keydom.mianren.ih_doctor.activity.online_consultation.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.ConsultationAdviceBean;

import java.util.List;
import java.util.Map;

/**
 * @date 20/4/9 11:28
 * @des 会诊室-会诊意见
 */
public interface ConsultationAdviceView extends BaseView {

    Map<String, Object> getCommitParams();

    String getConsultationAdvice();

    String getRecordId();

    void commitSuccess();

    void commitFailed(String msg);

    void getConsultationAdviceSuccess(List<ConsultationAdviceBean> data);

    void getConsultationAdviceFailed(String msg);
}
