package com.keydom.ih_patient.activity.nursing_service.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.DiagnoseIndexBean;
import com.keydom.ih_patient.bean.DiagnosesAndNurDepart;
import com.keydom.ih_patient.bean.HospitalAreaInfo;
import com.keydom.ih_patient.bean.RecommendDocAndNurBean;

import java.util.List;
import java.util.Map;

/**
 * 护理服务在线问诊view
 */
public interface NursingOnlineConsultView extends BaseView {

    /**
     * 获取首页数据成功
     */
    void getHomeDataSuccess(DiagnoseIndexBean data);

    /**
     * 获取首页数据失败
     */
    void getHomeDataFailed(String errMsg);

    /**
     * 获取推荐成功
     */
    void getRecommendSuccess(List<RecommendDocAndNurBean> recommendList);

    /**
     * 获取推荐失败
     */
    void getRecommendFailed(String errMsg);

    /**
     * 获取科室成功
     */
    void getDepartListSuccess(List<DiagnosesAndNurDepart> data);

    /**
     * 获取科室失败
     */
    void getDepartListFailed(String errMsg);

    /**
     * 展示选择医院弹框
     */
    void showHospitalPopupWindow();

    /**
     * 获取院区列表成功
     * @param data
     */
    void getAreaList(List<HospitalAreaInfo> data);

    /**
     * 获取院区失败
     * @param msg
     */
    void getAreaListFailed(String msg);

    Map<String,Object> getQueryDeptMap();

    int getUnpaytag();
}
