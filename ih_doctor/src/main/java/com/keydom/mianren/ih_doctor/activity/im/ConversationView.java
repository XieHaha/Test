package com.keydom.mianren.ih_doctor.activity.im;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;

interface ConversationView extends BaseView {

    String getUserId();

    /**
     * 问诊单id
     *
     * @return
     */
    long getId();

    void loadSuccess(InquiryBean data);

    /**
     * 接单成功
     */
    void acceptSuccess();

    void acceptFailed(String msg);

    /**
     * 发起结束问诊成功
     */
    void endSuccess();

    boolean isGetStatus();

    void stopReferralSuccess();

    boolean isTeam();
}
