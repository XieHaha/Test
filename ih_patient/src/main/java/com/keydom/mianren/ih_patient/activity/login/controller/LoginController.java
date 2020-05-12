package com.keydom.mianren.ih_patient.activity.login.controller;

import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.manager.ImPreferences;
import com.keydom.ih_common.im.manager.NimUserInfoCache;
import com.keydom.ih_common.im.manager.TeamDataCache;
import com.keydom.ih_common.im.utils.MD5;
import com.keydom.ih_common.minterface.OnLoginListener;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.login.RegisterActivity;
import com.keydom.mianren.ih_patient.activity.login.UpdatePasswordActivity;
import com.keydom.mianren.ih_patient.activity.login.view.ILoginView;
import com.keydom.mianren.ih_patient.bean.UserInfo;
import com.keydom.mianren.ih_patient.net.LoginService;
import com.keydom.mianren.ih_patient.net.PayService;
import com.keydom.mianren.ih_patient.net.UserService;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @Name：com.keydom.ih_patient.activity.controller
 * @Description：登录控制器
 * @Author：song
 * @Date：18/11/9 上午11:47
 * 修改人：xusong
 * 修改时间：18/11/9 上午11:47
 */
public class LoginController extends ControllerImpl<ILoginView> implements View.OnClickListener {

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_btn:
                if (StringUtils.isEmpty(getView().getAccount()) || StringUtils.isEmpty(getView().getPassword())) {
                    ToastUtil.showMessage(getContext(), "请检查账号或者密码是否已填写");
                } else {
                    getView().hideWarnning();
                    doLogin();
                }
                break;
            case R.id.regist:
                RegisterActivity.start(getContext());
                break;
            case R.id.forget_password_txt:
                UpdatePasswordActivity.start(getContext());
                break;
            case R.id.validate_img:
                getValidateCode(getView().getAccountMobile());
                break;
            default:
        }
    }

    /**
     * 开始登录
     */
    private void doLogin() {
        HashMap<String, String> map = new HashMap<>();
        map.put("accountMobile", getView().getAccount());
        map.put("password", MD5.getStringMD5(getView().getPassword()));
        if (getView().isLoginLocked())
            map.put("code", getView().getValidateCode());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginService.class).doLogin(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<UserInfo>(getContext(), getDisposable(), true,false) {
            @Override
            public void requestComplete(@Nullable UserInfo data) {
                ImClient.loginIM(data.getId() + "", data.getImToken(), new OnLoginListener() {
                    @Override
                    public void success(String msg) {
                        ImClient.getUserInfoProvider().setAccount(data.getId() + "");
                        NimUserInfoCache.getInstance().buildCache();
                        TeamDataCache.getInstance().buildCache();
                        ImPreferences.saveUserAccount(data.getId() + "");
                        ImPreferences.saveUserToken(data.getImToken());
                       /* if (BuildConfig.DEBUG) {
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                        }*/
                        SharePreferenceManager.setUserCode(getView().getAccount());
                        getView().loginSuccess(data);
                    }

                    @Override
                    public void failed(String errMsg) {
                        Logger.e(errMsg);
                    }
                });

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                if (code == 305) {
                    getView().loginLocked();
                    getValidateCode(getView().getAccountMobile());
                } else if (code == 307) {
                    ToastUtil.showMessage(getContext(), "验证码错误,请重新填写");
                    getValidateCode(getView().getAccountMobile());
                } else if (code == 201) {
                    ToastUtil.showMessage(getContext(), "密码验证不通过");
                    if (getView().isLoginLocked())
                        getValidateCode(getView().getAccountMobile());
                }else if(code == 313){
                    getView().toChangePwd(msg);
                } else {
                    getView().showWarnning();
                    getView().loginFailed(msg);
                }
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getValidateCode(String accountMobile) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).sendCodeThreeTime(accountMobile), new HttpSubscriber<String>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().getValidateCodeSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getValidateCodeFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取支付宝验证码
     */
    public void getAliAuth() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PayService.class).aliPayAuthToken(), new HttpSubscriber<String>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().getAliAuth(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 第三方登录 1支付宝 2微信 3.QQ  Integer类型
     */
    public void loginTrilateral(String uid, int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("type", type);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).loginTrilateral(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<UserInfo>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable UserInfo data) {
                ImClient.loginIM(data.getId() + "", data.getImToken(), new OnLoginListener() {
                    @Override
                    public void success(String msg) {
                        ImClient.getUserInfoProvider().setAccount(data.getUserAccount());
                        NimUserInfoCache.getInstance().buildCache();
                        TeamDataCache.getInstance().buildCache();
                        ImPreferences.saveUserAccount(data.getId() + "");
                        ImPreferences.saveUserToken(data.getImToken());
/*
                        if (BuildConfig.DEBUG) {
                        }*/
                        getView().loginSuccess(data);
                    }

                    @Override
                    public void failed(String errMsg) {
                        Logger.e(errMsg);
                    }
                });
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtils.showShort(msg);
                if (code == 201) {
                    getView().goBindPhone(uid, type);
                }
                return super.requestError(exception, code, msg);
            }
        });
    }
}
