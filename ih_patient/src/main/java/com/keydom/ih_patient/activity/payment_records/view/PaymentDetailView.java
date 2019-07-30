package com.keydom.ih_patient.activity.payment_records.view;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.PayRecordDetailBean;

import java.util.List;

/**
 * created date: 2019/1/15 on 15:29
 * des:缴费详情view
 */
public interface PaymentDetailView extends BaseView {
    /**
     * 获取数据回调
     */
    void getDetailCallBack(PayRecordDetailBean detailBean, List<MultiItemEntity> list);
}
