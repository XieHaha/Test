package com.keydom.ih_patient.activity.member.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.VIPDetailBean;

public interface ChargeMemberView extends BaseView {

    void paySuccess();

    double getSelectedPrice();

    void getMyVipCardSuccess(VIPDetailBean data);

    void getMyVipCardFail(String msg);
}
