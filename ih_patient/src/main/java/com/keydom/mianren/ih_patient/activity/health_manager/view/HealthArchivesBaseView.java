package com.keydom.mianren.ih_patient.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;

import java.util.Date;
import java.util.List;

/**
 * @author 顿顿
 * @date 20/3/4 10:55
 * @des 健康档案-基本信息
 */
public interface HealthArchivesBaseView extends BaseView {
    List<String> getJobTypes();

    List<String> getBirthStatus();

    void setSexSelect(int type);

    void setMarrySelect(int type);

    String getNation();

    void setNation(String nation);

    void setJobType(int jobType);

    void setBirthStatus(int position);

    void onBirthDaySelect(Date date);
}
