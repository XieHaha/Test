package com.keydom.mianren.ih_patient.utils;

import android.view.View;

import com.netease.nimlib.sdk.msg.model.IMMessage;

public class XClickUtil {

    /**
     * 最近一次点击的时间
     */
    private static long mLastClickTime;
    /**
     * 最近一次点击的控件ID
     */
    private static int mLastClickViewId;

    /**
     * 最后一次点击的消息
     */
    private static IMMessage mLastClickmessage;

    /**
     * 是否是快速点击
     *
     * @param v  点击的控件
     * @param intervalMillis  时间间期（毫秒）
     * @return  true:是，false:不是
     */
    public static boolean isFastDoubleClick(View v, long intervalMillis) {
        int viewId = v.getId();
        long time =System.currentTimeMillis();
//        long time = SystemClock.elapsedRealtime();
        long timeInterval = Math.abs(time - mLastClickTime);
        if (timeInterval < intervalMillis && viewId == mLastClickViewId) {
            return true;
        } else {
            mLastClickTime = time;
            mLastClickViewId = viewId;
            return false;
        }
    }

    public static boolean isFastDoubleClick(IMMessage message, long intervalMillis) {
        long time =System.currentTimeMillis();
//        long time = SystemClock.elapsedRealtime();
        long timeInterval = Math.abs(time - mLastClickTime);
        if (timeInterval < intervalMillis && message == mLastClickmessage) {
            return true;
        } else {
            mLastClickTime = time;
            mLastClickmessage = message;
            return false;
        }
    }

}
