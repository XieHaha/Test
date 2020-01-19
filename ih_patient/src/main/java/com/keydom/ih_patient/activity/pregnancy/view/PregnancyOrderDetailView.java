package com.keydom.ih_patient.activity.pregnancy.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.PregnancyOrderDetailItem;

import java.util.List;

public interface PregnancyOrderDetailView extends BaseView {

    void getDetailProductInspectionSuccess(List<PregnancyOrderDetailItem> data);

    void getDetailProductInspectionFailed(String msg);

}
