package com.keydom.mianren.ih_patient.activity.order_doctor_register.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_common.bean.DoctorInfo;

import java.util.List;

/**
 * 查询预约view
 */
public interface RegisterSearchView extends BaseView {
    /**
     * 查询科室和医生
     */
    void getSearchDocAndDepSuccess(List<Object> dataList);

    /**
     * 查询失败
     */
    void getSearchDocAndDepFailed(String Msg);

    /**
     * 查询医生
     */
    void getSearchDoctorSuccess(List<DoctorInfo> dataList);

    /**
     * 查询失败
     */
    void getSearchDoctorFailed(String Msg);
}
