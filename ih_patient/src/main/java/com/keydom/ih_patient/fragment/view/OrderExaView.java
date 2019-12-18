package com.keydom.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.ExaminationInfo;
import com.keydom.ih_patient.constant.TypeEnum;

import java.util.List;

/**
 * 检查预约view
 */
public interface OrderExaView extends BaseView {
    /**
     * 获取列表数据成功
     */
    void fillExaminationList(List<ExaminationInfo> dataList, TypeEnum typeEnum);

    /**
     * 获取失败
     */
    void fillExaminationListFailed(String errMsg);
}
