package com.keydom.mianren.ih_patient.activity.order_examination.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;

import java.util.List;

/**
 * 检查订单view
 */
public interface OrderExaminationView extends BaseView {

    /**
     * 创建订单
     */
    void toDoOrder(long inspectProjectId,String applyNumber);


    /**
     * 展示就诊卡弹框
     */
    void showCardPopupWindow();


    /**
     * 获取所有就诊卡
     */
    void getAllCard(List<MedicalCardInfo> dataList);



}
