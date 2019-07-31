package com.keydom.ih_common.activity.controller;

import com.keydom.ih_common.activity.view.DiagnoseOrderDetailView;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.DiagnoseOrderDetailBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.ApiService;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class DiagnoseOrderDetailController extends ControllerImpl<DiagnoseOrderDetailView> {


    public void getPatientInquisitionById() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).getPatientInquisitionById(getView().getOrderId()), new HttpSubscriber<DiagnoseOrderDetailBean>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable DiagnoseOrderDetailBean data) {
                getView().getDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
