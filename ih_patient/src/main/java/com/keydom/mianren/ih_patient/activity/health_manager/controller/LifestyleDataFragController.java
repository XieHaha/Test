package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.activity.health_manager.view.LifestyleDataFragView;
import com.keydom.mianren.ih_patient.bean.EatItemBean;
import com.keydom.mianren.ih_patient.bean.SportsItemBean;
import com.keydom.mianren.ih_patient.net.ChronicDiseaseService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static com.keydom.mianren.ih_patient.activity.health_manager.LifestyleMainActivity.LIFESTYLE_DIET;


/**
 * 生活方式数据
 *
 * @author 顿顿
 */
public class LifestyleDataFragController extends ControllerImpl<LifestyleDataFragView> {


    /**
     * 获取食物库列表
     */
    public void foodBankList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).foodBankList(HttpService.INSTANCE.object2Body(getParams())), new HttpSubscriber<PageBean<EatItemBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PageBean<EatItemBean> data) {
                if (data != null) {
                    getView().requestFoodBankListSuccess(data.getRecords());
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取运动库列表
     */
    public void exerciseBankList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).exerciseBankList(HttpService.INSTANCE.object2Body(getParams())), new HttpSubscriber<PageBean<SportsItemBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PageBean<SportsItemBean> data) {
                if (data != null) {
                    getView().requestExerciseBankListSuccess(data.getRecords());
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    private Map<String, Object> getParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("currentPage", 0);
        params.put("pageSize", 100);
        params.put("type", getView().getProjectId());
        //        params.put("keyword", "");
        if (getView().getLifestyleType() == LIFESTYLE_DIET) {
            params.put("patientId", getView().getPatientId());
        }
        return params;
    }
}
