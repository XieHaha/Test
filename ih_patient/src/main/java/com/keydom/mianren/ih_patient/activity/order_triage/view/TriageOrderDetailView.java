package com.keydom.mianren.ih_patient.activity.order_triage.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_common.bean.DiagnoseOrderDetailBean;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface TriageOrderDetailView extends BaseView {

    /**
     * 获取问诊详情
     */
    void getInquisitionDetailSuccess(DiagnoseOrderDetailBean bean);

    void getInquisitionDetailFailed(String msg);

}