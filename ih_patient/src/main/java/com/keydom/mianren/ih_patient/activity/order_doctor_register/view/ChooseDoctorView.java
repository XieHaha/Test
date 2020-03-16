package com.keydom.mianren.ih_patient.activity.order_doctor_register.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.DateInfo;
import com.keydom.mianren.ih_patient.bean.DepartmentSchedulingBean;
import com.keydom.mianren.ih_patient.bean.HospitaldepartmentsInfo;

import java.util.List;

/**
 * 选择医生view
 */
public interface ChooseDoctorView extends BaseView {

    /**
     * 获取排班日期
     */
    void getDateListSuccess(List<DateInfo> dateInfoList);

    /**
     * 获取日期失败
     */
    void getDateListFailed(String errMsg);

    /**
     * 获取科室下排班医生列表
     */
    void getDoctorListSuccess(List<DepartmentSchedulingBean> dataList);

    /**
     * 获取医生失败
     */
    void getDoctorListFailed(String errMsg);

    /**
     * 获取已选择科室id
     */
    long getSelectedDepartmentId();

    /**
     * 获取科室成功
     */
    void getDepartmentSuceess( List<HospitaldepartmentsInfo> dataList);

    /**
     * 获取科室失败
     */
    void getDepartmentFailed(String errMsg);

    /**
     * 是否为医生选择模式
     */
    boolean isSelect();

    void defineSelect();
}
