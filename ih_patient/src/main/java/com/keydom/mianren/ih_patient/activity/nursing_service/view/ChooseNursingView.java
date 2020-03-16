package com.keydom.mianren.ih_patient.activity.nursing_service.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.ChooseNursingBean;

import java.util.List;

/**
 * 选择护理view
 */
public interface ChooseNursingView extends BaseView {
    void getTypeSuccess(List<ChooseNursingBean> dataList);
    void getTypeFailed(String errMsg);
}
