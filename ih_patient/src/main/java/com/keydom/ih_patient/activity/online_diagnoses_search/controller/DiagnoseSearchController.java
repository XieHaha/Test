package com.keydom.ih_patient.activity.online_diagnoses_search.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.activity.online_diagnoses_search.view.DiagnoseSearchView;
import com.keydom.ih_patient.bean.RecommendDocAndNurBean;
import com.keydom.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * 问诊搜索控制器
 */
public class DiagnoseSearchController extends ControllerImpl<DiagnoseSearchView> {

    /**
     * 查询医生或者护士
     */
    public void searchDoctor(Map<String,Object> map){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getListHomeRecommendDoctor(map), new HttpSubscriber<List<RecommendDocAndNurBean>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<RecommendDocAndNurBean> data) {
                getView().getSearchSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getSearchFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
