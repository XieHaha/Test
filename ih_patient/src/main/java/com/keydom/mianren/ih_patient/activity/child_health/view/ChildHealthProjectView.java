package com.keydom.mianren.ih_patient.activity.child_health.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.ChildHealthProjectBean;

import java.util.List;

/**
 * @author 顿顿
 * @date 20/2/27 11:38
 * @des 儿童保健项目列表
 */
public interface ChildHealthProjectView extends BaseView {
    void requestProjectSuccess(List<ChildHealthProjectBean> data);

    void requestProjectFailed(String msg);

}
