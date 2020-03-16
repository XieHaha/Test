package com.keydom.mianren.ih_patient.activity.nursing_order.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.NursingOrderDetailBean;

/**
 * created date: 2018/12/19 on 14:55
 * des:待处理订单veiw
 */
public interface WaitForAdmissionView extends BaseView {
    /**
     * 初始化页面
     */
    void launchInfo();

    /**
     * 获取接口基本数据
     */
    void getData(NursingOrderDetailBean data);

    /**
     * 获取订单实体
     */
    NursingOrderDetailBean getOrder();

    /**
     *  支付成功
     */
    void paySuccess();
}
