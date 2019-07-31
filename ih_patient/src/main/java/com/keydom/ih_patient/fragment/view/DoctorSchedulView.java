package com.keydom.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.DoctorSchedulingBean;

import java.util.List;

/**
 * 医生排班view
 */
public interface DoctorSchedulView extends BaseView {
    /**
     * 获取医生排班列表
     */
    void getDoctorSchedulingListSuccess(List<DoctorSchedulingBean> dataList);

    /**
     * 获取失败
     */
    void getDoctorSchedulingListFailde(String errMsg);
}
