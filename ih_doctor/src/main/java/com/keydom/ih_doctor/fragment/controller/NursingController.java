package com.keydom.ih_doctor.fragment.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.bean.NursingProjectInfo;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.fragment.view.NursingView;
import com.keydom.ih_doctor.net.NurseServiceApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NursingController extends ControllerImpl<NursingView> {

    /**
     * 获取基础护理或者专科护理或者产后护理
     *
     * @param id 护理类型ID
     */
    public void getNurseServiceProjectByCateId(String id, String currentPage) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("currentPage", currentPage);
        map.put("pageSize", Const.PAGE_SIZE);
        map.put("hospitalId", MyApplication.userInfo.getHospitalId());
        map.put("hospitalDeptId",MyApplication.userInfo.getDeptId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).getNurseServiceProjectByCateId(map), new HttpSubscriber<List<NursingProjectInfo>>() {
            @Override
            public void requestComplete(@Nullable List<NursingProjectInfo> data) {
                getView().getNursingProjectSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getNursingProjectFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
