package com.keydom.ih_common.im.config;

public class ImConstants {
    /**
     * 加号菜单选择图片回调
     */
    public static final int IMAGE_REQUEST = 22;
    /**
     * 加号菜单拍照回调
     */
    public static final int CAMERA_REQUEST = 23;

    /**
     * 隐式启动医生视频
     */
    public static final String IM_INTENT_ACTION_VIDEO_DOCTOR = "com.ih_common.intent.action.video.doctor";

    /**
     * 隐式启动用户视频
     */
    public static final String IM_INTENT_ACTION_VIDEO_USER = "com.ih_common.intent.action.video.user";

    /**
     * 会话对象
     */
    public static final String CALL_USER_TYPE = "userType";
    public static final String CALL_SESSION_ID = "sessionId";
    public static final String CALL_CALL_ACTION = "callAction";
    public static final String CALL_AVCHAT_DATA = "AVChat";
    public static final String CALL_AVCHAT_TYPE = "AVChatType";

    /**
     * 是否是聊天（非在线问诊）
     */
    public static final String CHATTING = "chatting";
    /**
     * 本地提醒，不发送至服务器
     */
    public static final String LOCAL_TIP = "localTip";
}
