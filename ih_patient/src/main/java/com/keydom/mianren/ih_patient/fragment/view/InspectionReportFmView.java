package com.keydom.mianren.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.InspectionRecordBean;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 检验检查view
 */
public interface InspectionReportFmView extends BaseView {

    /**
     * 获取列表数据成功
     */
    void getDataListSuccess(List<InspectionRecordBean> data);

    /**
     * 获取失败
     */
    void getDataListFailed(int errCode, String errMsg);

    /**
     * 列表参数
     */
    Map<String, Object> getParams();

    void setStartDate(Date date);

    void setEndDate(Date date);
}
