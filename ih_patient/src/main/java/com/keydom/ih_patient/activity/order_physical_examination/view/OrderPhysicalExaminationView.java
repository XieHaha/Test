package com.keydom.ih_patient.activity.order_physical_examination.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.PhysicalExaInfo;

import java.util.List;

/**
 * 体检订单view
 */
public interface OrderPhysicalExaminationView extends BaseView {

    /**
     * 获取全部体检订单列表
     */
    void fillPhysicalExaDataList(List<PhysicalExaInfo> dataList);

    /**
     * 获取失败
     */
    void getPhysicalExaDataFailed(String errMsg);
}
