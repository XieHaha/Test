package com.keydom.mianren.ih_patient.activity.payment_records.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;

import java.util.List;

/**
 * 支付记录view
 * @author 顿顿
 */
public interface PaymentRecordView extends BaseView {

    void getMangerUserList(List<ManagerUserBean> data);
}
