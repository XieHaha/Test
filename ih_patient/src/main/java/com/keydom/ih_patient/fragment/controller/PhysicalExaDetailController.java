package com.keydom.ih_patient.fragment.controller;

import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.SubscribeExaminationBean;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.fragment.view.PhysicalExaDetailView;
import com.keydom.ih_patient.net.OrderService;
import com.keydom.ih_patient.net.ReservationService;
import com.keydom.ih_patient.utils.pay.alipay.Alipay;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 体检套餐控制器
 */
public class PhysicalExaDetailController extends ControllerImpl<PhysicalExaDetailView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }

    /**
     * 获取体检详情
     */
    public void getPhysicalExaDetail(long id){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).findhealthCheckCheckDetai(id), new HttpSubscriber<String>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().FillPhysicalDetail(data);
            }
            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }


}
