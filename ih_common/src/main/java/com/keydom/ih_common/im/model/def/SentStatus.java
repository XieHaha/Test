package com.keydom.ih_common.im.model.def;

import android.support.annotation.IntDef;

import com.keydom.ih_common.im.model.ImMessageConstant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 消息发送状态
 */
@IntDef({ImMessageConstant.SENDING, ImMessageConstant.FAILED, ImMessageConstant.FINISH})
@Retention(RetentionPolicy.SOURCE)
public @interface SentStatus {
}