package com.keydom.mianren.ih_doctor.activity.online_consultation.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.ConsultationDetailBean;

/**
 * @date 3月28日
 * 会诊接收
 */
public interface ConsultationReceiveView extends BaseView {

    int getStatus();

    String getOrderId();

    String getApplyId();

    String getRecordId();

    /**
     * 会诊详情
     */
    void requestDetailSuccess(ConsultationDetailBean data);

    void requestDetailFailed(String msg);

    /**
     * 会诊意见
     */
    void requestAdviceSuccess();

    void requestAdviceFailed(String msg);
}