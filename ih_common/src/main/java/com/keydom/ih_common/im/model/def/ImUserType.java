package com.keydom.ih_common.im.model.def;

import android.support.annotation.StringDef;

import com.keydom.ih_common.im.model.ImMessageConstant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 消息发送者类型
 */
@StringDef({ImMessageConstant.DOCTOR, ImMessageConstant.PATIENT})
@Retention(RetentionPolicy.SOURCE)
public @interface ImUserType {
}
