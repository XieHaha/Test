package com.keydom.ih_patient.activity.reserve_amniocentesis.view;

import com.keydom.ih_common.base.BaseView;

/**
 * @date 20/3/9 16:09
 * @des 羊水穿刺预约评估
 */
public interface AmniocentesisEvaluateView extends BaseView {

    /**
     * 胎数
     */
    void onFetusSelect(int index);

    /**
     * HIV抗体
     */
    void onHivSelect(int index);

    /**
     * rh血型
     */
    void onBloodSelect(int index);

    /**
     * 羊穿原因超声异常
     */
    void onUltrasoundSelect(int index);

    /**
     * 高血压
     */
    void onHypertensionSelect(int index);

    /**
     * 糖尿病
     */
    void onDiabetesSelect(int index);

}
