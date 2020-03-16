package com.keydom.mianren.ih_patient.activity.login.view;


import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.UserInfo;

/**
 * 登录view
 */
public interface ILoginView extends BaseView {

    /**
     * 登录成功
     */
    void loginSuccess(UserInfo data);

    /**
     * 登录失败
     */
    void loginFailed(String msg);

    /**
     * 获取登录名
     */
    String getAccount();

    /**
     * 获取密码
     */
    String getPassword();

    /**
     * 锁定登录
     */
    void loginLocked();

    /**
     * 获取验证码成功
     */
    void getValidateCodeSuccess(String validateCode);

    /**
     * 获取验证码失败
     */
    void getValidateCodeFailed(String errMsg);

    /**
     * 获取登录手机
     */
    String getAccountMobile();


    /**
     * 显示警告
     */
    void showWarnning();

    /**
     * 隐藏警告
     */
    void hideWarnning();

    /**
     * 获取支付宝验证码
     */
    void getAliAuth(String authCode);

    /**
     * 绑定手机
     */
    void goBindPhone(String uid,int type);

    /**
     * 判断是否锁定
     */
    boolean isLoginLocked();

    /**
     * 返回验证码
     */
    String getValidateCode();

    /**
     * 去修改密码
     */
    void toChangePwd(String msg);
}
