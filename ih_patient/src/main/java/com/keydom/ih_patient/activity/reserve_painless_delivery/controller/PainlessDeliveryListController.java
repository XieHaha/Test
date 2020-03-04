package com.keydom.ih_patient.activity.reserve_painless_delivery.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.activity.reserve_painless_delivery.view.PainlessDeliveryListView;
import com.keydom.ih_patient.bean.PainlessDeliveryBean;
import com.keydom.ih_patient.constant.TypeEnum;
import com.keydom.ih_patient.net.PainlessDeliveryService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @date 20/3/4 16:59
 * @des
 */
public class PainlessDeliveryListController extends ControllerImpl<PainlessDeliveryListView> {

    public void getPainlessDeliveryList(TypeEnum typeEnum) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PainlessDeliveryService.class).getPainlessDeliveryList(), new HttpSubscriber<PageBean<PainlessDeliveryBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PageBean<PainlessDeliveryBean> data) {
                getView().requestSuccess(data.getRecords(), typeEnum);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }
}
