package com.keydom.mianren.ih_patient.activity.card_operate.view;

import com.keydom.ih_common.base.BaseView;

import java.util.Map;

/**
 * 卡view
 *
 * @author 顿顿
 */
public interface ElectronicCardDetailView extends BaseView {

    Map<String,String> getParams();

    void queryHealthCardSuccess(String data);

    void queryHealthCardFailed(String msg);
}
