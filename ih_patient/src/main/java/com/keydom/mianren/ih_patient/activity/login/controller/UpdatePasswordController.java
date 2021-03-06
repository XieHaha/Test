package com.keydom.mianren.ih_patient.activity.login.controller;

import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.im.utils.MD5;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.PhoneUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.login.view.UpdatePasswordView;
import com.keydom.mianren.ih_patient.net.HealthCardService;
import com.keydom.mianren.ih_patient.net.LoginService;
import com.keydom.mianren.ih_patient.utils.RegularUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改密码控制器
 */
public class UpdatePasswordController extends ControllerImpl<UpdatePasswordView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_code_bt:
                if (StringUtils.isEmpty(getView().getPhoneNum()) || !PhoneUtils.isMobileEnable(getView().getPhoneNum())) {
                    ToastUtil.showMessage(getContext(), "请填写正确的手机格式");
                } else {
                    getMsgCode(getView().getPhoneNum());
                }
                break;
            case R.id.register_next_btn:
                if (StringUtils.isEmpty(getView().getPhoneNum()) || !PhoneUtils.isMobileEnable(getView().getPhoneNum())) {
                    ToastUtil.showMessage(getContext(), "请填写正确的手机格式");
                } else if (StringUtils.isEmpty(getView().getMsgCode())) {
                    ToastUtil.showMessage(getContext(), "请填写验证码");
                } else {
                    inspecteMsgCode();
                }
                break;
            case R.id.next_step:
                if (!getView().getPassWord().equals(getView().getRePassWord())) {
                    ToastUtil.showMessage(getContext(), "两次填写的密码不一致");
                } else {
                    if (RegularUtils.PassWordValidate(getView().getPassWord())) {
                        updatePassword();
                    } else {
                        ToastUtil.showMessage(getContext(), "密码格式不正确，请重新填写");
                    }
                }
                break;
            case R.id.complete_regist_tv:
                getView().completeUpdate();
                break;

        }
    }

    /**
     * 修改密码
     */
    private void updatePassword() {
        Map<String, String> map = new HashMap<>();
        map.put("password", MD5.getStringMD5(getView().getPassWord()));
        map.put("phoneNumber", getView().getPhoneNum());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginService.class).updatePassword(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(), getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                getView().registerSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().registerFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getMsgCode(String s) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginService.class).sendValidate(s), new HttpSubscriber<Object>(getContext(), getDisposable(), true, false) {

            @Override
            public void requestComplete(@Nullable Object data) {
                getView().getMsgCodeSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
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
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginService.class).verificationCode(map), new HttpSubscriber<Object>(getContext(), getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                //                addCard();
                getView().msgInspectSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().msgInspectFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }

    /**
     * 绑定健康卡
     */
    public void addCard() {
        Map<String, String> map = new HashMap<>();
        map.put("name", App.userInfo.getCertificationName());
        map.put("idNo", App.userInfo.getIdCard());
        map.put("cellphone", App.userInfo.getPhoneNumber());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HealthCardService.class).addCard(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Boolean>(getContext(), getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable Boolean data) {
                getView().addCardSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().addCardFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
