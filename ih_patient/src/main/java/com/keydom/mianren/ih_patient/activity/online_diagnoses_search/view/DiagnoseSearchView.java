package com.keydom.mianren.ih_patient.activity.online_diagnoses_search.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.RecommendDocAndNurBean;
import com.keydom.mianren.ih_patient.constant.TypeEnum;

import java.util.List;

/**
 * 问诊搜索view
 */
public interface DiagnoseSearchView extends BaseView {
    /**
     * 查询科室和医生
     */
    void getSearchSuccess(List<RecommendDocAndNurBean> dataList, TypeEnum type);

    /**
     * 查询失败
     */
    void getSearchFailed(String Msg);

}
