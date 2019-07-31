package com.keydom.ih_doctor.activity.nurse_service.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.activity.nurse_service.view.SelectNurseView;
import com.keydom.ih_doctor.bean.NurseBean;
import com.keydom.ih_doctor.net.NurseServiceApiService;

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
public class SelectNurseController extends ControllerImpl<SelectNurseView> {
    /**
     * 获取护士列表
     */
    public void getNurseList() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).getNurserByHospitalIdOrDeptId(getView().getQueryMap()), new HttpSubscriber<List<NurseBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<NurseBean> data) {
                hideLoading();
                getView().getNurseListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getNurseListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
