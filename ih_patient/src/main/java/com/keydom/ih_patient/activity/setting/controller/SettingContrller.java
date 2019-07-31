package com.keydom.ih_patient.activity.setting.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.push.PushManager;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.login.UpdatePasswordActivity;
import com.keydom.ih_patient.activity.setting.GestureUnlockActivity;
import com.keydom.ih_patient.activity.setting.view.SettingView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.utils.LocalizationUtils;
import com.keydom.ih_patient.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

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
                            logOut();
                        }
                    }).setTitle("提示").setPositiveButton("确认").show();
                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }
                break;
            case R.id.jump_to_change_password_tv:
                if (Global.getUserId() != -1) {
                    UpdatePasswordActivity.start(getContext());
                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
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
        EventBus.getDefault().post(new Event(EventType.UPDATELOGINSTATE,null));
        getView().finishSetting();
    }
}
