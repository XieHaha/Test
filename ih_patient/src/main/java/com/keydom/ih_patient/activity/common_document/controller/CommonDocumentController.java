package com.keydom.ih_patient.activity.common_document.controller;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.activity.common_document.view.CommonDocumentView;
import com.keydom.ih_patient.bean.CommonDocumentBean;
import com.keydom.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * created date: 2019/3/27 on 16:40
 * des:公共文书维护控制器
 */
public class CommonDocumentController extends ControllerImpl<CommonDocumentView> {
    /**
     * 获取文书维护详情内容
     */
    public void getOfficialDispatchAllMsgByCode(String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("hospitalId", App.hospitalId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getOfficialDispatchAllMsgByCode(map), new HttpSubscriber<CommonDocumentBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable CommonDocumentBean data) {
                if (data != null)
                    getView().getData(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
