package com.keydom.ih_doctor.activity.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.AgreementBean;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：协议
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface AgreementView extends BaseView {


    /**
     * 开通成功
     *
     * @param msg 成功信息
     */
    void openSuccess(String msg);

    /**
     * 开通失败
     *
     * @param errMsg 失败信息
     */
    void openFailed(String errMsg);

    /**
     * 打开服务成功
     */
    void enableServiceSuccess(String msg);

    /**
     * 打开失败
     */
    void enableServiceFailed(String errMsg);

    /**
     * 获取服务ID
     *
     * @return
     */
    long getServiceId();

    int getAgreementType();

    void getOfficialDispatchSuccess(AgreementBean data);

    void getOfficialDispatchFailed(String errMsg);
}