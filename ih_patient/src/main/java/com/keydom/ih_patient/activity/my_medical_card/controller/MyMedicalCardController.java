package com.keydom.ih_patient.activity.my_medical_card.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.activity.my_medical_card.view.MyMedicalCardView;
import com.keydom.ih_patient.bean.MedicalCardInfo;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.CardService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * 我的就诊卡控制器
 */
public class MyMedicalCardController extends ControllerImpl<MyMedicalCardView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {

    }

    /**
     * 查找就诊卡
     */
    public void fillData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", Global.getUserId());
        map.put("hospital", App.hospitalId);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(CardService.class).getCardList(map), new HttpSubscriber<List<MedicalCardInfo>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<MedicalCardInfo> data) {
                hideLoading();
                getView().fillDataList(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().fillDataListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
