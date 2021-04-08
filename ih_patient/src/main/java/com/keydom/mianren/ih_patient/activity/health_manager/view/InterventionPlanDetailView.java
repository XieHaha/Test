package com.keydom.mianren.ih_patient.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.InterventionPlanBean;

/**
 * @author 顿顿
 * @date 20/3/4 10:55
 * @des 干预方案
 */
public interface InterventionPlanDetailView extends BaseView {
    void requestInterventionPlanDetailSuccess(InterventionPlanBean planBean);
}
