package com.keydom.mianren.ih_doctor.activity.doctor_cooperation.controller;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.DiagnoseCommonActivity;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.DiagnoseRecoderActivity;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.DoctorCooperationActivity;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.FillOutApplyActivity;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.GroupMemberActivity;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.UpdateGroupInfoActivity;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.view.DoctorCooperationView;
import com.keydom.mianren.ih_doctor.activity.online_consultation.ConsultationOrderActivity;
import com.keydom.mianren.ih_doctor.bean.GroupInfoBean;
import com.keydom.mianren.ih_doctor.bean.GroupInfoRes;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.GroupCooperateApiService;
import com.keydom.mianren.ih_doctor.view.BottomGroupCutDialog;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class DoctorCooperationController extends ControllerImpl<DoctorCooperationView> implements View.OnClickListener, IhTitleLayout.OnRightTextClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.no_group_create:
                if (getView().getIsCreate() == DoctorCooperationActivity.CAN_CREATE_GROUP) {
                    UpdateGroupInfoActivity.start(getContext(), TypeEnum.GROUP_CREATE, null);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("无法创建");
                    builder.setMessage("无创建团队权限或已经创建了一个团队！");
                    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.create().show();
                }

                break;
            case R.id.group_exchange_rl:
                GroupInfoBean bean = getView().getCurrentGroup();
                if (bean == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("没有团队");
                    builder.setMessage("请先创建或者加入一个团队！");
                    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.create().show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putBoolean(ImConstants.TEAM, true);
                ImClient.startConversation(getContext(), bean.getTid(), bundle);
                ImClient.clearUnreadCount(bean.getTid(), SessionTypeEnum.Team);
                getView().showOrHideGroupExchangeRedPoint(false);
                break;
            case R.id.group_member_rl:
                if (getView().getCurrentGroup() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("没有团队");
                    builder.setMessage("请先创建或者加入一个团队！");
                    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.create().show();
                    return;
                }
                GroupMemberActivity.start(getContext(), getView().getCurrentGroup().getId());
                break;
            case R.id.diagnosis_recoder_rl:
                DiagnoseRecoderActivity.start(getContext());
                break;
            case R.id.change_diagnose_recoder:
                DiagnoseCommonActivity.startDiagnoseChangeRecoder(getContext());
                break;
            case R.id.change_diagnose_rl:
                FillOutApplyActivity.startFillOut(getContext(),
                        FillOutApplyActivity.DOCTOR_GOURP_FILLOUT_APPLY);
                break;
            case R.id.receive_diagnose_rl:
                DiagnoseCommonActivity.startDiagnoseChangeReceive(getContext());
                break;
            case R.id.group_diagnose_recoder:
                ConsultationOrderActivity.start(getContext());
                break;
            case R.id.group_diagnose_rl:
                FillOutApplyActivity.startFillOut(getContext(),
                        FillOutApplyActivity.DIAGNOSE_FILLOUT_APPLY);
                break;
            case R.id.receive_group_diagnose_rl:
                DiagnoseCommonActivity.startDiagnoseGroupReceive(getContext());
                break;
            case R.id.group_cut:
                BottomGroupCutDialog cutDialog = new BottomGroupCutDialog(getContext(),
                        getView().getGroup());
                cutDialog.show();
                break;
            case R.id.group_edit:
                GroupInfoBean currentBean = getView().getCurrentGroup();
                if (currentBean == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("没有团队");
                    builder.setMessage("请先创建或者加入一个团队！");
                    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.create().show();
                    return;
                }
                if (!currentBean.getOwner().equals(MyApplication.userInfo.getUserCode())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("无法修改");
                    builder.setMessage("只有团队创建者才能修改团队信息！");
                    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.create().show();
                    return;
                }
                UpdateGroupInfoActivity.start(getContext(), TypeEnum.GROUP_UPDATE,
                        getView().getCurrentGroup());
                break;
            default:
        }

    }


    @Override
    public void OnRightTextClick(View v) {
        if (getView().getIsCreate() == 1) {
            UpdateGroupInfoActivity.start(getContext(), TypeEnum.GROUP_CREATE, null);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("无法创建");
            builder.setMessage("无创建团队权限或已经创建了一个团队！");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }

    }


    /**
     * 获取团队数据
     */
    public void getGroupInfo() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("doctorId", MyApplication.userInfo.getId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(GroupCooperateApiService.class).ihGroupGetUserIhGroup(map), new HttpSubscriber<GroupInfoRes>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable GroupInfoRes data) {
                getView().getGroupSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getGroupFailed(msg);
                return super.requestError(exception, code, msg);
            }


        });
    }
}
