package com.keydom.ih_patient.activity.reserve_painless_delivery.view;

import com.keydom.ih_common.base.BaseView;

/**
 * @auth 顿顿
 * @date 20/2/24 15:44
 * @des
 */
public interface ReservePainlessDeliveryView extends BaseView {

    /**
     * 预约成功
     */
    void reserveSuccess();

    /**
     * 获取就诊人
     */
    void getVisitUser();

    /**
     * 获取年龄
     */
    void getAge();

    /**
     * 末次月经
     */
    void getLastDate();

    /**
     * 预产期
     */
    void getDueDate();

    /**
     * 胎数
     */
    void getFutes();

    /**
     * 电话
     */
    void getPhone();
}
