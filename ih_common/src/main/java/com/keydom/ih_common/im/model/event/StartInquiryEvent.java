package com.keydom.ih_common.im.model.event;

import com.netease.nimlib.sdk.msg.model.IMMessage;

public class StartInquiryEvent {
    private IMMessage mMessage;

    public StartInquiryEvent(IMMessage message) {
        mMessage = message;
    }

    public IMMessage getMessage() {
        return mMessage;
    }
}
