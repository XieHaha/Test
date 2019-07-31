package com.keydom.ih_common.im.widget.provider;

import com.keydom.ih_common.im.manager.RevokeMessageHelper;
import com.keydom.ih_common.im.model.event.RevokeEvent;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.model.RevokeMsgNotification;

import org.greenrobot.eventbus.EventBus;

/**
 * 云信消息撤回观察者
 */

public class NimMessageRevokeObserver implements Observer<RevokeMsgNotification> {

    /**
     * 此处的EventBus事件在{@link com.keydom.ih_common.im.widget.ImMessageList#onRevokeEvent(RevokeEvent)}处理
     * @param notification
     */
    @Override
    public void onEvent(RevokeMsgNotification notification) {
        if (notification == null || notification.getMessage() == null) {
            return;
        }
        EventBus.getDefault().post(new RevokeEvent(notification.getMessage()));
        RevokeMessageHelper.getInstance().onRevokeMessage(notification.getMessage(), notification.getRevokeAccount());
    }
}
