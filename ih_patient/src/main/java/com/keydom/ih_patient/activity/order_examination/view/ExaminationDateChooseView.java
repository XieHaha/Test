package com.keydom.ih_patient.activity.order_examination.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.DateInfo;
import com.keydom.ih_patient.bean.ExaminationInfo;
import com.keydom.ih_patient.bean.HospitalAreaInfo;
import com.keydom.ih_patient.bean.SoruInfo;

import java.util.List;

/**
 * 检查预约view
 */
public interface ExaminationDateChooseView extends BaseView {

    /**
     * 展示医院选择弹框
     */
    void showHospitalAreaPopupWindow();

    /**
     * 获取地区列表
     */
    void getAreaList(List<HospitalAreaInfo> dataList);

    /**
     * 获取日期列表
     */
    void getDateList(List<DateInfo> dateList);

    /**
     * 获取日期失败
     */
    void getDateListFailed(String errMsg);

    /**
     * 获取选中项目id
     */
    long getSelectedProjectId();

    /**
     * 获取过去排班信息
     */
    void getSoruInfo(SoruInfo soruInfo);

    /**
     * 获取失败
     */
    void getSoruInfoFailed(String errMsg);

    /**
     * 完成订单回调
     */
    void completeOrder(ExaminationInfo data);

    /**
     * 完成订单失败
     */
    void completeOrderFailed(String errMsg);
}
