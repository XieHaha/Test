package com.keydom.ih_patient.fragment.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.bean.PhysicalExaCommentsInfo;
import com.keydom.ih_patient.fragment.view.PhysicalExaCommentsView;
import com.keydom.ih_patient.net.OrderService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * 体检套餐控制器
 */
public class PhysicalExaCommentsController extends ControllerImpl<PhysicalExaCommentsView> {

    /**
     * 获取体检套餐列表
     */
    public void findCommentsList(Map<String,Object> map){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).medicalReservationCommentList(map), new HttpSubscriber<List<PhysicalExaCommentsInfo>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<PhysicalExaCommentsInfo> data) {
                getView().updateCommentsListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().updateCommentsListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
