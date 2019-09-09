package com.keydom.ih_patient.activity.nursing_service.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.HospitaldepartmentsInfo;
import com.keydom.ih_patient.bean.NursingProjectInfo;

import java.util.List;
import java.util.Map;

/**
 * created date: 2018/12/24 on 19:15
 * des:护理订单填写view
 */
public interface NursingOrderFillInView extends BaseView {
    /**
     * 护理次数增加
     */
    void numAdd();

    /**
     * 护理次数减少
     */
    void numReduce();

    /**
     * 时间选择
     */
    void timeChoose(String date, String start, String end);

    /**
     * 科室选择
     */
    void getDepartmentData(List<HospitaldepartmentsInfo> data);

    /**
     * 获取医院id
     */
    long getHospitalAreaId();

    /**
     * 获取已选护理项目
     */
    List<NursingProjectInfo>  getSelectProjectList();

    /**
     * 上传图片成功
     */
    void uploadImgSuccess(String data);

    /**
     * 上传图片失败
     */
    void uploadImgFailed(String msg);

    /**
     * 获取提交map
     */
    Map<String,Object> getCommitMap();

    /**
     * 判断是否有项目可选
     */
    boolean isHaveProfessionalProject();

    /**
     * 判断是否更换
     */
    boolean isChange();

    /**
     * 获取更换map
     */
    Map<String,Object> getChangeMap();

    /**
     * 获取最后一次点击项目
     */
    boolean getLastItemClick(int position);

    /**
     * 获取图片size
     */
    int getImgSize();

    String getPicUrl(int position);

    List<String> getPicList();

    void setIsNeedSaveEdit(boolean isNeedSaveEdit);

}
