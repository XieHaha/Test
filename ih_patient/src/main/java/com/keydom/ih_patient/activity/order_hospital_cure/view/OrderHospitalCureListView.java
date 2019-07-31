package com.keydom.ih_patient.activity.order_hospital_cure.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.HospitalCureInfo;

import java.util.List;

/**
 * 入院列表view
 */
public interface OrderHospitalCureListView extends BaseView {

    /**
     * 查询全部入院记录
     */
    void fillHospitalCureList(List<HospitalCureInfo> dataList);

    /**
     * 查询失败
     */
    void fillHospitalCureListFailed(String errMsg);
}
