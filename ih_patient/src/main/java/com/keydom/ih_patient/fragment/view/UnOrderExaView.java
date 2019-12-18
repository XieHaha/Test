package com.keydom.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.ExaminationInfo;
import com.keydom.ih_patient.constant.TypeEnum;

import java.util.List;

/**
 * 检查记录view
 */
public interface UnOrderExaView extends BaseView {

    /**
     * 获取记录成功
     */
    void fillExaminationList(List<ExaminationInfo> dataList, TypeEnum typeEnum);

    /**
     * 获取记录失败
     */
    void fillExaminationListFailed(String errMsg);
}
