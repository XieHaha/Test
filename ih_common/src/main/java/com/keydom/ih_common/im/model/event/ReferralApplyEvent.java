package com.keydom.ih_common.im.model.event;

import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * 转诊申请事件
 */
public class ReferralApplyEvent {
    private IMMessage mMessage;

    public ReferralApplyEvent(IMMessage message) {
        mMessage = message;
    }

    public IMMessage getMessage() {
        return mMessage;
    }

    public void setMessage(IMMessage message) {
        mMessage = message;
    }
}
