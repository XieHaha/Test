package com.keydom.ih_doctor.activity.medical_record_templet.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.MedicalRecordTempletBean;

import java.util.List;

/**
 * created date: 2019/1/8 on 14:42
 * des:
 * author: HJW HP
 */
public interface MedicalRecordTempletView extends BaseView {
    /**
     * 获取模版列表数据成功
     *
     * @param data 模版数据
     */
    void templateListRequestCallBack(List<MedicalRecordTempletBean> data);

    /**
     * 获取模版数据失败
     */
    void requestErrorCallBack();

    /**
     * 获取搜索关键字
     *
     * @return
     */
    String getSearchStr();

    /**
     * 设置科室
     *
     * @param dept
     */
    void setDept(String dept);

    /**
     * 设置模版类型
     *
     * @param type
     */
    void setType(int type);
}
