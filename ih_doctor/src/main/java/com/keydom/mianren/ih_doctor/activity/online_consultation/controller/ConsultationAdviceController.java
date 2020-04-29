package com.keydom.mianren.ih_doctor.activity.online_consultation.controller;

import android.text.TextUtils;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationAdviceView;
import com.keydom.mianren.ih_doctor.bean.ConsultationDetailBean;
import com.keydom.mianren.ih_doctor.net.ConsultationService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @date 20/4/9 11:28
 * @des 会诊室-视频
 */
public class ConsultationAdviceController extends ControllerImpl<ConsultationAdviceView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.consultation_advice_commit_tv) {
            if (TextUtils.isEmpty(getView().getConsultationAdvice()) || getView().getConsultationAdvice().length() < 10) {
                ToastUtil.showMessage(mContext, "会诊意见不能少于10个字");
                return;
            }
            commitConsultationAdvice();
        }
    }

    /**
     * 提交会诊意见
     */
    private void commitConsultationAdvice() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ConsultationService.class).consultationOrderCreateAdvice(getView().getCommitParams()), new HttpSubscriber<String>(getContext(),getDisposable(),true) {
            @Override
            public void requestComplete(@Nullable String data) {

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 会诊意见列表
     */
    public void getConsultationAdviceList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ConsultationService.class).consultationOrderAdviceList(getView().getOrderId()), new HttpSubscriber<ConsultationDetailBean>() {
            @Override
            public void requestComplete(@Nullable ConsultationDetailBean data) {

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }
}
