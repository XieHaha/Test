package com.keydom.ih_patient.fragment.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.bean.BodyCheckRecordInfo;
import com.keydom.ih_patient.bean.InspectionRecordInfo;
import com.keydom.ih_patient.constant.Const;
import com.keydom.ih_patient.constant.TypeEnum;
import com.keydom.ih_patient.fragment.view.InspectionReportFmView;
import com.keydom.ih_patient.net.UserService;
import com.keydom.ih_patient.utils.DepartmentDataHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检验检查报告控制器
 */
public class InspectionReportFmController extends ControllerImpl<InspectionReportFmView> {
    /**
     * 获取检验报告
     */
    public void getInspectionReportList(String patientNumber,final TypeEnum typeEnum){
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("partientNumber", patientNumber);
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getCheckoutRecordPage(map), new HttpSubscriber<PageBean<InspectionRecordInfo>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable PageBean<InspectionRecordInfo> data) {
                getView().getDataListSuccess(DepartmentDataHelper.getInspectionInfoAfterHandle(data.getRecords()),typeEnum);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getDataListFailed(code,msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取检查报告
     */
    public void getBodyCheckReportList(String patientNumber,final TypeEnum typeEnum){
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("partientNumber", patientNumber);
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getInspectRecordPage(map), new HttpSubscriber<List<BodyCheckRecordInfo>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<BodyCheckRecordInfo> data) {
                getView().getDataListSuccess(DepartmentDataHelper.getBodyCheckInfoAfterHandle(data),typeEnum);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getDataListFailed(code,msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
