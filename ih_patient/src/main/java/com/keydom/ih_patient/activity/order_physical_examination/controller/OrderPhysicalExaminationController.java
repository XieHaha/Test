package com.keydom.ih_patient.activity.order_physical_examination.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.activity.order_physical_examination.view.OrderPhysicalExaminationView;
import com.keydom.ih_patient.bean.PhysicalExaInfo;
import com.keydom.ih_patient.net.OrderService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 体检订单控制器
 */
public class OrderPhysicalExaminationController extends ControllerImpl<OrderPhysicalExaminationView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {

    }

    /**
     * 查询全部体检订单
     */
    public void queryPjysicalExaList(){
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).findhealthCheckComboAll(App.hospitalId), new HttpSubscriber<List<PhysicalExaInfo>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<PhysicalExaInfo> data) {
                hideLoading();
                getView().fillPhysicalExaDataList(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }
}
