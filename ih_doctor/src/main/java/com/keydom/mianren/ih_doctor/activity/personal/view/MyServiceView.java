package com.keydom.mianren.ih_doctor.activity.personal.view;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.BaseView;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：我的服务view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface MyServiceView extends BaseView {


    /**
     * 获取服务成功
     */
    void getServiceSuccess(List<MultiItemEntity> list);

    /**
     * 获取服务失败
     */
    void getServiceFailed(String errMsg);

    /**
     * 打开服务成功
     */
    void enableServiceSuccess(String msg);

    /**
     * 打开失败
     */
    void enableServiceFailed(String errMsg);

    /**
     * 关闭服务成功
     */
    void disableServcieSuccess(String msg);

    /**
     * 关闭失败
     */
    void disableServiceFailed(String errMsg);

}