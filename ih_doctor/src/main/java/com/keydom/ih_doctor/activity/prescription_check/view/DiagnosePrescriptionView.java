package com.keydom.ih_doctor.activity.prescription_check.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.DiagnoseHandleBean;
import com.keydom.ih_doctor.bean.DoctorPrescriptionDetailBean;
import com.keydom.ih_doctor.bean.DrugBean;
import com.keydom.ih_doctor.bean.PrescriptionMessageBean;
import com.keydom.ih_doctor.bean.PrescriptionModelBean;
import com.keydom.ih_doctor.bean.PrescriptionTemplateBean;

import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface DiagnosePrescriptionView extends BaseView {

    /**
     * 添加普通处方
     */
    void addCommonPrescription();

    /**
     * 添加儿童处方
     */
    void addPaediatricsPrescription();

    /**
     * 保存处方模版
     *
     * @param isModel 是否存为模版
     * @param value   模版名称
     */
    void savePrescriptionModel(boolean isModel, String value);

    /**
     * 保存病历模版
     *
     * @param isModel 是否存为模版
     */
    void saveCaseModel(boolean isModel);

    /**
     * 是否存为模版（如果有存为模版的，提交需要弹窗）
     *
     * @return
     */
    boolean isSaveModel();

    /**
     * 获取提交参数
     *
     * @return 请求参数
     */
    Map<String, Object> getSaveMap();

    /**
     * 获取选择的药品列表
     *
     * @return 药品列表
     */
    List<List<DrugBean>> getSelectList();

    /**
     * 获取处方详情成功
     *
     * @param bean 处方详情数据
     */
    void getPrescriptionDetailSuccess(DoctorPrescriptionDetailBean bean);

    /**
     * 获取处方详情失败
     *
     * @param errMsg 失败信息
     */
    void getPrescriptionDetailFailed(String errMsg);

    /**
     * 获取详情请求参数
     *
     * @return 请求参数
     */
    Map<String, Object> getDetailMap();

    /**
     * 提交成功
     *
     * @param bean 提交成功信息
     */
    void saveSuccess(PrescriptionMessageBean bean);

    /**
     * 提交失败
     *
     * @param errMsg 失败信息
     */
    void saveFailed(String errMsg);


    /**
     * 获取提交处置建议请求参数
     *
     * @return 请求参数
     */
    Map<String, Object> getHandleMap();

    /**
     * 处置建议提交成功
     *
     * @param msg 成功消息数据
     */
    void handleSuccess(DiagnoseHandleBean msg);

    /**
     * 处置建议提交失败
     *
     * @param errMsg 失败信息
     */
    void handleFailed(String errMsg);

    /**
     * 检查是否可以提交
     *
     * @return true 可以提交，false 不可以提交
     */
    boolean checkSubmit();


    boolean checkPrescription();

    void removeDrug(int position,int childPosition);

    void removePrescription(int position);

    boolean isHavePrescription();

    void creatPrescription();

    List<PrescriptionTemplateBean> getTemplateList();

    void updateTemplateList(List<PrescriptionModelBean> prescriptionModelBeanList);
}