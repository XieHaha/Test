package com.keydom.ih_common.im.model;

public class ImMessageConstant {

    /**
     * 消息发送中状态
     */
    public static final int SENDING = 1;
    /**
     * 消息发送失败状态
     */
    public static final int FAILED = 2;
    /**
     * 消息发送成功状态
     */
    public static final int FINISH = 3;


    /**
     * 用户类型（医生）
     */
    public static final String DOCTOR = "doctor";
    /**
     * 用户类型（患者）
     */
    public static final String PATIENT = "patient";

    /**
     * 音频通话
     */
    public static final String AVCHAT_AUDIO = "avChat_audio";
    /**
     * 视频通话
     */
    public static final String AVCHAT_VIDEO = "avChat_video";


    /*****************************音视频通话状态*****************************/

    /**
     * 正常挂断
     */
    public static final int AVCHAT_HANGUP = 0;
    /**
     * 自己取消
     */
    public static final int AVCHAT_CANCEL = 1;
    /**
     * 自己拒绝
     */
    public static final int AVCHAT_REJECT = 2;
    /**
     * 未接听
     */
    public static final int AVCHAT_NO_RESPONSE = 3;
    /**
     * 对方已取消
     */
    public static final int AVCHAT_REMOTE_CANCEL = 4;
    /**
     * 对方已拒绝
     */
    public static final int AVCHAT_REMOTE_REJECT = 5;
    /**
     * 对方未接听
     */
    public static final int AVCHAT_REMOTE_HANGUP = 6;
    /**
     * 对方忙
     */
    public static final int AVCHAT_BUSY_LINE = 7;

    /*******************************音视频呼叫类型**********************************/

    /**
     * 呼出音视频
     */
    public static final int ACTION_OUTGOING_CALL = 1;
    /**
     * 接入音视频
     */
    public static final int ACTION_INCOMING_CALL = 2;
    /**
     * 重拨音视频
     */
    public static final int ACTION_RESUME_CALL = 3;

}
