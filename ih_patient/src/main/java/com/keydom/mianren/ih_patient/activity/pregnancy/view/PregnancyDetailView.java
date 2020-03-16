package com.keydom.mianren.ih_patient.activity.pregnancy.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.CheckProjectsItem;
import com.keydom.mianren.ih_patient.bean.PregnancyOrderTime;

import java.util.Date;
import java.util.List;

public interface PregnancyDetailView extends BaseView {

    void setOrderDate(Date date);

    String getSelectedDate();


    void getCheckProjectsSuccess(List<CheckProjectsItem> data);

    void getCheckProjectsFailed(String msg);


    void getCheckProjectsTimesSuccess(List<PregnancyOrderTime> data);

    void getCheckProjectsTimesFailed(String msg);

    List<CheckProjectsItem> getCheckProjects();

    boolean isOrderChecks();

    void setChecks(boolean isOrderChecks);

    boolean isOrderDiagnose();

    void setOrderDiagnose(boolean isOrderDiagnose);

    void commitPregnancySuccess(Object data);

    String getRecordID();

    int getAppointType();

    long getPrenatalProjectId();

    String getTimeInterval();
}