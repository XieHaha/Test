package com.keydom.mianren.ih_patient.activity.reserve_examination.view;

import com.keydom.ih_common.base.BaseView;

import java.util.Date;

/**
 * @date 20/3/6 10:54
 * @des
 */
public interface ExaminationReserveView extends BaseView {
    void setSelect();

    boolean isSelect();

    long getCurrentUserId();

    /**
     * true、检验   false、检查
     */
    void setCategory(boolean category);

    void setVisitDate(Date date);
}
