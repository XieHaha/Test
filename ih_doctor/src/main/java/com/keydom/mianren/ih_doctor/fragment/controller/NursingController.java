package com.keydom.mianren.ih_doctor.fragment.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.bean.NursingProjectInfo;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.fragment.view.NursingView;
import com.keydom.mianren.ih_doctor.net.NurseServiceApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class NursingController extends ControllerImpl<NursingView> {

    /**
     * 获取基础护理或者专科护理或者产后护理
     *
     * @param id 护理类型ID
     */
    public void getNurseServiceProjectByCateId(String id,  final TypeEnum typeEnum) {
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        map.put("hospitalId", MyApplication.userInfo.getHospitalId());
        map.put("hospitalDeptId",MyApplication.userInfo.getDeptId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).getNurseServiceProjectByCateId(map), new HttpSubscriber<PageBean<NursingProjectInfo>>() {
            @Override
            public void requestComplete(@Nullable PageBean<NursingProjectInfo> data) {
                getView().getNursingProjectSuccess(data.getRecords(),typeEnum);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getNursingProjectFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
