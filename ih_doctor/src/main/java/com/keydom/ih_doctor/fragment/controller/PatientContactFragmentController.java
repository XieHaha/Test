package com.keydom.ih_doctor.fragment.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.bean.ImPatientInfo;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.fragment.view.PatientContactFragmentView;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.net.PatientManageApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.fragment.controller
 * @Description：患者管理所有人页面控制器
 * @Author：song
 * @Date：18/11/14 上午10:56
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:56
 */
public class PatientContactFragmentController extends ControllerImpl<PatientContactFragmentView> implements View.OnClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {

    }

    /**
     * 获取所有联系人
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
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PatientManageApiService.class).getRegList(map), new HttpSubscriber<PageBean<ImPatientInfo>>() {
            @Override
            public void requestComplete(@Nullable PageBean<ImPatientInfo> data) {
                hideLoading();
                getView().getUserListSuccess(data.getRecords(),typeEnum);
                List<String> accounts = new ArrayList<>();
                for (ImPatientInfo info : data.getRecords()) {
                    accounts.add(info.getImNumber());
                }
                ImClient.getUserInfoProvider().getUserInfoAsync(accounts, null);
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
