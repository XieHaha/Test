package com.keydom.ih_patient.activity.payment_records.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.LocationInfo;
import com.keydom.ih_patient.bean.PayRecordBean;
import com.keydom.ih_patient.bean.entity.pharmacy.PharmacyBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * 未缴费view
 */
public interface UnpayRecordView extends BaseView {
    /**
     * 获取记录回调
     */
    void paymentListCallBack(List<PayRecordBean> list);

    /**
     * 获取总价
     */
    BigDecimal getTotalPay();

    /**
     * 获取选中列表
     */
    List<PayRecordBean> getSelectList();

    /**
     * 获取选中订单
     */
    String getDocument();

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

    //刷新列表
    void refreshData();
}
