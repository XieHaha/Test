package com.keydom.ih_common.constant;

/**
 * @Name：com.kentra.yxyz.constant
 * @Description：EventBus事件枚举类
 * @Author：song
 * @Date：18/11/2 下午1:49
 * 修改人：xusong
 * 修改时间：18/11/2 下午1:49
 */
public enum EventType {
    /**
     *
     */
    UPDATE,
    SEND,
    OFFLINE,
    FILE,
    DIAGNOSIS_FILE,
    /**
     * 发起会诊申请
     */
    APPLY_JOIN_CONSULTATION,
    /**
     * 收到会诊申请
     */
    NOTIFY_APPLY_JOIN_CONSULTATION,
    /**
     * 同意会诊申请
     */
    NOTIFY_AGREE_JOIN_CONSULTATION,
    /**
     * 拒绝会诊申请
     */
    NOTIFY_REJECT_JOIN_CONSULTATION,
    /**
     * 排班医生会话权限(医务端)
     */
    NOTIFY_DOCTOR_SPEAK_PERMISSION,
    /**
     * 排班医生会话权限(用户端)
     */
    NOTIFY_PATIENT_SPEAK_PERMISSION,
}
