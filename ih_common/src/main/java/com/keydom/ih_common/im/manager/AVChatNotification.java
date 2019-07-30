package com.keydom.ih_common.im.manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.keydom.ih_common.R;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.activity.CommonVideoCallActivity;
import com.keydom.ih_common.im.model.AVChatExtras;

/**
 * 音视频聊天通知栏
 * Created by huangjun on 2015/5/14.
 */
public class AVChatNotification {

    private Context context;

    private NotificationManager notificationManager;
    private Notification callingNotification;
    private Notification missCallNotification;
    private String account;
    private String displayName;
    private static final int CALLING_NOTIFY_ID = 111;
    private static final int MISS_CALL_NOTIFY_ID = 112;

    public AVChatNotification(Context context) {
        this.context = context;
    }

    public void init(String account, String displayName) {
        this.account = account;
        this.displayName = displayName;

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        AVChatNotificationChannelCompat26.createNIMMessageNotificationChannel(context);
    }

    private void buildCallingNotification() {
        if (callingNotification == null) {
            Intent localIntent = new Intent();
            localIntent.setClass(context, CommonVideoCallActivity.class);
            localIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            String tickerText = String.format(context.getString(R.string.avchat_notification), displayName);
            int iconId = R.mipmap.ic_launcher;

            PendingIntent pendingIntent = PendingIntent.getActivity(context, CALLING_NOTIFY_ID, localIntent, PendingIntent
                    .FLAG_UPDATE_CURRENT);
            callingNotification = makeNotification(pendingIntent, context.getString(R.string.avchat_call), tickerText, tickerText,
                    iconId, false, false);
        }
    }

    private void buildMissCallNotification() {
        if (missCallNotification == null) {
            Intent notifyIntent = new Intent(context, ImClient.notificationEntrance);
            notifyIntent.putExtra(AVChatExtras.EXTRA_ACCOUNT, account);
            notifyIntent.putExtra(AVChatExtras.EXTRA_FROM_NOTIFICATION, true);

            notifyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            notifyIntent.setAction(Intent.ACTION_VIEW);
            notifyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, CALLING_NOTIFY_ID, notifyIntent, PendingIntent
                    .FLAG_UPDATE_CURRENT);

            String title = context.getString(R.string.avchat_no_pickup_call);
            String tickerText = displayName + ": 【网络通话】";
            int iconId = R.mipmap.avchat_no_pickup;

            missCallNotification = makeNotification(pendingIntent, title, tickerText, tickerText, iconId, true, true);
        }
    }

    private Notification makeNotification(PendingIntent pendingIntent, String title, String content, String tickerText,
                                          int iconId, boolean ring, boolean vibrate) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, AVChatNotificationChannelCompat26.getNIMChannelId(context));
        builder.setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setTicker(tickerText)
                .setSmallIcon(iconId);
        int defaults = Notification.DEFAULT_LIGHTS;
        if (vibrate) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (ring) {
            defaults |= Notification.DEFAULT_SOUND;
        }
        builder.setDefaults(defaults);

        return builder.build();
    }

    public void activeCallingNotification(boolean active) {
        if (notificationManager != null) {
            if (active) {
                buildCallingNotification();
                notificationManager.notify(CALLING_NOTIFY_ID, callingNotification);
                ImClient.getNotifications().put(CALLING_NOTIFY_ID, callingNotification);
            } else {
                notificationManager.cancel(CALLING_NOTIFY_ID);
                ImClient.getNotifications().remove(CALLING_NOTIFY_ID);
            }
        }
    }

    public void activeMissCallNotification(boolean active) {
        if (notificationManager != null) {
            if (active) {
                buildMissCallNotification();
                notificationManager.notify(MISS_CALL_NOTIFY_ID, missCallNotification);
                ImClient.getNotifications().put(MISS_CALL_NOTIFY_ID, callingNotification);
            } else {
                notificationManager.cancel(MISS_CALL_NOTIFY_ID);
                ImClient.getNotifications().remove(MISS_CALL_NOTIFY_ID);
            }
        }
    }
}
