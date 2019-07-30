package com.keydom.ih_patient.activity.nursing_service.view;

import com.keydom.ih_common.base.BaseView;

import java.math.BigDecimal;

/**
 * created date: 2018/12/25 on 15:53
 * des:护理订单提交view
 */
public interface NursingApplyOrderView extends BaseView {

    /**
     * 获取总金额
     */
    BigDecimal getAllFee();

    /**
     * 获取订单号
     */
    String getOrderNum();
}
