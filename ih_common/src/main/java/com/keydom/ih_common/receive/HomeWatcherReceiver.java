package com.keydom.ih_common.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.keydom.ih_common.avchatkit.TeamAVChatProfile;

/**
 * @date 20/7/1 11:35
 * @des home监听
 */
public class HomeWatcherReceiver extends BroadcastReceiver {
    private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    /**
     * action内的某些reason
     * 键旁边的最近程序列表键
     */
    private static final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
    /**
     * 按下home键
     */
    private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    /**
     * 锁屏键
     */
    private static final String SYSTEM_DIALOG_REASON_LOCK = "lock";
    /**
     * 某些三星手机的程序列表键
     */
    private static final String SYSTEM_DIALOG_REASON_ASSIST = "assist";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            // android.intent.action.CLOSE_SYSTEM_DIALOGS
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);

            if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
                // 短按Home键
                //可以在这里实现关闭程序操作。。。
                TeamAVChatProfile.sharedInstance().setTeamAVChatting(false);

            } else if (SYSTEM_DIALOG_REASON_RECENT_APPS.equals(reason)) {
                //Home键旁边的显示最近的程序的按钮
                // 长按Home键 或者 activity切换键
                TeamAVChatProfile.sharedInstance().setTeamAVChatting(false);
            } else if (SYSTEM_DIALOG_REASON_LOCK.equals(reason)) {
                // 锁屏，似乎是没有反应，监听Intent.ACTION_SCREEN_OFF这个Action才有用
                TeamAVChatProfile.sharedInstance().setTeamAVChatting(false);
            } else if (SYSTEM_DIALOG_REASON_ASSIST.equals(reason)) {
                // samsung 长按Home键
                TeamAVChatProfile.sharedInstance().setTeamAVChatting(false);
            }

        }
    }
}
