package com.keydom.ih_doctor.activity.consulting_arrange.view;

import com.keydom.ih_common.base.BaseView;

import java.util.HashMap;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：门诊排班修改共用
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface ConsultingChangeView extends BaseView {

    /**
     * 修改排班
     */
    void changeArrange();

    /**
     * 修改成功
     *
     * @param msg 成功信息
     */
    void reqSuccess(String msg);

    /**
     * 修改失败
     *
     * @param errMsg 失败信息
     */
    void reqFailed(String errMsg);

    /**
     * 获取循环排班参数
     *
     * @return 循环排班请求参数
     */
    HashMap<String, Object> getChangeMap();

    /**
     * 获取增加循环排班参数
     *
     * @return 增加循环排班请求参数
     */
    HashMap<String, Object> getAddLoopMap();

    /**
     * 获取停诊请求参数
     *
     * @return 停诊请求参数
     */
    HashMap<String, Object> getAddStopMap();


    /**
     * 公用页面，获取类型
     *
     * @return
     */
    int getType();


}