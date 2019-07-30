package com.keydom.ih_doctor.activity.online_diagnose.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.activity.online_diagnose.view.DiagnoseCheckSelectItemView;
import com.keydom.ih_doctor.bean.CheckOutItemBean;
import com.keydom.ih_doctor.net.DiagnoseApiService;

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
    @Override
    public void onClick(View v) {


    }


    /**
     * 获取检验项目列表
     */
    public void checkoutList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).checkoutList(MyApplication.userInfo.getHospitalId()), new HttpSubscriber<List<CheckOutItemBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<CheckOutItemBean> data) {
                getView().getItemListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getItemListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
