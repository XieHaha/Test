package com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.HospitalAppointmentBean;
import com.keydom.mianren.ih_patient.constant.TypeEnum;

import java.util.List;

/**
 * 产科住院view
 */
public interface ObstetricView extends BaseView {

    /**
     * 获取记录成功
     */
    void getObstetricListSuccess(List<HospitalAppointmentBean> dataList, TypeEnum typeEnum);

    /**
     * 获取记录失败
     */
    void getObstetricListFailed(String errMsg);
}
