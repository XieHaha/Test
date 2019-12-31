package com.keydom.ih_patient.activity.member.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.PayRecordBean;
import com.keydom.ih_patient.constant.TypeEnum;

import java.util.List;

public interface ChargeMemberRecordView extends BaseView {

    void paymentListCallBack(List<PayRecordBean> list, TypeEnum typeEnum);
}
