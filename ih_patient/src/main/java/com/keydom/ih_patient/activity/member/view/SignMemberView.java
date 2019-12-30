package com.keydom.ih_patient.activity.member.view;

import com.keydom.ih_common.base.BaseView;

public interface SignMemberView extends BaseView {

    void showAliPaySelected();

    void showWechatPaySelected();

    String getName();

    String getID();

    int getPayType();

    void paySuccess();
}
