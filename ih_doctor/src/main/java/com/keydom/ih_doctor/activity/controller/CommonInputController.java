package com.keydom.ih_doctor.activity.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.activity.view.CommonInputView;
import com.keydom.ih_doctor.net.PersonalApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：通用编辑页面控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class CommonInputController extends ControllerImpl<CommonInputView> {


    /**
     * 更新个人信息部分字段
     */
    public void updateInfo() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).editInfo(HttpService.INSTANCE.object2Body(getView().getUpdatePersonalInfoMap())), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().updateSuccess(data);
                hideLoading();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().updateFailed(msg);
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }


}
