package com.keydom.mianren.ih_patient.activity.order_physical_examination.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.SubscribeExaminationBean;

/**
 * 体检详情view
 */
public interface PhysicalExaminationDetailView extends BaseView {

    /**
     * 支付成功
     */
    void paySuccess();

    /**
     * 选择支付方式
     */
    void choosePayWay(SubscribeExaminationBean data);
}
