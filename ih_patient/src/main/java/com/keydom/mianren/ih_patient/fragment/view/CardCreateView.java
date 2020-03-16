package com.keydom.mianren.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;

/**
 * 办卡view
 */
public interface CardCreateView extends BaseView {


    void isApplyElectronicCardSuccess(String data);

    void isApplyElectronicCardFailed(String msg);
}
