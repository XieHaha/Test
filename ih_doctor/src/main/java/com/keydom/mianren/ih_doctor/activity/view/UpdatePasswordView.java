package com.keydom.mianren.ih_doctor.activity.view;

import com.keydom.ih_common.base.BaseView;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 下午1:26
 * 修改人：xusong
 * 修改时间：18/11/16 下午1:26
 */
public interface UpdatePasswordView extends BaseView {
    /**
     * 获取电话号码
     *
     * @return 输入的电话号码
     */
    String getPhoneNo();

    /**
     * 获取输入的验证码
     *
     * @return 输入的验证码
     */
    String getIdentifyingCode();

    /**
     * 判断是否勾选协议
     *
     * @return true 勾选了协议，false 没有勾选协议
     */
    boolean getIsAgreement();

    /**
     * 获取验证码成功
     */
    void getIdentifyingCodeSuccess();

    /**
     * 获取验证码失败
     *
     * @param errMsg 失败信息
     */
    void getIdentifyingCodeFailed(String errMsg);

    /**
     * 校验验证码成功
     */
    void verifyCodeSuccess();

    /**
     * 校验验证码失败
     *
     * @param msg 失败信息
     */
    void verifyCodeFailed(String msg);
}
