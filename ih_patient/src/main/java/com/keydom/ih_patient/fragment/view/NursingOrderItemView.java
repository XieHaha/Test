package com.keydom.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.NursingOrderBean;
import com.keydom.ih_patient.bean.NursingOrderDetailBean;
import com.keydom.ih_patient.constant.TypeEnum;

import java.util.List;

/**
 * created date: 2018/12/18 on 15:07
 * des:护理订单view
 * author: HJW HP
 */
public interface NursingOrderItemView extends BaseView {

    /**
     * 获取数据成功
     */
    void getDataSuccess(List<NursingOrderBean> data, TypeEnum typeEnum);

    /**
     * 支付成功
     */
    void paySuccess();

    void getBasicData(NursingOrderDetailBean data);
}
