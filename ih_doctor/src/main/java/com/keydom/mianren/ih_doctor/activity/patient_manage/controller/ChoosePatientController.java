package com.keydom.mianren.ih_doctor.activity.patient_manage.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.activity.patient_manage.view.ChoosePatientView;
import com.keydom.mianren.ih_doctor.bean.ImPatientInfo;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.net.PatientManageApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ChoosePatientController extends ControllerImpl<ChoosePatientView> {

    /**
     * 获取患者
     */
    public void getUserList( final TypeEnum typeEnum) {
        showLoading();
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("doctorId", MyApplication.userInfo.getId());
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PatientManageApiService.class).getRegList(map), new HttpSubscriber<PageBean<ImPatientInfo>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PageBean<ImPatientInfo> data) {
                hideLoading();
                getView().getUserListSuccess(data.getRecords(),typeEnum);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getUserListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
