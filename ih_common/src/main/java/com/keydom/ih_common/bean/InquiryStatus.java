package com.keydom.ih_common.bean;

/**
 * 问诊状态<br>
 * 0未支付 1 待接收 2问诊中 3审核不通过 4 待转诊确认 5医生发起结束问诊 6 等待医生开处置建议或者处方 7换诊 8 待评价 9完成 -1已取消
 */
public class InquiryStatus {
    /**
     * 未支付
     */
    public static final int INQUIRY_UNPAID = 0;
    /**
     * 待接诊
     */
    public static final int INQUIRY_WAIT = 1;
    /**
     * 问诊中
     */
    public static final int INQUIRY_ING = 2;


    /**
     * 处方审核失败
     */
    public static final int AUDIT_FAILE = 3;


    /**
     * 转诊待确认
     */
    public static final int INQUIRY_WAIT_REFERRAL = 4;
    /**
     * 医生发起结束问诊
     */
    public static final int INQUIRY_APPLY_END = 5;
    /**
     * 等待医生开处置建议或者处方
     */
    public static final int INQUIRY_PRESCRIBE = 6;
    /**
     * 换诊
     */
    public static final int INQUIRY_REFERRAL_DOCTOR = 7;

    /**
     * 待评价
     */
    public static final int INQUIRY_NOT_EVALUATED = 8;
    /**
     * 已完成
     */
    public static final int INQUIRY_COMPLETE = 9;
    /**
     * 分诊中
     */
    public static final int INQUIRY_TRIAGE_DOING = 11;
    /**
     * 取消
     */
    public static final int INQUIRY_CANCELLED = -1;
}
