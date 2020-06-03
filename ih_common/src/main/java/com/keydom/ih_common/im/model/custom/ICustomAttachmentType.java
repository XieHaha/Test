package com.keydom.ih_common.im.model.custom;

/**
 * @author THINKPAD B
 */
public interface ICustomAttachmentType {
    /**
     * 问诊单消息类型
     */
    int INQUIRY = 10086;
    /**
     * 分诊单
     */
    int TRIAGE_ORDER = 10087;

    /**
     * 处方单消息
     */
    int CONSULTATION_RESULT = 10010;

    /**
     * 检验申请单消息
     */
    int INSPECTION = 90001;

    /**
     * 检查申请单消息
     */
    int EXAMINATION = 90002;

    /**
     * 自动换诊消息
     */
    int REFERRAL_DOCTOR = 95588;

    /**
     * 转诊申请单消息
     */
    int REFERRAL_APPLY = 95555;

    /**
     * 结束问诊消息
     */
    int END_INQUIRY = 95599;

    /**
     * 处置建议消息
     */
    int DISPOSAL_ADVICE = 12306;

    /**
     * 取药
     */
    int GET_DRUGS = 100101;

    /**
     * 收药
     */
    int RECEIVE_DRUGS = 100102;

    /**
     * 随访表
     */
    int USER_FOLLOW_UP = 100103;
}
