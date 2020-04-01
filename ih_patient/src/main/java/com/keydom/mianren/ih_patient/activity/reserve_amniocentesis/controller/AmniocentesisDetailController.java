package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.controller;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisDetailView;
import com.keydom.mianren.ih_patient.bean.AmniocentesisBean;
import com.keydom.mianren.ih_patient.net.AmniocentesisService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @date 20/3/11 15:26
 * @des 羊水穿刺预约详情
 */
public class AmniocentesisDetailController extends ControllerImpl<AmniocentesisDetailView> {

    /**
     * 获取羊水穿刺预约详情
     */
    public void getAmniocentesisDetail(int id) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(AmniocentesisService.class).getAmniocentesisDetail(id),
                new HttpSubscriber<AmniocentesisBean>(getContext(), getDisposable(),
                        true, false) {
                    @Override
                    public void requestComplete(@Nullable AmniocentesisBean data) {
                        getView().onAmniocentesisDetailSuccess();
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
