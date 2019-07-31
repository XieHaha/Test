package com.keydom.ih_patient.fragment.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.login.LoginActivity;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.fragment.view.CardBindView;
import com.keydom.ih_patient.net.CardService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * 绑卡控制器
 */
public class CardBindController extends ControllerImpl<CardBindView> implements View.OnClickListener {


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind_card_tv:
                if (Global.getUserId() == -1) {
                    new GeneralDialog(getContext(), "该功能需要登录才能使用，是否立即登录？", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            LoginActivity.start(getContext());
                        }
                    }).setTitle("提示").setCancel(false).setPositiveButton("登陆").show();
                } else
                    bindCard();
                break;
        }
    }


    /**
     * 绑卡操作
     */
    public void bindCard() {
        Map<String, Object> map = getView().getBindMap();
        if (map == null) {
            return;
        }
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(CardService.class).bindingCard(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                getView().bindSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().bindFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
