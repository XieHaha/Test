package com.keydom.ih_patient.activity.order_doctor_register.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.HospitalAreaInfo;
import com.keydom.ih_patient.bean.HospitaldepartmentsInfo;

import java.util.List;

/**
 * 预约挂号订单view
 */
public interface OrderDoctorRegisterView extends BaseView {

    /**
     * 上传数据成功
     */
    void upLoadData(List<Object> dataList,List<HospitaldepartmentsInfo> departmentList);

    /**
     * 获取地区列表
     */
    void getAreaList(List<HospitalAreaInfo> dataList);

    /**
     * 获取地区失败
     */
    void getAreaListFailed(String msg);

    /**
     * 展示医院选择弹框
     */
    void showHospitalAreaPopupWindow();

    /**
     * 获取科室列表
     */
    List<HospitaldepartmentsInfo> getDepartmentList();

    /**
     * 获取科室失败
     */
    void getDepartmentFailed(String msg);
}
