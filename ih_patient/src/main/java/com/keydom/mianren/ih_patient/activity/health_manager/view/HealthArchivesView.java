package com.keydom.mianren.ih_patient.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.HealthArchivesBean;

import java.util.Map;

/**
 * @author 顿顿
 * @date 20/3/4 10:55
 * @des 健康档案
 */
public interface HealthArchivesView extends BaseView {

    void setCurRelationPosition(int curRelationPosition);

    HealthArchivesBean getArchivesBean();

    Map<String, Object> getParams();

    String getPatientId();

    void savePatientInfoSuccess();
    void getPatientInfoSuccess(HealthArchivesBean data);
}
