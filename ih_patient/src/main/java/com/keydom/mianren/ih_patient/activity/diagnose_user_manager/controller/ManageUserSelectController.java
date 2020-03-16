package com.keydom.mianren.ih_patient.activity.diagnose_user_manager.controller;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.view.ManageUserSelectView;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 就诊人管理控制器
 */
public class ManageUserSelectController extends ControllerImpl<ManageUserSelectView> {

    /**
     * 获取就诊人管理列表
     */
    public void getManagerUserList() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getManagerUserList(Global.getUserId()), new HttpSubscriber<List<ManagerUserBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<ManagerUserBean> data) {
                hideLoading();
                getView().getMangerUserList(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
