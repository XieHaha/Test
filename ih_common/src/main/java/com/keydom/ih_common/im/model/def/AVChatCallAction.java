package com.keydom.ih_common.im.model.def;

import android.support.annotation.IntDef;

import com.keydom.ih_common.im.model.ImMessageConstant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 音视频呼叫类型
 */
@IntDef({ImMessageConstant.ACTION_OUTGOING_CALL, ImMessageConstant.ACTION_INCOMING_CALL, ImMessageConstant.ACTION_RESUME_CALL})
@Retention(RetentionPolicy.SOURCE)
public @interface AVChatCallAction {
}
