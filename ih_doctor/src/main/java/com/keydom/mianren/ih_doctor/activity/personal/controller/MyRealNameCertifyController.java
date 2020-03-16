package com.keydom.mianren.ih_doctor.activity.personal.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.personal.view.MyRealNameCertifyView;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.PersonalApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：我的认证控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class MyRealNameCertifyController extends ControllerImpl<MyRealNameCertifyView> implements View.OnClickListener, IhTitleLayout.OnRightTextClickListener {


    /**
     * 获取实名认证验证码
     */
    public void getCode() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).sendAutonymCode(getView().getCodeMap()), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().getCodeSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getCodeFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 实名认证
     */
    public void autonymAuth() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).autonymAuth(HttpService.INSTANCE.object2Body(getView().getRealNameMap())), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().realNameSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().realNameFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_identifying_code_bt:
                if (getView().checkPhone()) {
                    getCode();
                }
                break;

        }
    }
    @SingleClick(1000)
    @Override
    public void OnRightTextClick(View v) {
        if (getView().checkRealMap()) {
            autonymAuth();
        }
    }
}
