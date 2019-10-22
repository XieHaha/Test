package com.keydom.ih_doctor.activity.controller;

import android.app.Dialog;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.DialogCreator;
import com.keydom.ih_common.utils.PhoneUtils;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.AgreementActivity;
import com.keydom.ih_doctor.activity.view.UpdatePasswordView;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.net.LoginApiService;
import com.keydom.ih_doctor.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class UpdatePasswordController extends ControllerImpl<UpdatePasswordView> implements View.OnClickListener {
    private Dialog loading;
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.get_identifying_code_bt) {
            if (PhoneUtils.isMobileEnable(getView().getPhoneNo())) {
                sendCode(getView().getPhoneNo());
            } else {
                ToastUtil.shortToast(getContext(), "请输入正确的手机号");
            }
        } else if (v.getId() == R.id.next_step) {
            if (!PhoneUtils.isMobileEnable(getView().getPhoneNo())) {
                ToastUtil.shortToast(getContext(), "请输入获取验证码的手机号");
            } else if (StringUtils.isEmpty(getView().getIdentifyingCode())) {
                ToastUtil.shortToast(getContext(), "请输入验证码");
            } else if (!getView().getIsAgreement()) {
                ToastUtil.shortToast(getContext(), "请勾选用户服务协议");
            } else {
                verificationCode(getView().getPhoneNo(), getView().getIdentifyingCode());
            }
        } else if (v.getId() == R.id.register_agreement_tv) {
            AgreementActivity.startRegisterPage(getContext());
        }

    }

    /**
     * 发送验证码
     */
    private void sendCode(String phoneNo) {
        loading = DialogCreator.createLoadingDialog(getContext(), "请稍等");
        loading.show();
        HashMap<String, Object> map = new HashMap<>();
        map.put("phoneNumber", phoneNo);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginApiService.class).sendCode(map), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                loading.dismiss();
                getView().getIdentifyingCodeSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                loading.dismiss();
                getView().getIdentifyingCodeFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 校验验证码
     */
    private void verificationCode(String phoneNo, String code) {
        loading = DialogCreator.createLoadingDialog(getContext(), "请稍等");
        loading.show();
        HashMap<String, Object> map = new HashMap<>();
        map.put("phoneNumber", phoneNo);
        map.put("verificationCode", code);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginApiService.class).verificationCode(map), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                loading.dismiss();
                getView().verifyCodeSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                loading.dismiss();
                getView().verifyCodeFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
