package com.keydom.ih_patient.activity.location_manage.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.activity.location_manage.view.LocationManageView;
import com.keydom.ih_patient.bean.LocationInfo;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.LocationService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地址管理控制器
 */
public class LocationManageController extends ControllerImpl<LocationManageView> implements View.OnClickListener{
    @Override
    public void onClick(View view) {
    }


    /**
     * 获取地址列表
     */
    public void initLocationList(){
        Map<String,Object> map=new HashMap<>();
        map.put("userId",Global.getUserId());
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LocationService.class).getAddressList(map), new HttpSubscriber<List<LocationInfo>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<LocationInfo> data) {
                hideLoading();
                getView().getLocationList(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getLocationListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });


    }
}
