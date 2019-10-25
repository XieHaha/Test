package com.keydom.ih_patient.activity.my_message.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.activity.my_message.view.MyMessageView;
import com.keydom.ih_patient.bean.MessageBean;
import com.keydom.ih_patient.bean.NoticeBean;
import com.keydom.ih_patient.net.MessageService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * 我的消息控制器
 */
public class MyMessagaeController extends ControllerImpl<MyMessageView> {

    /**
     * 获取我的消息列表
     * @param messageMap
     */
    public void getMyMessageList(Map<String, Object> messageMap){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MessageService.class).userMessageInfos(messageMap), new HttpSubscriber<PageBean<MessageBean>>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable PageBean<MessageBean> data) {
                getView().getMessageListSuccess(data.getRecords());
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {

                getView().getMessageListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取公告列表
     * @param messageMap
     */
    public void getNoticeList(Map<String, Object> messageMap){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MessageService.class).getNoticeList(messageMap), new HttpSubscriber<NoticeBean>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable NoticeBean data) {
                getView().getNoticeSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {

                getView().getNoticeFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


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
