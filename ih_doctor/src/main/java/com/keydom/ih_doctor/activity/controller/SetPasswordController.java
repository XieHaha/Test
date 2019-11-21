package com.keydom.ih_doctor.activity.controller;

import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.SetPasswordActivity;
import com.keydom.ih_doctor.activity.view.SetPasswordView;
import com.keydom.ih_doctor.bean.LoginBean;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.net.LoginApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

import static com.keydom.ih_doctor.constant.Const.PHONE_NUM;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class SetPasswordController extends ControllerImpl<SetPasswordView> implements View.OnClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_finish_btn:
                if (StringUtils.isEmpty(getView().getPassword()) || StringUtils.isEmpty(getView().getRePassword())) {
                    ToastUtil.showMessage(getContext(), "请输入密码");
                } else if (!getView().getPassword().equals(getView().getRePassword())) {
                    ToastUtil.showMessage(getContext(), "两次输入的密码不匹配");
                } else if (!CommonUtils.checkPassword(getView().getPassword())) {
                    ToastUtil.showMessage(getContext(), "密码为6-20位数字和字母组合，请修改后重试！");
                } else if (!isDigit(getView().getPassword())) {
                    ToastUtil.showMessage(getContext(), "密码为6-20位数字和字母组合，请修改后重试！");
                } else {
                    setPassword(((SetPasswordActivity) getContext()).getIntent().getStringExtra(PHONE_NUM), getView().getPassword());
                }
                break;
            default:
        }
    }

    private boolean isDigit(String str) {
        boolean isDigit = false;
        boolean isLetter = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {
                isLetter = true;
            }
        }
        return isDigit & isLetter;
    }


    /**
     * 设置密码方法<br/>
     *
     * @param phoneNo  电话号码
     * @param password 登录密码
     */
    private void setPassword(String phoneNo, String password) {
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("phoneNumber", phoneNo);
        map.put("password", password);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginApiService.class).updatePassword(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<LoginBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable LoginBean data) {
                hideLoading();
                getView().updateSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().updateFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
