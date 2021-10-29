package com.keydom.mianren.ih_doctor.activity.electronic_signature.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.electronic_signature.view.SiChuanCASetView;
import com.keydom.mianren.ih_doctor.net.LoginApiService;
import com.keydom.mianren.ih_doctor.net.SignService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class SiChuanCASetController extends ControllerImpl<SiChuanCASetView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_sign_verify_send_tv:
                sendCode();
                break;
            case R.id.set_sign_next_tv:
                if (getView().enable()) {
                    if (getView().isSign()) {
                        resetPin();
                    } else {
                        certEnrollAndUserSignConfig();
                    }
                }
                break;
        }
    }


    /**
     * 发送验证码
     */
    public void sendCode() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phoneNumber", SharePreferenceManager.getPhoneNumber());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginApiService.class).sendCode(map), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().getIdentifyingCodeSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getIdentifyingCodeFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 申请证书并绑定签章
     */
    public void certEnrollAndUserSignConfig() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(SignService.class).certEnrollAndUserSignConfig(HttpService.INSTANCE.object2Body(getView().getApplyMap())), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().applySuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().applyFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 重置pin
     */
    public void resetPin() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(SignService.class).resetPin(HttpService.INSTANCE.object2Body(getView().getResetPinMap())), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().applySuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().applyFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
