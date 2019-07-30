package com.keydom.ih_common.activity.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_common.bean.DiagnoseOrderDetailBean;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface DiagnoseOrderDetailView extends BaseView {

    long getOrderId();

    void getDetailSuccess(DiagnoseOrderDetailBean bean);

    void getDetailFailed(String errMsg);

}