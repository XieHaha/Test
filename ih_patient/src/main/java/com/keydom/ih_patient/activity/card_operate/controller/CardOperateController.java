package com.keydom.ih_patient.activity.card_operate.controller;

import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.card_operate.view.CardOperateView;
import com.keydom.ih_patient.activity.health_card.HealthCardDetailActivity;
import com.keydom.ih_patient.activity.login.UpdatePasswordActivity;
import com.keydom.ih_patient.net.HealthCardService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 卡控制器
 */
public class CardOperateController extends ControllerImpl<CardOperateView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_orbind_card_health_card_root_ll:

                if (null != App.userInfo && !TextUtils.isEmpty(App.userInfo.getIdCard())) {
                    if (App.userInfo.isCertification()) {
                        if (getView().isBind()) {
                            HealthCardDetailActivity.start(getContext());
                        } else {
                            UpdatePasswordActivity.start(getContext(), true);
                        }

                    } else {
                        ToastUtils.showShort("请先实名认证");
                    }
                } else {
                    ToastUtils.showShort("请先实名认证");
                }


                break;
        }
    }


    /**
     * 查询是否有绑定过健康卡
     */
    public void getHealthCardState(String idCard) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HealthCardService.class).getState(idCard), new HttpSubscriber<String>(getContext(), getDisposable(), true, false) {

            @Override
            public void requestComplete(@Nullable String data) {
                getView().getHealthCardStateSuccess(Boolean.valueOf(data));
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getHealthCardStateFailed(msg);
                return super.requestError(exception, code, msg);

            }
        });
    }
}
