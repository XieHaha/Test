package com.keydom.ih_patient.activity.reserve_painless_delivery.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.PainlessDeliveryBean;
import com.keydom.ih_patient.constant.TypeEnum;

import java.util.List;

/**
 * @date 20/3/4 16:59
 * @des 无痛分娩预约列表
 */
public interface PainlessDeliveryListView extends BaseView {
    void requestSuccess(List<PainlessDeliveryBean> data, TypeEnum typeEnum);

    void requestFailed(String msg);

    void cancelSuccess(int position);

    void cancelFailed(String msg);
}
