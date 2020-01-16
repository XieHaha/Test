package com.keydom.ih_patient.activity.card_operate.view;

import com.keydom.ih_common.base.BaseView;

/**
 * 卡view
 */
public interface CardOperateView extends BaseView {


    void getHealthCardStateFailed(String msg);

    void getHealthCardStateSuccess(Boolean data);

    boolean isBind();
}
