package com.keydom.mianren.ih_patient.activity.apply_for_order_detail.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.apply_for_order_detail.view.CheckOrderDetailView;
import com.keydom.mianren.ih_patient.bean.CheckItemListBean;
import com.keydom.mianren.ih_patient.net.DiagnoseApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Description：检查单控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改时间：18/11/ 上午9:09
 */
public class CheckOrderDetailController extends ControllerImpl<CheckOrderDetailView> {

    /**
     *  获取检查单详情
     */
    public void getInspectDetail(long orderId) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getInspectDetail(orderId), new HttpSubscriber<CheckItemListBean>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable CheckItemListBean data) {
                getView().getInspactDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getInspactDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取检验单详情
     */
    public void getCheckoutDetail(long orderId) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getCheckoutDetail(orderId), new HttpSubscriber<CheckItemListBean>(getContext(), getDisposable(), false, false) {
            @Override
            public void requestComplete(@Nullable CheckItemListBean data) {
                getView().getCheckOutDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getCheckOutDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
