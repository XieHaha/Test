package com.keydom.ih_doctor.activity.my_message.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.activity.my_message.view.MyMessageView;
import com.keydom.ih_doctor.bean.MessageBean;
import com.keydom.ih_doctor.net.MessageService;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 我的消息控制器
 */
public class MyMessagaeController extends ControllerImpl<MyMessageView> {

    /**
     * 获取我的消息列表
     */
    public void getMyMessageList(){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MessageService.class).userMessageInfos(SharePreferenceManager.getPhoneNumber()), new HttpSubscriber<List<MessageBean>>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable List<MessageBean> data) {
                getView().getMessageListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {

                getView().getMessageListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
