package com.keydom.ih_patient.activity.my_medical_card.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.MedicalCardInfo;

/**
 * 就诊卡详情view
 */
public interface MedicalCardDetailView extends BaseView {

    /**
     * 解绑成功
     */
    void removeBindSuccess();

    /**
     * 解绑失败
     */
    void removeBindFailed(String msg);

    /**
     * 获取就诊卡实体
     */
    MedicalCardInfo getCardObject();
}
