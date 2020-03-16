package com.keydom.mianren.ih_patient.activity.login.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.UserInfo;

/**
 * 注册view
 */
public interface IRegisterView extends BaseView {

    /**
     * 发送消息成功
     */
    void msgInspectSuccess();

    /**
     * 发送失败
     */
    void msgInspectFailed(String msg);

    /**
     * 注册成功
     */
    void registerSuccess(UserInfo data);

    /**
     * 注册失败
     */
    void registerFailed(String msg);

    /**
     * 获取验证码成功
     */
    void getMsgCodeSuccess();

    /**
     * 获取失败
     */
    void getMsgCodeFailed(String errMsg);

    /**
     * 获取手机号
     */
    String getPhoneNum();

    /**
     * 获取验证码
     */
    String getMsgCode();

    /**
     * 获取账号
     */
    String getAccount();

    /**
     * 获取密码
     */
    String getPassWord();

    /**
     * 获取明文密码
     */
    String getRePassWord();

    /**
     * 关闭页面
     */
    void Finish();

    /**
     * 获取类型
     */
    int getType();

    /**
     * 获取用户id
     */
    String getUid();

    /**
     * 绑定手机
     */
    void bindPhone();

    /**
     * 判断是否绑定
     */
    boolean isBind();

}
