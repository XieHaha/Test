package com.keydom.mianren.ih_patient.activity.pregnancy.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.PregnancyOrderBean;

public interface PregnancyOrderDetailView extends BaseView {

    void getDetailProductInspectionSuccess(PregnancyOrderBean data);

    void getDetailProductInspectionFailed(String msg);

    void requestDoctorSuccess(String name);

}
