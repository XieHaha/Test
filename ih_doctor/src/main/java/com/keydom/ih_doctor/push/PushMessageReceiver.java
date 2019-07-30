package com.keydom.ih_doctor.push;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.orhanobut.logger.Logger;

import java.util.Set;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class PushMessageReceiver extends JPushMessageReceiver {

    private Context mContext;
    private static final int SEND_ALIAS = 1;
    private static final int SEND_TAGS = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SEND_ALIAS:
                    PushManager.setAlias(mContext, (String) msg.obj);
                    break;
                case SEND_TAGS:
                    PushManager.setTags(mContext, (Set<String>) msg.obj);
                    break;
                default:
            }
        }
    };

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        mContext = context;
        if (jPushMessage.getErrorCode() == 0) {
            PushPreference.saveTags(jPushMessage.getTags());
        } else {
            if (jPushMessage.getErrorCode() == 6002 || jPushMessage.getErrorCode() == 6014) {
                Message message = new Message();
                message.obj = jPushMessage.getTags();
                message.what = SEND_TAGS;
                mHandler.sendMessageDelayed(message, 1000 * 60);
            } else {
                Logger.e("PushMessageReceiver:tags-->errorCode=" + jPushMessage.getErrorCode());
            }
        }
        super.onTagOperatorResult(context, jPushMessage);
    }

    /**
     * 返回的错误码为6002 超时,6014 服务器繁忙,都建议延迟重试
     *
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        mContext = context;
        if (jPushMessage.getErrorCode() == 0) {
            PushPreference.saveAlias(jPushMessage.getAlias());
        } else {
            if (jPushMessage.getErrorCode() == 6002 || jPushMessage.getErrorCode() == 6014) {
                Message message = new Message();
                message.obj = jPushMessage.getAlias();
                message.what = SEND_ALIAS;
                mHandler.sendMessageDelayed(message, 1000 * 60);
            } else {
                Logger.e("PushMessageReceiver:alias-->errorCode=" + jPushMessage.getErrorCode());
            }
        }
        super.onAliasOperatorResult(context, jPushMessage);

    }
}
