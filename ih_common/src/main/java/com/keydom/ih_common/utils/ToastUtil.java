package com.keydom.ih_common.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Toast显示工具类
 * 
 * @author Xun.Zhang
 * @date 1.0.0
 */
public class ToastUtil {

    /**
     * Show Toast in short time by system
     * 
     * @param context
     * @param strMsg
     */
    public static void showMessage(final Context context, final String strMsg) {
        Toast.makeText(context, strMsg, Toast.LENGTH_SHORT).show();
    }

    public static void showMessageHandler(final Context context, final String strMsg){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, strMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Show Toast in short time by system
     * 
     * @param context
     * @param resId
     */
    public static void showMessage(final Context context, final int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * Show Toast in long time by system
     * 
     * @param context
     * @param resId
     */
    public static void showMessageLong(final Context context, final int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }

    /**
     * Show Toast in long time by system
     * 
     * @param context
     * @param strMsg
     */
    public static void showMessageLong(final Context context, final String strMsg) {
        if (context != null) {
            Toast.makeText(context, strMsg, Toast.LENGTH_LONG).show();
        }
    }

}
