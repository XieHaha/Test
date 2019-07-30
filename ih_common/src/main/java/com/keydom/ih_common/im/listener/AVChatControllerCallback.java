package com.keydom.ih_common.im.listener;

public interface AVChatControllerCallback<T> {

    void onSuccess(T t);

    void onFailed(int code, String errorMsg);
}