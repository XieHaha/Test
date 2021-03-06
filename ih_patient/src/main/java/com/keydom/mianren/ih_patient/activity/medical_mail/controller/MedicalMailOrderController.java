package com.keydom.mianren.ih_patient.activity.medical_mail.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.activity.medical_mail.view.MedicalMailOrderView;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.keydom.mianren.ih_patient.net.OrderService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 病案邮寄
 */
public class MedicalMailOrderController extends ControllerImpl<MedicalMailOrderView> {

    /**
     * 获取病案已邮寄
     */
    public void queryMedicalMailed(final TypeEnum typeEnum) {
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", "");
        map.put("endTime", "");
        map.put("logisticsNo", "");
        map.put("name", App.userInfo.getUserName());
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).medicalMailedOrder(HttpService.INSTANCE.json2Body(map)),
                new HttpSubscriber<PageBean<String>>(getContext(), getDisposable(), false, false) {
                    @Override
                    public void requestComplete(@Nullable PageBean<String> data) {
                        if (data != null) {
                            getView().getMedicalMailedSuccess(data.getRecords(), typeEnum);
                        }
                    }

                    @Override
                    public boolean requestError(@NotNull ApiException exception, int code,
                                                @NotNull String msg) {
                        getView().getMedicalMailedFailed(msg);
                        return super.requestError(exception, code, msg);
                    }
                });
    }

    /**
     * 获取病案未邮寄
     */
    public void queryMedicalNotMailed(final TypeEnum typeEnum) {
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("cardNumber", "");
        map.put("name", App.userInfo.getUserName());
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).medicalNotMailedOrder(HttpService.INSTANCE.json2Body(map)),
                new HttpSubscriber<PageBean<String>>(getContext(), getDisposable(), false, false) {
                    @Override
                    public void requestComplete(@Nullable PageBean<String> data) {
                        if (data != null) {
                            getView().getMedicalNotMailedSuccess(data.getRecords(), typeEnum);
                        }
                    }

                    @Override
                    public boolean requestError(@NotNull ApiException exception, int code,
                                                @NotNull String msg) {
                        getView().getMedicalNotMailedFailed(msg);
                        return super.requestError(exception, code, msg);
                    }
                });
    }
}
