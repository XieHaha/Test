package com.keydom.ih_patient.activity.pregnancy.view;

import com.keydom.ih_common.base.BaseView;

import java.util.Date;

public interface PregnancyDetailView extends BaseView {

    void setOrderDate(Date date);

    String getSelectedDate();
}
