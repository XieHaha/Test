package com.keydom.ih_doctor.activity.prescription_check.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.PrescriptionDetailBean;

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
    /**
     * 获取处方详情成功
     *
     * @param bean 处方详情数据
     */
    void getDetailSuccess(PrescriptionDetailBean bean);

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
    Map<String, Object> getAuditMap();

    /**
     * 审核通过
     */
    void auditPass(String signature, String jobId);

    /**
     * 审核不通过
     *
     * @param auditOpinion 审核意见
     */
    void auditReturn(String auditOpinion, String signature, String jobID);

}