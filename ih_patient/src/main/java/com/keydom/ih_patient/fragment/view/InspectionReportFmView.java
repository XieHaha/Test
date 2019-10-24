package com.keydom.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.constant.TypeEnum;

import java.util.List;

/**
 * 检验检查view
 */
public interface InspectionReportFmView extends BaseView {

    /**
     * 获取列表数据成功
     */
    void getDataListSuccess(List<Object> dataList, TypeEnum typeEnum);

    /**
     * 获取失败
     */
    void getDataListFailed(int errCode,String errMsg);
}
