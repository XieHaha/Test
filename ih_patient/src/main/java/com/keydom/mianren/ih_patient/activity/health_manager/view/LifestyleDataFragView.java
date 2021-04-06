package com.keydom.mianren.ih_patient.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.EatItemBean;
import com.keydom.mianren.ih_patient.bean.SportsItemBean;

import java.util.List;

/**
 * 生活方式数据
 *
 * @author 顿顿
 */
public interface LifestyleDataFragView extends BaseView {
    int getProjectId();

    String getPatientId();

    int getLifestyleType();

    void requestFoodBankListSuccess(List<EatItemBean> data);

    void requestExerciseBankListSuccess(List<SportsItemBean> data);
}
