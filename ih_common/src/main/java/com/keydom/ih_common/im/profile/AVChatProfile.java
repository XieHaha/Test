package com.keydom.ih_common.im.profile;

import android.os.Handler;

import com.keydom.Common;
import com.keydom.ih_common.im.ImClient;
import com.netease.nimlib.sdk.avchat.model.AVChatData;

public class AVChatProfile {

    private final String TAG = "AVChatProfile";

    /**
     * 是否正在音视频通话
     */
    private boolean isAVChatting = false;

    private Handler mHandler = new Handler(Common.INSTANCE.getApplication().getMainLooper());

    public static AVChatProfile getInstance() {
        return InstanceHolder.instance;
    }

    public boolean isAVChatting() {
        return isAVChatting;
    }

    public void setAVChatting(boolean chating) {
        isAVChatting = chating;
    }

    private static class InstanceHolder {
        public final static AVChatProfile instance = new AVChatProfile();
    }

    public void launchActivity(final AVChatData data, final String displayName, final int source) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // 启动，如果 task正在启动，则稍等一下
//                if (!AVChatKit.isMainTaskLaunching()) {
//                    launchActivityTimeout();
//                    AVChatActivity.incomingCall(AVChatKit.getContext(), data, displayName, source);
//                } else {
//                    launchActivity(data, displayName, source);
//                }
                ImClient.startVideoActivity(Common.INSTANCE.getApplication(), data);
            }
        };
        mHandler.postDelayed(runnable, 200);
    }

    public void activityLaunched() {
        mHandler.removeCallbacks(launchTimeout);
    }

    /**
     * 有些设备（比如OPPO、VIVO）默认不允许从后台broadcast receiver启动activity
     * 增加启动activity超时机制
     */
    private void launchActivityTimeout() {
        mHandler.removeCallbacks(launchTimeout);
        mHandler.postDelayed(launchTimeout, 3000);
    }

    /**
     * 如果未成功启动，就恢复av chatting -> false
     */
    private Runnable launchTimeout = new Runnable() {
        @Override
        public void run() {
            setAVChatting(false);
        }
    };
}