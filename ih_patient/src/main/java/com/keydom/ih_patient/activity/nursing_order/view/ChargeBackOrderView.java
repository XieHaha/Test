package com.keydom.ih_patient.activity.nursing_order.view;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.NursingOrderChargeBackBean;

import java.util.List;

/**
 * created date: 2018/12/18 on 17:57
 * des:退单view
 */
public interface ChargeBackOrderView extends BaseView {
    /**
     * 获取数据
     */
    void getData(List<MultiItemEntity> items);

    /**
     * 获取退单实体
     */
    NursingOrderChargeBackBean getChargeBackBean();

    /**
     * 获取初始实体回调
     */
    void getInitBean(NursingOrderChargeBackBean bean);

    /**
     * 退单成功
     */
    void chargeBackSuccess();
}
