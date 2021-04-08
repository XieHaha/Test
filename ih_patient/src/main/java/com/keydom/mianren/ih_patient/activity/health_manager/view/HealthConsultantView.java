package com.keydom.mianren.ih_patient.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.HealthConsultantBean;

import java.util.List;

/**
 * @author 顿顿
 * @date 20/3/4 10:55
 * @des 健康管理咨询师
 */
public interface HealthConsultantView extends BaseView {
    void requestHealthDoctorListSuccess(List<HealthConsultantBean> data);
}
