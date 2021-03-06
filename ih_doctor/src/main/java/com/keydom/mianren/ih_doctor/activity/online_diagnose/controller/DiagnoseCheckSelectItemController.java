package com.keydom.mianren.ih_doctor.activity.online_diagnose.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.view.DiagnoseCheckSelectItemView;
import com.keydom.ih_common.bean.CheckOutGroupBean;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.DiagnoseApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class DiagnoseCheckSelectItemController extends ControllerImpl<DiagnoseCheckSelectItemView> implements View.OnClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {


    }

    /**
     * 获取检验项目列表
     */
    public void checkoutList(int type) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).checkoutCategoryList(type), new HttpSubscriber<List<CheckOutGroupBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<CheckOutGroupBean> data) {
                getView().getGroupListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getGroupListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取检验项目下子项目列表
     */
    public void checkoutItemList(int type, String code) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).checkoutItemList(type, code), new HttpSubscriber<List<CheckOutGroupBean>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable List<CheckOutGroupBean> data) {
                getView().getItemListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getItemListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
