package com.keydom.mianren.ih_patient.activity.member.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.RenewalRecordItem;
import com.keydom.mianren.ih_patient.constant.TypeEnum;

import java.util.List;

public interface ChargeMemberRecordView extends BaseView {

    void paymentListCallBack(List<RenewalRecordItem> list, TypeEnum typeEnum);
}
