package com.keydom.ih_patient.activity.nursing_order.view;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.NursingOrderBean;
import com.keydom.ih_patient.bean.NursingOrderDetailBean;

import java.util.List;

/**
 * created date: 2018/12/20 on 10:55
 * des:护理订单详情view
 */
public interface SentListView extends BaseView {
    /**
     * 初始化数据
     */
    void launchInfo();

    /**
     * 获取接口基本数据
     */
    void getBasicData(NursingOrderDetailBean data,boolean isPay);

    /**
     * 处理list数据后回调
     */
    void getListData(List<MultiItemEntity> list);

    /**
     * 获取订单实体
     */
    NursingOrderBean getOrderDetail();
    /**
     * 获取订单id
     */
    long getId();
    /**
     * 获取订单state
     */
    int getState();

    /**
     * 支付成功
     */
    void paySuccess();
}
