package com.keydom.mianren.ih_doctor.activity.online_consultation.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationRoomView;
import com.keydom.mianren.ih_doctor.net.ConsultationService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConsultationRoomController extends ControllerImpl<ConsultationRoomView> {
    /**
     * 结束会诊
     */
    public void endConsultationOrder(String recordId, String videoUrl) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ConsultationService.class).endConsultationOrder(recordId, videoUrl), new HttpSubscriber<String>(mContext, getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().endConsultationSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().endConsultationFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
