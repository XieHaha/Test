package com.keydom.ih_patient.fragment.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.fragment.view.PhysicalExaProcessView;
import com.keydom.ih_patient.net.OrderService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 体检套餐评论控制器
 */
public class PhysicalExaProcessController extends ControllerImpl<PhysicalExaProcessView> {

    /**
     * 获取体检评论
     */
    public void getPhysicalExaProcess(long id){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).findhealthCheckCheckProcess(id), new HttpSubscriber<String>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().FillPhysicalProcess(data);

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().FillPhysicalProcessFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
