package com.keydom.ih_patient.activity.setting.view;

import com.keydom.ih_common.base.BaseView;

public interface GestureUnlockView extends BaseView {
    /**
     * 发送消息成功
     */
    void msgInspectSuccess();

    /**
     * 发送失败
     */
    void msgInspectFailed(String msg);

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
     * 设置手势密码成功
     */
    void setPasswordSuccess();


}
