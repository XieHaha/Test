package com.keydom.ih_patient.activity.setting.controller;

import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.PhoneUtils;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.setting.view.GestureUnlockView;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.LoginService;
import com.keydom.ih_patient.net.UserService;
import com.keydom.ih_patient.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class GestureUnlockController extends ControllerImpl<GestureUnlockView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_next_btn:
                Logger.e("register_next_btn->click");
                if (StringUtils.isEmpty(getView().getPhoneNum()) || !PhoneUtils.isMobileEnable(getView().getPhoneNum())) {
                    ToastUtil.shortToast(getContext(), "请填写正确的手机格式");
                } else if (StringUtils.isEmpty(getView().getMsgCode())) {
                    ToastUtil.shortToast(getContext(), "请填写验证码");
                } else {
                    inspecteMsgCode();
                }
                break;
            case R.id.get_code_bt:
                Logger.e("get_code_bt->click");
                if (StringUtils.isEmpty(getView().getPhoneNum()) || !PhoneUtils.isMobileEnable(getView().getPhoneNum())) {
                    ToastUtil.shortToast(getContext(), "请填写正确的手机格式");
                } else {
                    getMsgCode(getView().getPhoneNum());
                }
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getMsgCode(String s) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginService.class).sendValidate(s), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {

            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                getView().getMsgCodeSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getMsgCodeFailed(msg);
                return super.requestError(exception, code, msg);

            }
        });
    }
    /**
     * 验证验证码
     */
    private void inspecteMsgCode() {
        Map<String, String> map = new HashMap<>();
        map.put("phoneNumber", getView().getPhoneNum());
        map.put("verificationCode", getView().getMsgCode());
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginService.class).verificationCode(map), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                getView().msgInspectSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().msgInspectFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }

    public void setPassword(String passwordStr){
        Map<String,Object> map=new HashMap<>();
        map.put("account",Global.getUserId());
        map.put("password",passwordStr);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).setPassword(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                ToastUtil.shortToast(getContext(),"手势密码设置成功");
                getView().setPasswordSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtil.shortToast(getContext(),"手势密码设置失败"+msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
