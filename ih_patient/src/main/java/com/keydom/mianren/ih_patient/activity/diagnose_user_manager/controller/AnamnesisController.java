package com.keydom.mianren.ih_patient.activity.diagnose_user_manager.controller;

import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.AddManageUserActivity;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.view.AnamnesisView;
import com.keydom.mianren.ih_patient.bean.HistoryListBean;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * created date: 2018/12/15 on 10:49
 * des:既往病史控制器
 */
public class AnamnesisController extends ControllerImpl<AnamnesisView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save) {
            if (getView().getManager() != null) {
                saveInfo(getView().getStatus(), getView().getManager());
            }
        }
    }

    /**
     * 获取既往病史列表
     */
    public void getHistoryList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getHistoryList(), new HttpSubscriber<HistoryListBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable HistoryListBean data) {
                getView().getHistorySuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 保存就诊人
     */
    public void saveInfo(final int state, final ManagerUserBean manager) {
        showLoading();
        Map<String, Object> map = new HashMap<>();
        map.put("registerUserId", Global.getUserId());
        map.put("name", manager.getName());
        map.put("sex", manager.getSex());
        map.put("idCard", manager.getCardId());
        map.put("birthDate", manager.getBirthday());
        map.put("phoneNumber", manager.getPhone());
        map.put("maritalHistory", manager.getMaritalHistory());
        map.put("familyHistory", manager.getFamilyHistory());
        map.put("surgeryHistory", manager.getSurgeryHistory());
        map.put("personHistory", manager.getPersonHistory());
        map.put("allergyHistory", manager.getAllergyHistory());
        map.put("address", manager.getAddress());
        map.put("area", manager.getArea());
        if (state == AddManageUserActivity.UPDATE) {
            map.put("id", manager.getId());
        }
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).addManagerUser(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                String hint = "新增成功";
                if (state == AddManageUserActivity.UPDATE) {
                    hint = "修改成功";
                }
                ToastUtils.showShort(hint);
                ActivityUtils.finishActivity(AddManageUserActivity.class);
                getView().addOrEditSuccess(manager);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
