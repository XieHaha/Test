package com.keydom.ih_patient.activity.online_diagnoses_order.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.DiagnosesOrderBean;
import com.keydom.ih_patient.bean.LocationInfo;
import com.keydom.ih_patient.bean.entity.pharmacy.PharmacyBean;

import java.util.List;

/**
 * 在线问诊view
 */
public interface OnlineDiagnonsesOrderView extends BaseView {
    /**
     * 获取问诊订单成功
     */
    void getDiagnosesOrderListSuccess(List<DiagnosesOrderBean> orderBeanArrayList);

    /**
     * 获取问诊订单失败
     */
    void getDiagnosesOrderListFailed(String errMsg);

    /**
     * 退单成功
     */
    void returnBackSuccess();

    /**
     * 退单失败
     */
    void returnBackFailed(String errMsg);

    /**
     * 发起支付
     */
    void goPay(boolean needDispatch, String orderNum,String orderId, double totalMoney,String prescriptionId,boolean isWaiYan);

    /**
     * 支付成功
     */
    void paySuccess();

    /**
     * 获取配送费
     */
    void getDistributionFee(String fee);
    void getLocationList(List<LocationInfo> data);


    /**
     * 刷新运费
     * */
    void refreshDeliveryCostView(List<PharmacyBean> data);
    void refreshPriceView(List<PharmacyBean> data);
    void setPharmacyBeans(List<PharmacyBean> data);
}
