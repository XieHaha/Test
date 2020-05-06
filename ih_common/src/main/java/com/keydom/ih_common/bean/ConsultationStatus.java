package com.keydom.ih_common.bean;

/**
 * @date 20/4/30 11:24
 * @des 会诊接收状态
 */
public class ConsultationStatus {

    /**
     * 发起者
     */
    public static final int CONSULTATION_NONE = -1;
    /**
     * 未接收
     */
    public static final int CONSULTATION_WAIT = 0;
    /**
     * 已接收
     */
    public static final int CONSULTATION_RECEIVED = 1;

}
