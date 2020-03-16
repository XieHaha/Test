package com.keydom.mianren.ih_doctor.activity.doctor_cooperation.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.SelectDoctorActivity;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.view.GroupMemberView;
import com.keydom.mianren.ih_doctor.bean.DeptDoctorBean;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.GroupCooperateApiService;

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
public class GroupMemberController extends ControllerImpl<GroupMemberView> implements View.OnClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_member:
                SelectDoctorActivity.startSelfDeptDoctor(getContext(), getView().getSelectedList(), getView().getGroupId());
                break;
            default:
        }

    }

    /**
     * 获取团队中所有成员
     */
    public void ihGroupQueryDoctorTeamAllUser() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(GroupCooperateApiService.class).queryDoctorTeamAllUserAuthorization(getView().getQueryMap()), new HttpSubscriber<List<DeptDoctorBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<DeptDoctorBean> data) {
                getView().getGroupMemberSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getGroupMemberFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
