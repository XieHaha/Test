package com.keydom.mianren.ih_patient.activity.online_diagnoses_search.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_search.view.DiagnoseSearchView;
import com.keydom.mianren.ih_patient.bean.RecommendDocAndNurBean;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.keydom.mianren.ih_patient.net.UserService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * 问诊搜索控制器
 */
public class DiagnoseSearchController extends ControllerImpl<DiagnoseSearchView> implements OnRefreshListener, OnLoadMoreListener {

    Map<String, Object> mCurrentMap;

    /**
     * 查询医生或者护士
     */
    public void searchDoctor(Map<String, Object> map, TypeEnum type) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getListHomeRecommendDoctor(map), new HttpSubscriber<PageBean<RecommendDocAndNurBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PageBean<RecommendDocAndNurBean> data) {
                mCurrentMap = map;
                getView().getSearchSuccess(data.getRecords(), type);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getSearchFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        searchDoctor(mCurrentMap, TypeEnum.LOAD_MORE);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setDefaultPage();
        searchDoctor(mCurrentMap, TypeEnum.REFRESH);
    }
}
