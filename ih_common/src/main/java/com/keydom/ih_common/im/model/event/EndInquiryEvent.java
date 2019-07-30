package com.keydom.ih_common.im.model.event;

import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * 结束问诊事件
 * @author THINKPAD B
 */
public class EndInquiryEvent {
    private IMMessage mMessage;

    public EndInquiryEvent(IMMessage message) {
        mMessage = message;
    }

    public IMMessage getMessage() {
        return mMessage;
    }

    public void setMessage(IMMessage message) {
        mMessage = message;
    }
}
