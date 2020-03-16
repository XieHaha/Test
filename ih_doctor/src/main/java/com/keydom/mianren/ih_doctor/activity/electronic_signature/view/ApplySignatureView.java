package com.keydom.mianren.ih_doctor.activity.electronic_signature.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.SignRegInfoBean;

import java.util.Map;

public interface ApplySignatureView extends BaseView {

    /**
     * 获取注册参数
     *
     * @return
     */
    Map<String, Object> getApplyMap();

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

    /**
     * 获取用户成功
     *
     * @param data
     */
    void getUserSuccess(SignRegInfoBean data);

    /**
     * 获取用户失败
     *
     * @param errMsg
     */
    void getUserFailed(String errMsg);

    /**
     * 更换电话号码成功
     *
     * @param msg
     */
    void changeMobileSuccess(String msg);

    /**
     * 更换电话号码失败
     *
     * @param errMsg
     */
    void changeMobileFailed(String errMsg);

    /**
     * 添加任务ID成功
     *
     * @param msg
     */
    void addJobIdSuccess(String msg);

    /**
     * 添加任务ID失败
     *
     * @param errMsg
     */
    void addJobIdFailed(String errMsg);

}
