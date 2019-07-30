package com.keydom.ih_doctor.activity.doctor_cooperation.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.activity.doctor_cooperation.view.DiagnoseCommonView;
import com.keydom.ih_doctor.bean.ChangeDiagnoseRecoderBean;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.net.GroupCooperateApiService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
public class DiagnoseCommonController extends ControllerImpl<DiagnoseCommonView> implements OnRefreshListener, OnLoadMoreListener {


    /**
     * 获取转诊记录
     *
     * @param type 获取类型，刷新或者加载更多
     */
    public void getRecoder(final TypeEnum type) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(GroupCooperateApiService.class).list(getView().getQueryMap()), new HttpSubscriber<List<ChangeDiagnoseRecoderBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<ChangeDiagnoseRecoderBean> data) {
                getView().getListSuccess(data, type);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        getRecoder(TypeEnum.LOAD_MORE);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setCurrentPage(1);
        getRecoder(TypeEnum.REFRESH);
    }
}
