package com.keydom.mianren.ih_doctor.activity.online_consultation.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationReceiveView;
import com.keydom.mianren.ih_doctor.bean.ConsultationDetailBean;
import com.keydom.mianren.ih_doctor.net.ConsultationService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @date 3月28日
 * 会诊接收
 */
public class ConsultationReceiveController extends ControllerImpl<ConsultationReceiveView> implements View.OnClickListener {

    /**
     * 获取会诊详情
     */
    public void getConsultationOrderDetail(String id) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ConsultationService.class).consultationOrderDetail(id), new HttpSubscriber<ConsultationDetailBean>(mContext, getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable ConsultationDetailBean data) {
                getView().requestDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().requestDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取会诊意见列表
     */
    public void consultationOrderAdviceList(String id) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ConsultationService.class).consultationOrderAdviceList(id), new HttpSubscriber<ConsultationDetailBean>(mContext, getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable ConsultationDetailBean data) {
                getView().requestAdviceSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().requestAdviceFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.consultation_receive_commit_tv) {

        }
    }
}
