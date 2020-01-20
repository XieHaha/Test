package com.keydom.ih_patient.activity.member.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.VIPCardInfoResponse;

public interface SignMemberView extends BaseView {


    String getName();

    String getID();

    boolean isCheckAgreement();

    void paySuccess();

    VIPCardInfoResponse getVipCardInfo();
}
