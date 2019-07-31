package com.keydom.ih_patient.activity.online_diagnoses_search.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.RecommendDocAndNurBean;

import java.util.List;

/**
 * 问诊搜索view
 */
public interface DiagnoseSearchView extends BaseView {
    /**
     * 查询科室和医生
     */
    void getSearchSuccess(List<RecommendDocAndNurBean> dataList);

    /**
     * 查询失败
     */
    void getSearchFailed(String Msg);

}
