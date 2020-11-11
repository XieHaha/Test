package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view;

import com.keydom.ih_common.base.BaseView;

import java.util.Date;

/**
 * @author 顿顿
 * @date 20/3/9 16:09
 * @des 羊水穿刺预约评估
 */
public interface AmniocentesisEvaluateView extends BaseView {

    /**
     * 胎数
     */
    void onFetusSelect(int index);

    int getFetusSelect();

    /**
     * HIV抗体
     */
    void onHivSelect(int index);

    String getHivSelect();

    /**
     * rh血型
     */
    void onBloodSelect(int index);

    String getBloodSelect();

    /**
     * 梅毒
     */
    void onSyphilisSelect(int index);

    String getSyphilisSelect();

    /**
     * 羊穿原因超声异常
     */
    void onUltrasoundSelect(int index);

    int getUltrasoundSelect();

    /**
     * 高血压
     */
    void onHypertensionSelect(int index);

    int getHypertensionSelect();

    /**
     * 糖尿病
     */
    void onDiabetesSelect(int index);

    int getDiabetesSelect();

    boolean isSelect();

    void onAmniocentesisEvaluateSuccess();

    void onUltrasoundDateSelect(Date date);

    String getUltrasoundDate();

    String getNTValue();

    String getHeadLengthValue();

}
