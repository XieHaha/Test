package com.keydom.ih_patient.activity.apply_for_order_detail.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.CheckItemListBean;
import com.keydom.ih_patient.bean.InquiryBean;


/**
 * @Description：检查单view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface CheckOrderDetailView extends BaseView {

    /**
     * 获取检验单详情接口回调
     */
    void getCheckOutDetailSuccess(CheckItemListBean bean);

    /**
     * 获取失败
     */
    void getCheckOutDetailFailed(String errMsg);

    /**
     * 获取检查单详情接口回调
     */
    void getInspactDetailSuccess(CheckItemListBean bean);

    /**
     * 获取失败
     */
    void getInspactDetailFailed(String errMsg);

    /**
     * 获取检验单
     */
    CheckItemListBean getCheckOutOrder();

    /**
     * 获取检查单
     */
    InquiryBean getInqueryOrder();

    /**
     * 获取type
     */
    int getType();

}