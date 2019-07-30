package com.keydom.ih_common.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * @Name：com.keydom.ih_doctor.utils
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/7 下午9:48
 * 修改人：xusong
 * 修改时间：18/11/7 下午9:48
 */
public class StatusBarUtils {

        public static void setWindowStatusBarColor(Activity activity, int colorResId) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = activity.getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(activity.getResources().getColor(colorResId));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void setWindowStatusBarColor(Dialog dialog, int colorResId) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = dialog.getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(dialog.getContext().getResources().getColor(colorResId));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
