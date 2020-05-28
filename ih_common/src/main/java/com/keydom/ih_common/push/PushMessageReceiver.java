package com.keydom.ih_common.push;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.keydom.ih_common.bean.MessageEvent;
import com.keydom.ih_common.constant.EventType;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.Set;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class PushMessageReceiver extends JPushMessageReceiver {
    private static final String TAG = "JPushReceiver";

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
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        Logger.e(TAG + "PushMessageReceiver:tags-->=" + message.notificationExtras);
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NOTIFY_APPLY_JOIN_CONSULTATION).build());
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage message) {
        Logger.e(TAG + "PushMessageReceiver:tags-->=" + message.notificationExtras);
    }

    //    /**
    //     * 状态通知
    //     */
    //    private void notifyStatusChange(String type) {
    //        switch (type) {
    //            case MESSAGE_DOCTOR_AUTH_SUCCESS:
    //                NotifyChangeListenerManager.getInstance().notifyDoctorAuthStatus
    //                (DocAuthStatus.AUTH_SUCCESS);
    //                break;
    //            case MESSAGE_DOCTOR_AUTH_FAILED:
    //                NotifyChangeListenerManager.getInstance().notifyDoctorAuthStatus
    //                (DocAuthStatus.AUTH_FAILD);
    //                break;
    //            case MESSAGE_TRANSFER_APPLY:
    //            case MESSAGE_TRANSFER_CANCEL:
    //            case MESSAGE_TRANSFER_SYSTEM_CANCEL_R:
    //            case MESSAGE_TRANSFER_SYSTEM_CANCEL_T:
    //                NotifyChangeListenerManager.getInstance().notifyDoctorTransferPatient("");
    //                //系统消息列表
    //                NotifyChangeListenerManager.getInstance().notifySystemMessageStatusChange("");
    //                break;
    //            case MESSAGE_DOCTOR_ADD_SUCCESS:
    //                NotifyChangeListenerManager.getInstance().notifyDoctorStatusChange("");
    //                break;
    //            case MESSAGE_PATIENT_ADD_SUCCESS:
    //                NotifyChangeListenerManager.getInstance().notifyPatientListChanged("");
    //                break;
    //            default:
    //                //系统消息列表
    //                NotifyChangeListenerManager.getInstance().notifySystemMessageStatusChange("");
    //                break;
    //        }
    //    }
    //
    //    /**
    //     * 通知栏业务处理
    //     */
    //    private void jumpPageByType(Context context, String type, String msgId) {
    //        Intent mainIntent, baseIntent;
    //        Intent[] intents;
    //        if (TextUtils.isEmpty(type) || !ZycApplication.getInstance().isLoginStatus() ||
    //                ZycApplication.getInstance().getLoginBean() == null) {
    //            mainIntent = new Intent(context, LoginOptionsActivity.class);
    //            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //            context.startActivity(mainIntent);
    //            return;
    //        }
    //        switch (type) {
    //            case MESSAGE_DOCTOR_AUTH_SUCCESS:
    //            case MESSAGE_DOCTOR_AUTH_FAILED:
    //                mainIntent = new Intent(context, AuthDoctorActivity.class);
    //                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //                context.startActivity(mainIntent);
    //                break;
    //            case MESSAGE_SERVICE_REPORT:
    //                mainIntent = new Intent(context, MainActivity.class);
    //                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //                baseIntent = new Intent(context, ReservationServiceDetailActivity.class);
    //                baseIntent.putExtra(CommonData.KEY_ORDER_ID, msgId);
    //                if (!LifecycleHandler.isApplicationInForeground()) {
    //                    intents = new Intent[] { mainIntent, baseIntent };
    //                    context.startActivities(intents);
    //                }
    //                else {
    //                    context.startActivity(baseIntent);
    //                }
    //                break;
    //            case MESSAGE_SERVICE_ADVICE:
    //                mainIntent = new Intent(context, MainActivity.class);
    //                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //                baseIntent = new Intent(context, ReservationServiceDetailActivity.class);
    //                baseIntent.putExtra(CommonData.KEY_ORDER_ID, msgId);
    //                if (!LifecycleHandler.isApplicationInForeground()) {
    //                    intents = new Intent[] { mainIntent, baseIntent };
    //                    context.startActivities(intents);
    //                }
    //                else {
    //                    context.startActivity(baseIntent);
    //                }
    //                break;
    //            case MESSAGE_TRANSFER_UPDATE:
    //            case MESSAGE_TRANSFER_REJECT:
    //            case MESSAGE_TRANSFER_RECEIVED:
    //            case MESSAGE_TRANSFER_OTHER:
    //            case MESSAGE_TRANSFER_SYSTEM_CANCEL_T:
    //                mainIntent = new Intent(context, MainActivity.class);
    //                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //                baseIntent = new Intent(context, TransferInitiateDetailActivity.class);
    //                baseIntent.putExtra(CommonData.KEY_ORDER_ID, msgId);
    //                if (!LifecycleHandler.isApplicationInForeground()) {
    //                    intents = new Intent[] { mainIntent, baseIntent };
    //                    context.startActivities(intents);
    //                }
    //                else {
    //                    context.startActivity(baseIntent);
    //                }
    //                break;
    //            case MESSAGE_TRANSFER_APPLY:
    //            case MESSAGE_TRANSFER_CANCEL:
    //            case MESSAGE_TRANSFER_SYSTEM_CANCEL_R:
    //                mainIntent = new Intent(context, MainActivity.class);
    //                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //                baseIntent = new Intent(context, TransferReceiveDetailActivity.class);
    //                baseIntent.putExtra(CommonData.KEY_ORDER_ID, msgId);
    //                if (!LifecycleHandler.isApplicationInForeground()) {
    //                    intents = new Intent[] { mainIntent, baseIntent };
    //                    context.startActivities(intents);
    //                }
    //                else {
    //                    context.startActivity(baseIntent);
    //                }
    //                break;
    //            case MESSAGE_DOCTOR_ADD_SUCCESS:
    //                mainIntent = new Intent(context, MainActivity.class);
    //                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //                baseIntent = new Intent(context, ChatContainerActivity.class);
    //                baseIntent.putExtra(CommonData.KEY_CHAT_ID, msgId);
    //                baseIntent.putExtra(CommonData.KEY_DOCTOR_CHAT, true);
    //                if (!LifecycleHandler.isApplicationInForeground()) {
    //                    intents = new Intent[] { mainIntent, baseIntent };
    //                    context.startActivities(intents);
    //                }
    //                else {
    //                    context.startActivity(baseIntent);
    //                }
    //                break;
    //            case MESSAGE_PATIENT_ADD_SUCCESS:
    //                mainIntent = new Intent(context, MainActivity.class);
    //                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //                baseIntent = new Intent(context, ChatContainerActivity.class);
    //                baseIntent.putExtra(CommonData.KEY_CHAT_ID, msgId);
    //                if (!LifecycleHandler.isApplicationInForeground()) {
    //                    intents = new Intent[] { mainIntent, baseIntent };
    //                    context.startActivities(intents);
    //                }
    //                else {
    //                    context.startActivity(baseIntent);
    //                }
    //                break;
    //            case MESSAGE_CURRENCY_ARRIVED:
    //            case MESSAGE_CURRENCY_DEDUCTION:
    //            case MESSAGE_ACCOUNT_CREATE:
    //            default:
    //                break;
    //        }
    //    }


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
                Logger.e(TAG + "PushMessageReceiver:tags-->errorCode=" + jPushMessage.getErrorCode());
            }
        }
        super.onTagOperatorResult(context, jPushMessage);
    }

    /**
     * 返回的错误码为6002 超时,6014 服务器繁忙,都建议延迟重试
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
                Logger.e(TAG + "PushMessageReceiver:alias-->errorCode=" + jPushMessage.getErrorCode());
            }
        }
        super.onAliasOperatorResult(context, jPushMessage);

    }
}
