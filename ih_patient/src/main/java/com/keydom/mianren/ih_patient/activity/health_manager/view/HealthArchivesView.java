package com.keydom.mianren.ih_patient.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.HealthArchivesBean;

import java.util.List;
import java.util.Map;

/**
 * @author 顿顿
 * @date 20/3/4 10:55
 * @des 健康档案
 */
public interface HealthArchivesView extends BaseView {

    void setCurSurgeryPosition(int curSurgeryPosition);

    void setCurRelationPosition(int curRelationPosition);

    HealthArchivesBean getArchivesBean();

    List<String> getDrinkDegreeData();

    List<String> getDrinkNumData();

    List<String> getSmokeDegreeData();

    List<String> getSmokeNumData();

    List<String> getDrinkOrSmokeYearData();

    void setSelectDrink(int type);

    void setSelectSmoke(int type);

    Map<String, Object> getParams();

    String getPatientId();

    void savePatientInfoSuccess();

    void getPatientInfoSuccess(HealthArchivesBean data);

    void setDrinkDegree(int position);

    void setDrinkNum(int position);

    void setDrinkYear(int position);

    void setSmokeDegree(int position);

    void setSmokeNum(int position);

    void setSmokeYear(int position);
}
