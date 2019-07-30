package com.keydom.ih_doctor.activity.electronic_signature.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.activity.electronic_signature.view.ApplySignatureView;
import com.keydom.ih_doctor.bean.SignRegInfoBean;
import com.keydom.ih_doctor.net.LoginApiService;
import com.keydom.ih_doctor.net.SignService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class ApplySignatureController extends ControllerImpl<ApplySignatureView> {


    /**
     * 注册电子签名
     */
    public void applySign() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(SignService.class).addTrustedUsert(HttpService.INSTANCE.object2Body(getView().getApplyMap())), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().applySuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().applyFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 发送验证码
     */
    public void sendCode(String phoneNo) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phoneNumber", phoneNo);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginApiService.class).sendCode(map), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().getIdentifyingCodeSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getIdentifyingCodeFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 添加任务ID
     */
    public void addAuthJob() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("msspId", MyApplication.userInfo.getMsspId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(SignService.class).addAuthJob(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().getIdentifyingCodeSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getIdentifyingCodeFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 更换手机号码
     *
     * @param mobile
     */
    public void changeMobile(String mobile) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("msspId", MyApplication.userInfo.getMsspId());
        map.put("userId", MyApplication.userInfo.getId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(SignService.class).changeMobile(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().changeMobileSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().changeMobileFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 获取签名用户信息
     */
    public void getUserElectronicsInfo() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", MyApplication.userInfo.getId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(SignService.class).getUserElectronicsInfo(map), new HttpSubscriber<SignRegInfoBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable SignRegInfoBean data) {
                getView().getUserSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getUserFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
