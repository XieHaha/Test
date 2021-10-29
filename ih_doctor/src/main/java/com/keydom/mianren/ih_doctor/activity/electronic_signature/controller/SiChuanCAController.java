package com.keydom.mianren.ih_doctor.activity.electronic_signature.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.common_document.CommonDocumentActivity;
import com.keydom.mianren.ih_doctor.activity.electronic_signature.SiChuanCAActivity;
import com.keydom.mianren.ih_doctor.activity.electronic_signature.SiChuanCASetActivity;
import com.keydom.mianren.ih_doctor.activity.electronic_signature.view.SiChuanCAView;
import com.keydom.mianren.ih_doctor.bean.AgreementBean;
import com.keydom.mianren.ih_doctor.net.SignService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SiChuanCAController extends ControllerImpl<SiChuanCAView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_select_iv:
                getView().setSelect();
                break;
            case R.id.sign_protocol_tv:
                CommonDocumentActivity.start(getContext(), AgreementBean.CODE_16);
                break;
            case R.id.sign_next_tv:
                if (getView().isSelect()) {
                    SiChuanCASetActivity.start(getContext(), getView().isSign(),
                            SiChuanCAActivity.RESULT_CODE);
                } else {
                    ToastUtil.showMessage(getContext(), "请仔细阅读以上协议并勾选");
                }
                break;
            default:
        }
    }

    /**
     * 获取签名用户信息
     */
    public void isSign() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(SignService.class).isSign(), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().requestUserSignSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().requestUserSignFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取签名用户信息
     */
    public void getSign() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(SignService.class).getSign(), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().getSignSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getSignFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
