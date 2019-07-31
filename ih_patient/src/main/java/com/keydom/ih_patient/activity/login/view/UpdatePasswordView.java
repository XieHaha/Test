package com.keydom.ih_patient.activity.login.view;

import com.keydom.ih_common.base.BaseView;

/**
 * 更新密码view
 */
public interface UpdatePasswordView extends BaseView  {
    /**
     * 发送消息成功
     */
    void msgInspectSuccess();

    /**
     * 发送失败
     */
    void msgInspectFailed(String msg);

    /**
     * 更改成功
     */
    void registerSuccess();

    /**
     * 更改失败
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
     * 返回验证码
     */
    String getMsgCode();

    /**
     * 获取密码
     */
    String getPassWord();

    /**
     * 获取明文密码
     */
    String getRePassWord();

    /**
     * 修改成功
     */
    void completeUpdate();
}
