package com.keydom.mianren.ih_doctor.activity.online_diagnose.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.CheckItemListBean;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface CheckOrderDetailView extends BaseView {
    /**
     * 获取检查、检验单对象
     */
    CheckItemListBean getCheckOutOrder();

    /**
     * 获取问诊订单对象
     */
    InquiryBean getInqueryOrder();

    /**
     * 获取订单类型
     */
    int getType();

}