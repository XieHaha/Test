package com.keydom.mianren.ih_doctor.fragment.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.fragment.view.DiagnoseOrderFragmentView;
import com.keydom.mianren.ih_doctor.net.DiagnoseApiService;
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
public class DiagnoseOrderFragmentController extends ControllerImpl<DiagnoseOrderFragmentView> implements OnRefreshListener, OnLoadMoreListener {


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
        getHeadNurseServiceOrderList(type);
    }

    /**
     * 获取问诊订单
     *
     * @param type 订单type
     */
    private void getHeadNurseServiceOrderList(final TypeEnum type) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).listInquisition(getView().getListMap()), new HttpSubscriber<List<InquiryBean>>() {
            @Override
            public void requestComplete(@Nullable List<InquiryBean> data) {
                getView().getDataSuccess(type, data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getDataFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
