package com.keydom.ih_common.im.listener;

import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;

public interface QueryMessageListener {
    void onSuccess(List<IMMessage> param);

    void onFailed(int code);

    void onException(Throwable exception);
}