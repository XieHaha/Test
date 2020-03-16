package com.keydom.mianren.ih_patient.activity.online_diagnoses_order.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;

import java.util.List;

public interface TypePatientView extends BaseView {
    /**
     * 获取就诊人列表
     * @param data
     */
    void getMangerUserList(List<ManagerUserBean> data);
}
