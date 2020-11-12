package com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_common.bean.DoctorInfo;
import com.keydom.mianren.ih_patient.bean.DepartmentInfo;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;

import java.util.List;
import java.util.Map;

/**
 * @author 顿顿
 * @date 20/3/11 17:00
 * @des
 */
public interface ReserveObstetricHospitalView extends BaseView {

    void requestDepartSuccess(List<DepartmentInfo> data);

    void requestDepartFailed(String msg);

    void requestDoctorSuccess(List<DoctorInfo> data,int type);

    void requestDoctorFailed(String msg);

    void reserveSuccess();

    Map<String, Object> getParams();

    boolean reserveAble();

    /**
     * 查询就诊卡列表接口回调
     */
    void getAllCardSuccess(List<MedicalCardInfo> dataList);

    /**
     * 查询就诊卡失败
     */
    void getAllCardFailed(String errMsg);
}
