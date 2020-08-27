package com.keydom.mianren.ih_patient.activity.pregnancy.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.CheckProjectRootBean;
import com.keydom.mianren.ih_patient.bean.CheckProjectSubBean;
import com.keydom.mianren.ih_patient.bean.DoctorScheduling;
import com.keydom.mianren.ih_patient.bean.PregnancyOrderTime;

import java.util.Date;
import java.util.List;

public interface PregnancyReserveView extends BaseView {

    void setOrderDate(Date date);

    String getSelectedDate();


    int getmPrenancyType();

    void getCheckProjectsSuccess(CheckProjectRootBean data);

    void getCheckProjectsFailed(String msg);


    void getCheckProjectsTimesSuccess(List<PregnancyOrderTime> data);

    void getCheckProjectsTimesFailed(String msg);

    void requestDoctorSchedulingSuccess(List<DoctorScheduling> data);
    void requestDoctorSchedulingFailed(String data);

    List<CheckProjectSubBean> getCheckProjects();

    List<CheckProjectSubBean> getSelectSubBeans();

    void commitPregnancySuccess(Object data);

    String getRecordID();

    String getTimeInterval();
}
