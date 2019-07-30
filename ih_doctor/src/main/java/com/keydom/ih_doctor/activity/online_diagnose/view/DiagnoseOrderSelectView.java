package com.keydom.ih_doctor.activity.online_diagnose.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.InquiryBean;
import com.keydom.ih_doctor.constant.TypeEnum;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface DiagnoseOrderSelectView extends BaseView {


    /**
     * 获取问诊订单成功
     *
     * @param type
     * @param list 订单列表数据
     */
    void getOrderSuccess(TypeEnum type, List<InquiryBean> list);

    /**
     * 获取问诊订单失败
     *
     * @param errMsg 失败提示信息
     */
    void getOrderFailed(String errMsg);

}