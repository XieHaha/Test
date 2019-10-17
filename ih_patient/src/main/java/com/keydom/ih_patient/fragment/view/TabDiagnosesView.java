package com.keydom.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.BannerBean;
import com.keydom.ih_patient.bean.DiagnoseIndexBean;
import com.keydom.ih_patient.bean.DiagnosesAndNurDepart;
import com.keydom.ih_patient.bean.HospitalAreaInfo;
import com.keydom.ih_patient.bean.RecommendDocAndNurBean;
import com.keydom.ih_patient.constant.TypeEnum;

import java.util.List;
import java.util.Map;

/**
 * 在线问诊view
 */
public interface TabDiagnosesView extends BaseView {
    /**
     * 获取首页数据成功
     */
    void getHomeDataSuccess(DiagnoseIndexBean data);

    /**
     * 获取主页失败
     */
    void getHomeDataFailed(String errMsg);

    /**
     * 获取记录成功
     */
    void getRecommendSuccess(List<RecommendDocAndNurBean> recommendList, TypeEnum type);

    /**
     * 获取记录失败
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
     * 展示医院选择弹框
     */
    void showHospitalPopupWindow();

    /**
     * 获取图片list成功
     */
    void  getPicListSuccess(List<BannerBean> dataList);

    /**
     * 获取图片失败
     */
    void getPicListFailed(String errMsg);

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
