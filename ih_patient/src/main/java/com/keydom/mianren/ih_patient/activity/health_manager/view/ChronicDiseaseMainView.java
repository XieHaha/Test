package com.keydom.mianren.ih_patient.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.HealthDataBean;

import java.util.Calendar;
import java.util.Map;

/**
 * @author 顿顿
 * @date 20/3/4 10:55
 * @des 慢病管理
 */
public interface ChronicDiseaseMainView extends BaseView {

    Calendar getCalendar();

    String getCurSelectDate();

    int getChronicDiseaseType();

    HealthDataBean getHealthDataBean();

    String getPatientId();

    void setNewDate(int value);

    void requestHealthDataSuccess(HealthDataBean bean);

    Map<String, Object> getUpdateHealthDataParams(HealthDataBean bean);
}
