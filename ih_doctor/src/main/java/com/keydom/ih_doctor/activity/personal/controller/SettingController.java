package com.keydom.ih_doctor.activity.personal.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.push.PushManager;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.LoginActivity;
import com.keydom.ih_doctor.activity.UpdatePasswordActivity;
import com.keydom.ih_doctor.activity.personal.view.MyVisitingCardView;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.utils.LocalizationUtils;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：设置控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class SettingController extends ControllerImpl<MyVisitingCardView> implements View.OnClickListener {


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
                        PushManager.deleteAlias(getContext());
                        SharePreferenceManager.setToken("");
                        SharePreferenceManager.setImToken("");
                        SharePreferenceManager.setPhoneNumber("");
                        SharePreferenceManager.setRoleId(-1);
                        SharePreferenceManager.setPositionId(-1);
                        LocalizationUtils.deleteFileFromLocal(getContext(), Const.USER_INFO);
                        ImClient.loginOut();
                        LoginActivity.start(getContext());
                    }
                }).show();
                break;
            case R.id.user_book:
                break;
            default:
        }
    }
}
