package com.keydom.mianren.ih_doctor.activity.online_triage.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;

import java.util.List;

/**
 * @date 20/3/21 13:53
 * @des 分诊列表view
 */
public interface TriageOrderListView extends BaseView {
    void requestSuccess(List<String> data, TypeEnum typeEnum);

    void requestFailed(String msg);
}
