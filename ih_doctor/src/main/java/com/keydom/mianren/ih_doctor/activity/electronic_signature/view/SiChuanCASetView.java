package com.keydom.mianren.ih_doctor.activity.electronic_signature.view;

import com.keydom.ih_common.base.BaseView;

import java.util.Map;

public interface SiChuanCASetView extends BaseView {

    boolean isSign();

    /**
     * 获取注册参数
     *
     * @return
     */
    Map<String, Object> getApplyMap();

    Map<String, Object> getResetPinMap();

    /**
     * 注册成功
     *
     * @param result
     */
    void applySuccess(String result);

    /**
     * 注册失败
     *
     * @param errMsg
     */
    void applyFailed(String errMsg);

    /**
     * 获取验证码成功
     */
    void getIdentifyingCodeSuccess();

    /**
     * 获取验证码失败
     *
     * @param errMsg
     */
    void getIdentifyingCodeFailed(String errMsg);

    boolean enable();
}
