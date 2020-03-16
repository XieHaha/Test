package com.keydom.mianren.ih_patient.activity.my_message.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.my_message.view.NoticeDeatailView;
import com.keydom.mianren.ih_patient.net.MessageService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 消息详情控制球
 */
public class NoticeDeatailCOntroller extends ControllerImpl<NoticeDeatailView> {
    /**
     * 更新消息状态
     */
    public void updateMessageState(long messageId){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MessageService.class).updateMessageStatus(messageId), new HttpSubscriber<Object>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable Object data) {

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }
}
