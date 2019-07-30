package com.keydom.ih_common.im.model.event;

import com.netease.nimlib.sdk.msg.model.IMMessage;

public class RevokeEvent {
    private IMMessage mMessage;

    public RevokeEvent(IMMessage message) {
        mMessage = message;
    }

    public IMMessage getMessage() {
        return mMessage;
    }
}
