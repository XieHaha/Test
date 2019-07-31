package com.keydom.ih_common.im.model.event;

import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;

public class MessageCountEvent {
    private int count = 0;
    private String sessionId;
    private MsgDirectionEnum direct;

    public MsgDirectionEnum getDirect() {
        return direct;
    }

    public void setDirect(MsgDirectionEnum direct) {
        this.direct = direct;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
