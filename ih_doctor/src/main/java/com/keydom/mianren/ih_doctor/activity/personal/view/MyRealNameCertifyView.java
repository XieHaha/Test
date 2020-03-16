package com.keydom.mianren.ih_doctor.activity.personal.view;

import com.keydom.ih_common.base.BaseView;

import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：我的实名view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface MyRealNameCertifyView extends BaseView {


    /**
     * 获取实名请求参数
     */
    Map<String, Object> getRealNameMap();

    /**
     * 获取验证码请求参数
     */
    Map<String, Object> getCodeMap();

    /**
     * 实名认证成功
     */
    void realNameSuccess(String msg);

    /**
     * 实名认证失败
     */
    void realNameFailed(String errMsg);

    /**
     * 获取验证码成功
     */
    void getCodeSuccess(String msg);

    /**
     * 获取验证码失败
     */
    void getCodeFailed(String msg);

    /**
     * 检查手机号
     */
    boolean checkPhone();

    /**
     * 检查是否实名
     */
    boolean checkRealMap();

    /**
     * 身份证号
     */
    String getIdCard();

}