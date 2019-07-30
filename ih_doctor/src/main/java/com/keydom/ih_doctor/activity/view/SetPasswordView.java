package com.keydom.ih_doctor.activity.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.LoginBean;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface SetPasswordView extends BaseView {

    /**
     * 更新密码成功
     *
     * @param userInfo 修改成功返回实体，同登录成功返回
     */
    void updateSuccess(LoginBean userInfo);

    /**
     * 更新密码失败
     *
     * @param errMsg 错误信息
     */
    void updateFailed(String errMsg);

    /**
     * 获取第一次输入的登录密码
     *
     * @return 第一次登录密码
     */
    String getPassword();

    /**
     * 获取第二次输入的登录密码
     *
     * @return 第二次输入的登录密码
     */
    String getRePassword();
}