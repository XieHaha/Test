package com.keydom.mianren.ih_patient.activity.reserve_painless_delivery.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_patient.activity.reserve_painless_delivery.view.PainlessDeliveryDetailView;
import com.keydom.mianren.ih_patient.net.PainlessDeliveryService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * 无痛分泌预约控制器
 *
 * @author 顿顿
 */
public class PainlessDeliveryDetailController extends ControllerImpl<PainlessDeliveryDetailView> implements IhTitleLayout.OnRightTextClickListener {

    /**
     * 无痛分娩预约
     */
    private void commitPainlessDelivery(Map<String, Object> map) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PainlessDeliveryService.class).commitPainlessDelivery(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable String data) {
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });

    }

    /**
     * 取消无痛分娩预约
     */
    public void cancelPainlessDelivery(String id) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PainlessDeliveryService.class).dealPainlessDelivery(id, 1), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().cancelSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void OnRightTextClick(View v) {
        cancelPainlessDelivery(getView().getId());
    }
}
