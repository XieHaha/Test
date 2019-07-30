package com.keydom.ih_patient.activity.certification.view;

import com.keydom.ih_common.base.BaseView;

/**
 * 验证view
 */
public interface CertificateView  extends BaseView {

    /**
     * 短信验证回调
     */
    void msgInspectSuccess();

    /**
     * 验证失败
     */
    void msgInspectFailed(String msg);

    /**
     * 获取验证码回调
     */
    void getMsgCodeSuccess();

    /**
     * 获取失败
     */
    void getMsgCodeFailed(String errMsg);

    /**
     * 实名认证回调
     */
    void idCardCertificateSuccess();

    /**
     * 实名认证失败
     */
    void idCardCertificateFailed(String errMsg);

    /**
     *获取姓名
     */
    String getName();

    /**
     * 获取身份证号
     */
    String getIdCardNum();

    /**
     * 获取手机号
     */
    String getPhoneNum();

    /**
     * 获取验证码
     */
    String getMessageCode();
}
