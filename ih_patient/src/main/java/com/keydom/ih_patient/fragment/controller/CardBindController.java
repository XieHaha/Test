package com.keydom.ih_patient.fragment.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.R;
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
        switch (view.getId()){
            case R.id.bind_card_tv:
                bindCard();
                break;
        }
    }


    /**
     * 绑卡操作
     */
    public void bindCard(){
        Map<String,Object> map=getView().getBindMap();
        if(map==null){
         return;
        }
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(CardService.class).bindingCard(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(),getDisposable(),false) {
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
