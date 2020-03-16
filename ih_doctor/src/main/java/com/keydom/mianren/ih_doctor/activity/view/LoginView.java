package com.keydom.mianren.ih_doctor.activity.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.LoginBean;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface LoginView extends BaseView {

    /**
     * 登录成功
     *
     * @param info 登录用户实体
     */
    void loginSuccess(LoginBean info);


    /**
     * 登录失败
     *
     * @param code   登录失败code
     * @param errMsg 登录失败信息
     */
    void loginFailed(int code, String errMsg);

    /**
     * 获取登录验证码成功
     *
     * @param msg 登录验证码
     */
    void getLoginCodeSuccess(String msg);

    /**
     * 获取登录验证码失败
     *
     * @param msg 失败信息
     */

    void getLoginCodeFailed(String msg);

    /**
     * 获取输入的密码
     *
     * @return 输入的密码
     */
    String getPassword();

    /**
     * 获取输入的账户
     *
     * @return 输入的账户
     */
    String getUserName();

    /**
     * 获取输入的验证码
     *
     * @return 输入的验证码
     */
    String getCode();

    /**
     * 是否需要添加验证码参数
     *
     * @return true 需要添加验证码， false 不需要添加验证码
     */
    boolean isCode();
}