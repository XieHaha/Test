package com.keydom.mianren.ih_patient.activity.child_health.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.ChildHealthRootBean;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;

/**
 * @date 20/2/27 11:38
 * @des 儿童保健首页view
 */
public interface ChildHealthView extends BaseView {
    /**
     * 儿童保健首页数据
     */
    void requestSuccess(ChildHealthRootBean data);

    String getEleCardNumber();

    MedicalCardInfo getMedicalCardInfo();

    void transTitleBar(boolean direction, float scale);

    void finishPage();
}
