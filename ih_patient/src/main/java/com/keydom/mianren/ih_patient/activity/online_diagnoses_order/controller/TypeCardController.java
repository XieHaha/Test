package com.keydom.mianren.ih_patient.activity.online_diagnoses_order.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.card_operate.CardoperateActivity;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.view.TypeCardView;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.CardService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * 就诊卡控制器
 */
public class TypeCardController extends ControllerImpl<TypeCardView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jump_to_card_operate_tv:
                CardoperateActivity.start(getContext());
                break;

        }
    }

    /**
     * 查询所有就诊卡
     */
    public void queryAllCard(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", Global.getUserId());
        map.put("hospital", App.hospitalId);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(CardService.class).getCardList(map), new HttpSubscriber<List<MedicalCardInfo>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<MedicalCardInfo> data) {
                hideLoading();
                getView().getAllCardSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getAllCardFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
