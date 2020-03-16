package com.keydom.mianren.ih_doctor.fragment.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.bean.NurseServiceListBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.fragment.view.NurseServiceOrderFragmentView;
import com.keydom.mianren.ih_doctor.net.NurseServiceApiService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.fragment.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 下午2:26
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:26
 */
public class NurseServiceOrderFragmentController extends ControllerImpl<NurseServiceOrderFragmentView> implements OnRefreshListener, OnLoadMoreListener {


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        getData(TypeEnum.LOAD_MORE);

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setCurrentPage(1);
        getData(TypeEnum.REFRESH);

    }

    public void getData(TypeEnum type) {
        if (getView().getType() == TypeEnum.HEAD_NURSE_FRAGMENT_ALREADY_RECEIVE_ORDER || getView().getType() == TypeEnum.HEAD_NURSE_FRAGMENT_RECEIVE_ORDER) {
            getHeadNurseServiceOrderList(type);
        } else {
            getCommonNurseServiceOrderList(type);
        }
    }

    private void getCommonNurseServiceOrderList(final TypeEnum type) {
//        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).getCommonNurseServiceOrderList(getView().getListMap()), new HttpSubscriber<List<NurseServiceListBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<NurseServiceListBean> data) {
                hideLoading();
                getView().getDataSuccess(type, data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getDataFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }

    private void getHeadNurseServiceOrderList(final TypeEnum type) {
//        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).getHeadNurseServiceOrderList(getView().getListMap()), new HttpSubscriber<List<NurseServiceListBean>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<NurseServiceListBean> data) {
                hideLoading();
                getView().getDataSuccess(type, data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getDataFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
