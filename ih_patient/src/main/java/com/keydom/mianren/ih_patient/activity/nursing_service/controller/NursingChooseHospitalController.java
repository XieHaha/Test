package com.keydom.mianren.ih_patient.activity.nursing_service.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.location_manage.LocationManageActivity;
import com.keydom.mianren.ih_patient.activity.nursing_service.view.NursingChooseHospitalView;
import com.keydom.mianren.ih_patient.bean.BaseNurseFeeBean;
import com.keydom.mianren.ih_patient.bean.HospitalLocationInfo;
import com.keydom.mianren.ih_patient.bean.LocationInfo;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.net.LocationService;
import com.keydom.mianren.ih_patient.net.NursingService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 护理服务选择医院控制器
 */
public class NursingChooseHospitalController extends ControllerImpl<NursingChooseHospitalView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.choose_location_tv:
                LocationManageActivity.start(getContext(),Type.STARTLOCATIONWITHRESULT);
        }
    }

    /**
     * 获取服务区域内医院坐标
     */
    public void getHospital(Map<String,Object> bodyMap){
        if(bodyMap!=null){
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingService.class).getHospital(bodyMap), new HttpSubscriber<List<HospitalLocationInfo>>(getContext(),getDisposable(),false) {
                @Override
                public void requestComplete(@Nullable List<HospitalLocationInfo> data) {
                    getView().getHospitalLocationInfoSuccess(data);
                }

                @Override
                public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                    getView().getHospitalLocationFailed(msg);
                    return super.requestError(exception, code, msg);
                }
            });
        }

    }

    /**
     * 获取基础服务费
     *
     */
    public void getBaseFee(){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingService.class).getBaseFee(App.hospitalId), new HttpSubscriber<BaseNurseFeeBean>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable BaseNurseFeeBean data) {
                getView().getBaseFee(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getBaseFeeFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 设置默认服务地址
     */
    public void getDefaultServiceAddress(){
        Map<String,Object> map=new HashMap<>();
        map.put("userId",Global.getUserId());
        map.put("currentPage", "1");
        map.put("pageSize", 50);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LocationService.class).getAddressList(map), new HttpSubscriber<PageBean<LocationInfo>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable PageBean<LocationInfo> data) {
                getView().getDefaultAddressSuccess(data.getRecords());
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getDefaultAddressFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }
}
