package com.keydom.mianren.ih_doctor.fragment.controller;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.SearchActivity;
import com.keydom.mianren.ih_doctor.activity.patient_manage.NewGroupChatActivity;
import com.keydom.mianren.ih_doctor.activity.patient_manage.WarrantListActivity;
import com.keydom.mianren.ih_doctor.bean.PermissionBean;
import com.keydom.mianren.ih_doctor.fragment.view.PatientManageFragmentView;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.PatientManageApiService;
import com.keydom.mianren.ih_doctor.utils.SelectHospitalPopUtil;

import org.jetbrains.annotations.Nullable;

/**
 * @Name：com.keydom.ih_doctor.fragment.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/14 上午10:56
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:56
 */
public class PatientManageFragmentController extends ControllerImpl<PatientManageFragmentView> implements View.OnClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.authorize_rl:
                if (getView().getEmpowerState() == 1) {
                    WarrantListActivity.start(getContext());
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("无法授权");
                    builder.setMessage("该账户无此操作权限！");
                    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.create().show();
                }
                break;
            case R.id.create_group_rl:
                if (getView().getBuildingGroupState() == 1)
                    NewGroupChatActivity.start(getContext());
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("无法建群");
                    builder.setMessage("该账户无此操作权限！");
                    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.create().show();
                }
                break;
            case R.id.search_btn:
                SearchActivity.start(getContext());
                break;
            case R.id.top_hospital_name:
                SelectHospitalPopUtil.getInstance().initPopWindow(getContext()).showHospitalPopupWindow(getView().getTitleLayout());
                break;
            default:
        }
    }

    public void getPermission(String doctorId) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PatientManageApiService.class).getPermission(doctorId), new HttpSubscriber<PermissionBean>() {
            @Override
            public void requestComplete(@Nullable PermissionBean data) {
                getView().getPermissionSuccess(data);
            }
        });
    }
}
