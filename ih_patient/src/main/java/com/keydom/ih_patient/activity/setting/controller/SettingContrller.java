package com.keydom.ih_patient.activity.setting.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.push.PushManager;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.login.UpdatePasswordActivity;
import com.keydom.ih_patient.activity.setting.GestureUnlockActivity;
import com.keydom.ih_patient.activity.setting.view.SettingView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.UserService;
import com.keydom.ih_patient.utils.LocalizationUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 设置控制
 */
public class SettingContrller extends ControllerImpl<SettingView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logout_tv:
                if (Global.getUserId() != -1) {
                    new GeneralDialog(getContext(), "确认要退出当前账号？", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            loginoutFromService();
                        }
                    }).setTitle("提示").setPositiveButton("确认").show();
                } else {
                    ToastUtil.showMessage(getContext(), getContext().getString(R.string.unlogin_hint));
                }
                break;
            case R.id.jump_to_change_password_tv:
                if (Global.getUserId() != -1) {
                    UpdatePasswordActivity.start(getContext());
                } else {
                    ToastUtil.showMessage(getContext(), getContext().getString(R.string.unlogin_hint));
                }
                break;
            case R.id.gesture_unlock_tv:
                GestureUnlockActivity.start(getContext(),GestureUnlockActivity.CREATETYPE,"","");
            default:
        }
    }

    /**
     * 退出登录
     */
    private void logOut() {
        if (LocalizationUtils.deleteFileFromLocal(getContext(), "userInfo")) {
            ImClient.loginOut();
            Logger.e("本地用户数据清除成功");
            Global.setUserId(-1);
        }
        PushManager.setAlias(getContext(), "");
        //EventBus.getDefault().post(new Event(EventType.LOGOUT,null));
        SharePreferenceManager.setToken("");
        EventBus.getDefault().post(new Event(EventType.UPDATELOGINSTATE,null));
        getView().finishSetting();
    }


    /**
     * 退出登录
     */
    private void loginoutFromService() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).logout(), new HttpSubscriber<Object>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                logOut();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                logOut();
                return super.requestError(exception, code, msg);
            }
        });
    }
}
