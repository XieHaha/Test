package com.keydom.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.VIPCardInfoResponse;

public interface TabMemberView extends BaseView {

    void getMyVipCardSuccess(VIPCardInfoResponse data);

    VIPCardInfoResponse getVipCardInfo();
}
