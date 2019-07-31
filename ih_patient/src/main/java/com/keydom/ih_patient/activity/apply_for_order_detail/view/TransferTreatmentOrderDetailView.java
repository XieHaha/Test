package com.keydom.ih_patient.activity.apply_for_order_detail.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.DiagnoseOrderDetailBean;

import java.util.Map;

/**
 * @Description：检查单详情view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface TransferTreatmentOrderDetailView extends BaseView {

    /**
     * 获取请求map
     */
    Map<String, Object> getQueryMap();

    /**
     * 获取数据成功
     */
    void getDetailSuccess(DiagnoseOrderDetailBean bean);

    /**
     * 获取数据失败
     */
    void getDetailFailed(String errMsg);
}