package com.keydom.mianren.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.SubscribeExaminationBean;
import com.keydom.mianren.ih_patient.constant.TypeEnum;

import java.util.List;

/**
 * created date: 2018/12/12 on 17:24
 * des:体检预约view
 */
public interface SubscribeExaminationOrderView extends BaseView {
    /**
     * 获取数据成功回调
     */
    void getDataSuccess(List<SubscribeExaminationBean> data, TypeEnum typeEnum);

    /**
     * 获取数据失败回调
     */
    void getDataFailed(String msg);

    /**
     * 支付成功
     */
    void paySuccess();
}
