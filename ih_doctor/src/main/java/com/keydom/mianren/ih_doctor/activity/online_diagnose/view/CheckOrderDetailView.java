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
     * 获取检验单详情成功
     *
     * @param bean 检验单对象
     */
    void getCheckOutDetailSuccess(CheckItemListBean bean);

    /**
     * 获取检验单详情失败
     *
     * @param errMsg 失败提示信息
     */
    void getCheckOutDetailFailed(String errMsg);


    /**
     * 获取检查单详情成功
     *
     * @param bean 检查单对象
     */
    void getInspactDetailSuccess(CheckItemListBean bean);

    /**
     * 获取检查单详情失败
     *
     * @param errMsg 失败提示信息
     */
    void getInspactDetailFailed(String errMsg);

    /**
     * 获取检查、检验单对象
     *
     * @return
     */
    CheckItemListBean getCheckOutOrder();

    /**
     * 获取问诊订单对象
     *
     * @return
     */
    InquiryBean getInqueryOrder();

    /**

     * 获取订单类型
     *
     * @return
     */
    int getType();

}