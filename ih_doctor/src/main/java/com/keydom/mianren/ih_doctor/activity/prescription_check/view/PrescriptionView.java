package com.keydom.mianren.ih_doctor.activity.prescription_check.view;

import android.widget.ScrollView;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.DoctorPrescriptionDetailBean;

import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface PrescriptionView extends BaseView {
    ScrollView getScrollView();
    /**
     * 获取处方详情成功
     *
     * @param bean 处方详情数据
     */
    void getDetailSuccess(DoctorPrescriptionDetailBean bean);

    /**
     * 获取处方详情失败
     *
     * @param msg 失败信息
     */
    void getDetailFailed(String msg);

    /**
     * 药师审核成功
     *
     * @param successMsg 成功信息
     */
    void auditSuccess(String successMsg);

    /**
     * 药师审核失败
     *
     * @param errMsg 失败信息
     */
    void auditFailed(String errMsg);

    /**
     * 获取处方详情请求参数
     *
     * @return 请求参数
     */
    Map<String, Object> getDetailMap();

    /**
     * 获取药师审核请求参数
     *
     * @return 请求参数
     */
    Map<String, Object> getAuditMap(String data);

    /**
     * 审核通过
     */
    void auditPass();
    /**
     * 审核不通过
     *
     * @param auditOpinion 审核意见
     */
    void auditReturn(String auditOpinion);


    void handleImageAndPdf(String password);

}