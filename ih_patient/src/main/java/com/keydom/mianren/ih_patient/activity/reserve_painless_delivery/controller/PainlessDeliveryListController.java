package com.keydom.mianren.ih_patient.activity.reserve_painless_delivery.controller;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_painless_delivery.view.PainlessDeliveryListView;
import com.keydom.mianren.ih_patient.bean.PainlessDeliveryBean;
import com.keydom.mianren.ih_patient.net.PainlessDeliveryService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author 顿顿
 * @date 20/3/4 16:59
 * @des
 */
public class PainlessDeliveryListController extends ControllerImpl<PainlessDeliveryListView> implements BaseQuickAdapter.OnItemChildClickListener {

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        PainlessDeliveryBean bean = (PainlessDeliveryBean) adapter.getItem(position);
        if (view.getId() == R.id.tv_cancel) {
            new GeneralDialog(mContext, "确认取消预约?", new GeneralDialog.OnCloseListener() {
                @Override
                public void onCommit() {
                    cancelPainlessDelivery(bean.getId(), position);
                }
            }).show();
        }

    }

    /**
     * 获取无痛分娩预约列表
     */
    public void getPainlessDeliveryList(String idCard) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PainlessDeliveryService.class).getPainlessDeliveryList(idCard), new HttpSubscriber<List<PainlessDeliveryBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<PainlessDeliveryBean> data) {
                getView().requestSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().requestFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 取消无痛分娩预约
     */
    public void cancelPainlessDelivery(String id, int position) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PainlessDeliveryService.class).dealPainlessDelivery(id, 1), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().cancelSuccess(position);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().cancelFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 确认无痛分娩预约
     */
    public void surePainlessDelivery(String id, int position) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PainlessDeliveryService.class).dealPainlessDelivery(id, 0), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().cancelSuccess(position);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().cancelFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
