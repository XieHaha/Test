package com.keydom.mianren.ih_doctor.activity.patient_manage.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.WarrantDoctorBean;

import java.util.List;

public interface WarrantListView extends BaseView {

     void getWarrantListSuccess(List<WarrantDoctorBean> dataList);

     void getWarrantListFailed(String errMsg);
}
