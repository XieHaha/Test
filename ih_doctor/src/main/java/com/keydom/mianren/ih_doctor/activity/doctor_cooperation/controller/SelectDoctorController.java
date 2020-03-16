package com.keydom.mianren.ih_doctor.activity.doctor_cooperation.controller;

import android.view.View;
import android.widget.AdapterView;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.SelectDoctorActivity;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.view.SelectDoctorView;
import com.keydom.mianren.ih_doctor.bean.DeptDoctorBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.GroupCooperateApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class SelectDoctorController extends ControllerImpl<SelectDoctorView> implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
        }

    }

    /**
     * 获取医护人员列表
     */
    public void getDoctorList(final TypeEnum typeEnum) {
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(GroupCooperateApiService.class).listDoctor(getView().getQueryMap()), new HttpSubscriber<PageBean<DeptDoctorBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PageBean<DeptDoctorBean> data) {
                getView().getDoctorListSuccess(data.getRecords(),typeEnum);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getDoctorListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 获取该医生所有团队中的成员
     */
    public void ihGroupQueryDoctorTeamAllUser() {
        Map<String, Object> map = new HashMap<>();
        map.put("userCode", MyApplication.userInfo.getUserCode());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(GroupCooperateApiService.class).ihGroupQueryDoctorTeamAllUser(map), new HttpSubscriber<List<DeptDoctorBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<DeptDoctorBean> data) {
                getView().getDoctorListSuccess(data,TypeEnum.REFRESH);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getDoctorListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 添加团队成员
     */
    public void addDoctorList() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(GroupCooperateApiService.class).ihGroupAddTeamMembers(HttpService.INSTANCE.object2Body(getView().getAddMap())), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().addDoctorSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().addDoctorFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        getView().selectDept(position);
        ((SelectDoctorActivity) getContext()).pageLoading();
        getDoctorList(TypeEnum.REFRESH);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
