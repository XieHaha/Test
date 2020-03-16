package com.keydom.mianren.ih_doctor.fragment.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.bean.PrescriptionBean;
import com.keydom.mianren.ih_doctor.constant.ServiceConst;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.fragment.view.PrescriptionFragmentView;
import com.keydom.mianren.ih_doctor.net.PrescriptionService;
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
public class PrescriptionFragmentController extends ControllerImpl<PrescriptionFragmentView> implements OnRefreshListener, OnLoadMoreListener {


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        currentPagePlus();
        getData(TypeEnum.LOAD_MORE);

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setCurrentPage(1);
        getData(TypeEnum.REFRESH);

    }

    public void getData(TypeEnum type) {
        if (ServiceConst.DOCTOR_PRESCRIPTION_SERVICE_CODE.equals(getView().getStartCod())) {
            getDoctorPrescriptionList(type);
        } else if(ServiceConst.MEDICINE_PRESCRIPTION_SERVICE_CODE.equals(getView().getStartCod())){
            getDrugsPrescriptionList(type);
        }
    }

    private void getDoctorPrescriptionList(final TypeEnum type) {
//        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getDoctorPrescriptionList(getView().getListMap()), new HttpSubscriber<List<PrescriptionBean>>() {
            @Override
            public void requestComplete(@Nullable List<PrescriptionBean> data) {
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

    private void getDrugsPrescriptionList(final TypeEnum type) {
//        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getDrugsPrescriptionList(getView().getListMap()), new HttpSubscriber<List<PrescriptionBean>>() {
            @Override
            public void requestComplete(@Nullable List<PrescriptionBean> data) {
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
