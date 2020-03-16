package com.keydom.mianren.ih_patient.activity.order_doctor_register.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.bean.PaymentOrderBean;

import java.util.List;
import java.util.Map;

/**
 * 选择就诊卡view
 */
public interface ChooseMedicalCardView extends BaseView {

    /**
     * 打开支付弹窗
     */
    void showPayDialog();

    /**
     * 获取就诊卡
     */
    void getAllCardSuccess(List<MedicalCardInfo> dataList);

    /**
     * 获取失败
     */
    void getAllCardFailed(String errMsg);

    /**
     * 创建订单成
     */
    void createOrderNumSuccess(PaymentOrderBean paymentOrderBean);

    /**
     * 创建订单失败
     */
    void createOrderNumFailed(String errMsg);

    /**
     * 获取创建订单请求map
     */
    Map<String,Object> getCreateOrderNumQueryMap();

    void completeOrder();
}
