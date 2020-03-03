package com.keydom.ih_patient.activity.online_diagnoses_order.controller;

import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.diagnose_user_manager.AddManageUserActivity;
import com.keydom.ih_patient.activity.online_diagnoses_order.view.TypePatientView;
import com.keydom.ih_patient.bean.ManagerUserBean;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TypePatientController extends ControllerImpl<TypePatientView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.jump_to_patient_operate_tv){
            Intent i = new Intent(getContext(), AddManageUserActivity.class);
            i.putExtra(AddManageUserActivity.TYPE, AddManageUserActivity.ADD);
            ActivityUtils.startActivity(i);
        }
    }

    /**
     * 获取就诊人管理列表
     */
    public void getManagerUserList(){
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getManagerUserList(Global.getUserId()), new HttpSubscriber<List<ManagerUserBean>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<ManagerUserBean> data) {
                hideLoading();
                getView().getMangerUserList(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
