package com.keydom.mianren.ih_patient.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.InterventionPlanBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 顿顿
 * @date 20/3/4 10:55
 * @des 干预方案
 */
public interface InterventionPlanView extends BaseView {
    Map<String, Object> getParams();

    void requestInterventionPlanListSuccess(List<InterventionPlanBean> data);
    void requestInterventionPlanListFailed(String error);

    ArrayList<InterventionPlanBean> getInterventionPlanBeans();

    void finishPage();
}
