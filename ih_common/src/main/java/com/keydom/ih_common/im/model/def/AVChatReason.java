package com.keydom.ih_common.im.model.def;


import android.support.annotation.IntDef;

import com.keydom.ih_common.im.model.ImMessageConstant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({ImMessageConstant.AVCHAT_HANGUP, ImMessageConstant.AVCHAT_CANCEL,
        ImMessageConstant.AVCHAT_REJECT, ImMessageConstant.AVCHAT_NO_RESPONSE,
        ImMessageConstant.AVCHAT_REMOTE_CANCEL, ImMessageConstant.AVCHAT_REMOTE_REJECT,
        ImMessageConstant.AVCHAT_REMOTE_HANGUP, ImMessageConstant.AVCHAT_BUSY_LINE})
@Retention(RetentionPolicy.SOURCE)
public @interface AVChatReason {
}
