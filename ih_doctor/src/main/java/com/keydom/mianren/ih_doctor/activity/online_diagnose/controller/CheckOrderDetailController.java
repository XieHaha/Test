package com.keydom.mianren.ih_doctor.activity.online_diagnose.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.ApplyForCheckActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.CheckOrderDetailActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.view.CheckOrderDetailView;
import com.keydom.mianren.ih_doctor.bean.CheckItemListBean;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.DiagnoseApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class CheckOrderDetailController extends ControllerImpl<CheckOrderDetailView> implements View.OnClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.edit_order_ib:
                if (getView().getType() == CheckOrderDetailActivity.INSPACT_ORDER) {
                    ApplyForCheckActivity.startUpdateInspect(getContext(), getView().getCheckOutOrder(), getView().getInqueryOrder());
                } else {
                    ApplyForCheckActivity.startUpdateTest(getContext(), getView().getCheckOutOrder(), getView().getInqueryOrder());
                }
                break;
        }
    }


    /**
     * 获取检查单详情
     *
     * @param orderId 检查单ID
     */
    public void getInspectDetail(long orderId) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getInspectDetail(orderId), new HttpSubscriber<CheckItemListBean>(getContext(), getDisposable(), false) {
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
     *
     * @param orderId 检验单ID
     */
    public void getCheckoutDetail(long orderId) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getCheckoutDetail(orderId), new HttpSubscriber<CheckItemListBean>(getContext(), getDisposable(), false) {
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
