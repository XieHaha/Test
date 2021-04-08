package com.keydom.mianren.ih_patient.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.HealthSummaryBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 顿顿
 * @date 20/3/4 10:55
 * @des 健康总结
 */
public interface HealthSummaryView extends BaseView {
    Map<String, Object> getParams();

    ArrayList<HealthSummaryBean> getHealthSummaryBeans();

    void requestHealthSummaryListSuccess(List<HealthSummaryBean> data);
}
