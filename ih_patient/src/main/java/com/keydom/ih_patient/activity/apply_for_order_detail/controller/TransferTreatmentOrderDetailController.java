package com.keydom.ih_patient.activity.apply_for_order_detail.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.activity.apply_for_order_detail.view.TransferTreatmentOrderDetailView;
import com.keydom.ih_patient.bean.DiagnoseOrderDetailBean;
import com.keydom.ih_patient.net.DiagnoseApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Description：检查单详情控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改时间：18/11/16 :09
 */
public class TransferTreatmentOrderDetailController extends ControllerImpl<TransferTreatmentOrderDetailView> {


    /**
     * 获取详情
     */
    public void getDetail() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getDetail(getView().getQueryMap()), new HttpSubscriber<DiagnoseOrderDetailBean>(getContext(), getDisposable(), false,false) {
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
