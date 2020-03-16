package com.keydom.mianren.ih_doctor.m_interface;

import android.view.View;

import com.keydom.mianren.ih_doctor.bean.PrescriptionModelBean;

import java.util.List;

public interface OnModelAndCaseDialogListener {
    void dialogClick(View v, String modelType, String modelName, List<PrescriptionModelBean> prescriptionModelBeanList);
}
