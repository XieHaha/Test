package com.keydom.ih_common.im.listener;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.netease.nimlib.sdk.msg.model.IMMessage;

public interface IConversationBehaviorListener {
    /**
     * 头像点击回调
     *
     * @param context
     * @param message
     * @return
     */
    boolean onUserPortraitClick(Context context, @Nullable IMMessage message);

    /**
     * 消息体点击回调
     *
     * @param context
     * @param view
     * @param message
     * @return
     */
    boolean onMessageClick(Context context, View view, @Nullable IMMessage message);

    /**
     * 去支付回调
     *
     * @param context
     * @param view
     * @param message
     * @return
     */
    boolean onPayClick(Context context, View view, @Nullable IMMessage message);

    /**
     * 消息长按
     *
     * @param context
     * @param message
     */
    //void onMessageLongClick(Context context, IMMessage message);

    /**
     * 点击处方
     *
     * @param context
     * @param view
     * @param message
     * @return
     */
    boolean onPrescriptionClick(Context context, @Nullable IMMessage message);
}
