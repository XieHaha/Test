package com.keydom.mianren.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.VIPCardInfoResponse;

public interface TabMemberView extends BaseView {

    void getMyVipCardSuccess(VIPCardInfoResponse data);

    void getMyVipCardFail(String msg);

    VIPCardInfoResponse getVipCardInfo();
}
