package com.keydom.mianren.ih_patient.activity.payment_records.controller;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.payment_records.view.PaymentRecordView;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 缴费记录控制器
 * @author 顿顿
 */
public class PaymentRecordController extends ControllerImpl<PaymentRecordView> {

    /**
     * 获取就诊人管理列表
     */
    public void getManagerUserList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getManagerUserList(Global.getUserId()), new HttpSubscriber<List<ManagerUserBean>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable List<ManagerUserBean> data) {
                getView().getMangerUserList(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
