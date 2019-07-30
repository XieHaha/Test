package com.keydom.ih_patient.activity.certification.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.certification.view.CertificateView;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.LoginService;
import com.keydom.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证控制器
 */
public class CertificateController extends ControllerImpl<CertificateView> implements View.OnClickListener, IhTitleLayout.OnRightTextClickListener {
    private String type;
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.get_message_bt){
            getMsgCode(getView().getPhoneNum());
        }

    }

    @Override
    public void OnRightTextClick(View v) {
        if(type.equals("phone_certificate")){
            inspecteMsgCode();
        }else {
            inspecteIdCard();
        }
    }

    public void  getType(String type){
        this.type=type;
    }

    /**
     * 短信验证
     */
    private void inspecteMsgCode() {
        Map<String, String> map = new HashMap<>();
        map.put("phoneNumber", getView().getPhoneNum());
        map.put("verificationCode", getView().getMessageCode());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginService.class).verificationCode(map), new HttpSubscriber<Object>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                getView().msgInspectSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().msgInspectFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getMsgCode(String s) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginService.class).sendCode(s), new HttpSubscriber<Object>(getContext(),getDisposable(),false) {

            @Override
            public void requestComplete(@Nullable Object data) {
                getView().getMsgCodeSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getMsgCodeFailed(msg);
                return super.requestError(exception, code, msg);

            }
        });
    }

    /**
     * 实名认证
     */
    private void inspecteIdCard(){
        Map<String, Object> map = new HashMap<>();
        map.put("id",Global.getUserId());
        map.put("userName", getView().getName());
        map.put("idCard", getView().getIdCardNum());
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).realNameCertificate(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                getView().idCardCertificateSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().idCardCertificateFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
