package com.keydom.ih_common.im.model.event;

import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * 处置意见，事件
 *
 * @author THINKPAD B
 */
public class PrescriptionEvent {
    private IMMessage mMessage;

    public PrescriptionEvent(IMMessage message) {
        mMessage = message;
    }

    public IMMessage getMessage() {
        return mMessage;
    }

    public void setMessage(IMMessage message) {
        mMessage = message;
    }
}
