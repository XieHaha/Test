package com.keydom.mianren.ih_doctor.activity.medical_record_templet.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.MedicalRecordTempletBean;
import com.keydom.mianren.ih_doctor.bean.MedicalTempletDetailBean;

import java.util.List;

/**
 * created date: 2019/1/8 on 15:43
 * des:
 * author: HJW HP
 */
public interface MedicalTempletDetailView extends BaseView {
    /**
     * 获取病历详情
     *
     * @param list 病历模版列表数据
     * @param bean
     */
    void templateDetailrequestCallBack(List<MedicalTempletDetailBean> list, MedicalRecordTempletBean bean);
}
