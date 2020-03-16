package com.keydom.mianren.ih_patient.activity.payment_records.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.PayRecordBean;
import com.keydom.mianren.ih_patient.constant.TypeEnum;

import java.util.List;

/**
 * 已缴费view
 */
public interface PaiedRecordView extends BaseView {
    void paymentListCallBack(List<PayRecordBean> list, TypeEnum typeEnum);
}
