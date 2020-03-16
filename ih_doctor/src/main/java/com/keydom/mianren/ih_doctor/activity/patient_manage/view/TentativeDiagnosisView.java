package com.keydom.mianren.ih_doctor.activity.patient_manage.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.DeptDoctorBean;
import com.keydom.mianren.ih_doctor.bean.ImPatientInfo;
import com.keydom.mianren.ih_doctor.bean.WarrantDetailBean;

import java.util.List;
import java.util.Map;

public interface TentativeDiagnosisView extends BaseView {


    void inquiryScalerAdd();

    void inquiryScalerMinus();

    void showTimePikerDialog();

    void jurisdictionIsOpen(boolean b);

    List<ImPatientInfo> getSelectPatient();

    DeptDoctorBean getSelectDoctor();

    boolean submitCheck();

    Map<String, Object> getAuthorizeMap();

    void authSuccess(String msg);

    void authFailed(String errMsg);

    void getWarrantDetailSuccess(WarrantDetailBean warrantDetailBean);
    void getWarrantDetailFailed(String errMsg);

    int getType();
}
