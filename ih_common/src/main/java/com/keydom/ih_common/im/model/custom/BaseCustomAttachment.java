package com.keydom.ih_common.im.model.custom;

import com.alibaba.fastjson.JSONObject;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;


public abstract class BaseCustomAttachment implements MsgAttachment {
    protected int type;

    public BaseCustomAttachment(int type) {
        this.type = type;
    }

    public void fromJson(JSONObject data) {
        if (data != null) {
            paresData(data);
        }
    }

    @Override
    public String toJson(boolean send) {
        return CustomAttachParser.packData(type, packData());
    }

    public int getType() {
        return type;
    }

    protected abstract void paresData(JSONObject data);

    protected abstract JSONObject packData();
}
