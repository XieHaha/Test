package com.keydom.ih_common.im.listener;

public interface MsgIndexRecordListener {
//    void onSuccess(List<MsgIndexRecord> result);

    void onFailed(int code, Throwable throwable);
}
