package com.keydom.ih_doctor.activity.personal.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.push.PushManager;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.LoginActivity;
import com.keydom.ih_doctor.activity.UpdatePasswordActivity;
import com.keydom.ih_doctor.activity.personal.view.MyVisitingCardView;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.net.UserService;
import com.keydom.ih_doctor.utils.LocalizationUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：设置控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class SettingController extends ControllerImpl<MyVisitingCardView> implements View.OnClickListener {

    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_password:
                if ("".equals(SharePreferenceManager.getPhoneNumber())) {
                    UpdatePasswordActivity.start(getContext());
                } else {
                    UpdatePasswordActivity.startWithPhoneNumber(getContext());
                }

                break;
            case R.id.exit:
                new GeneralDialog(mContext, "是否确认退出?", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        loginoutFromService();
                    }
                }).show();
                break;
            case R.id.user_book:
                break;
            default:
        }
    }
    /**
     * 退出登录
     */
    private void loginoutFromService() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).logout(), new HttpSubscriber<Object>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable Object data) {

                PushManager.deleteAlias(getContext());
                SharePreferenceManager.setToken("Bearer ");
                SharePreferenceManager.setImToken("");
                SharePreferenceManager.setPhoneNumber("");
                SharePreferenceManager.setRoleId(-1);
                SharePreferenceManager.setPositionId(-1);
                LocalizationUtils.deleteFileFromLocal(getContext(), Const.USER_INFO);
                ImClient.loginOut();
                LoginActivity.start(getContext());
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                PushManager.deleteAlias(getContext());
                SharePreferenceManager.setToken("Bearer ");
                SharePreferenceManager.setImToken("");
                SharePreferenceManager.setPhoneNumber("");
                SharePreferenceManager.setRoleId(-1);
                SharePreferenceManager.setPositionId(-1);
                LocalizationUtils.deleteFileFromLocal(getContext(), Const.USER_INFO);
                ImClient.loginOut();
                LoginActivity.start(getContext());
                return super.requestError(exception, code, msg);
            }
        });
    }
}
