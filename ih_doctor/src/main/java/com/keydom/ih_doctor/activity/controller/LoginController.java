package com.keydom.ih_doctor.activity.controller;

import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.manager.ImPreferences;
import com.keydom.ih_common.im.manager.NimUserInfoCache;
import com.keydom.ih_common.im.manager.TeamDataCache;
import com.keydom.ih_common.minterface.OnLoginListener;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.UpdatePasswordActivity;
import com.keydom.ih_doctor.activity.view.LoginView;
import com.keydom.ih_doctor.bean.LoginBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.net.LoginApiService;
import com.keydom.ih_doctor.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：登录控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class LoginController extends ControllerImpl<LoginView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (getView().isCode()) {
                    if (StringUtils.isEmpty(getView().getCode())) {
                        ToastUtil.shortToast(getContext(), "请输入验证码");
                        return;
                    }
                    if (StringUtils.isEmpty(getView().getUserName()) || StringUtils.isEmpty(getView().getPassword())) {
                        ToastUtil.shortToast(getContext(), "请输入正确的用户名和密码");
                        return;
                    }
                } else {
                    if (StringUtils.isEmpty(getView().getUserName()) || StringUtils.isEmpty(getView().getPassword())) {
                        ToastUtil.shortToast(getContext(), "请输入正确的用户名和密码");
                        return;
                    }
                }
                login(getView().getUserName(), getView().getPassword(), getView().getCode());

                break;
            case R.id.forget_password_txt://忘记密码
                UpdatePasswordActivity.start(getContext());
                break;
            case R.id.identifying_code_iv:
                getLoginCode(getView().getUserName());
                break;
            default:
        }

    }


    /**
     * 登陆方法<br/>
     * 登陆成功后立刻登陆三方IM平台<br/>
     * 登陆三方调用类
     * <li><a href="com.keydom.ih_common.im.ImClient.loginIM(final String account, String token, final OnLoginListener listener)">三方登陆参考这个方法</a></li>
     *
     * @param account  登陆账号，必填。
     * @param password 登陆密码，必填。
     * @param code     登陆验证码 在登陆失败次数过多，账号锁定的时候必填。
     */
    public void login(String account, String password, String code) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("accountMobile", account);
        map.put("password", password);
        map.put("code", code);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginApiService.class).login(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<LoginBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable final LoginBean data) {
                hideLoading();
                if (data.getUserCode() == null || data.getImToken() == null || "".equals(data.getUserCode()) || "".equals(data.getImToken())) {
                    getView().loginFailed(302, "帐号错误，请检查后重试！");
                } else {
                    ImClient.loginIM(data.getUserCode(), data.getImToken(), new OnLoginListener() {
                        @Override
                        public void success(String msg) {
                            ImClient.getUserInfoProvider().setAccount(data.getUserCode());
                            NimUserInfoCache.getInstance().buildCache();
                            TeamDataCache.getInstance().buildCache();
                            ImPreferences.saveUserAccount(data.getUserCode());
                            ImPreferences.saveUserToken(data.getImToken());
                            getView().loginSuccess(data);
                        }

                        @Override
                        public void failed(String errMsg) {
                            getView().loginFailed(302, "聊天服务器登录失败");
                        }
                    });
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().loginFailed(code, msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 获取验证码<br/>
     * 用于登陆失败次数过多，获取登陆验证码使用<br/>
     * 获取的验证码为Base64格式的图片<br/>
     *
     * @param account 获取验证码的账号
     */
    public void getLoginCode(String account) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("accountMobile", account);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginApiService.class).loginSendCode(map), new HttpSubscriber<String>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().getLoginCodeSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getLoginCodeFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
