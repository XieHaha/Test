package com.keydom.ih_doctor.activity.online_diagnose.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.PrescriptionTempletBean;

import java.util.List;

public interface PrescriptionTempletView extends BaseView {
    /**
     * 获取模版数据成功
     *
     * @param dataList 模版列表数据
     */
    void getTempletListSuccess(List<PrescriptionTempletBean> dataList);

    /**
     * 模版获取失败
     *
     * @param errMsg 失败提示信息
     */
    void getTempletListFailed(String errMsg);

    /**
     * 选择科室后进行设置
     *
     * @param deptName 科室名称
     * @param type     科室代表的type
     */
    void setDept(String deptName, String type);

    /**
     * 获取请求type
     *
     * @return
     */
    String getType();
}
