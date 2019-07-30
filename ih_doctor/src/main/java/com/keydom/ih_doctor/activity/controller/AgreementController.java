package com.keydom.ih_doctor.activity.controller;

import android.app.Activity;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.AgreementActivity;
import com.keydom.ih_doctor.activity.MainActivity;
import com.keydom.ih_doctor.activity.view.AgreementView;
import com.keydom.ih_doctor.net.MainApiService;
import com.keydom.ih_doctor.net.PersonalApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：协议页面控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class AgreementController extends ControllerImpl<AgreementView> implements View.OnClickListener, IhTitleLayout.OnRightTextClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.established:
                if (getView().getAgreementType() == AgreementActivity.SERVICE_AGREEMENT) {
                    batchEnableAllService();
                } else if (getView().getAgreementType() == AgreementActivity.SERVICE_SINGLE) {
                    openService(getView().getServiceId());
                } else {
                    ((Activity) getContext()).finish();
                }
                break;
            case R.id.agreement_tip_tv:
//                AgreementActivity.startRegisterPage(getContext());
                break;

        }

    }

    @Override
    public void OnRightTextClick(View v) {
        SharePreferenceManager.setIsAgreement(false);
        MainActivity.start(getContext(),false);
    }


    /**
     * 批量开通服务
     */
    public void batchEnableAllService() {
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MainApiService.class).batchEnableAllService(map), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().openSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().openFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 开始服务
     */
    public void openService(long serviceId) {
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("serviceId", serviceId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).enabledService(map), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().enableServiceSuccess(data);
                hideLoading();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().enableServiceFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
