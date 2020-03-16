package com.keydom.mianren.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.VIPDetailBean;

public interface VIPMemberDetailView extends BaseView {


    void getMyVipCardSuccess(VIPDetailBean data);
    void getMyVipCardFail(String msg);
}
