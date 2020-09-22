package com.keydom.mianren.ih_patient.activity.card_operate.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.ElectronicCardRootBean;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;

/**
 * 卡view
 *
 * @author 顿顿
 */
public interface ElectronicCardView extends BaseView {

    void queryHealthCardSuccess(ElectronicCardRootBean bean);

    void queryHealthCardFailed(String msg);

    MedicalCardInfo getMineCardInfo();
}
