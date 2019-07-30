package com.keydom.ih_patient.fragment.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.bean.RegistrationRecordInfo;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.fragment.view.RegisterRecordView;
import com.keydom.ih_patient.net.OrderService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 挂号订单控制器
 */
public class RegisterRecordContrller extends ControllerImpl<RegisterRecordView> {

    /**
     * 获取挂号列表
     */
    public void queryRegistrationRecordList(String state,int currentPage,int pageSize){
        Map<String,Object> map=new HashMap<>();
        map.put("userId",Global.getUserId());
        map.put("hospitalId", App.hospitalId);
        map.put("state",state);
        map.put("currentPage",currentPage);
        map.put("pageSize",pageSize);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).list(map), new HttpSubscriber<List<RegistrationRecordInfo>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<RegistrationRecordInfo> data) {
                getView().getRegistrarionRecordListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getRegistrarionRecordListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
