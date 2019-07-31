package com.keydom.ih_patient.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * toast弹框工具
 */

public class ToastUtil {
    public static void shortToast(Context context, String desc) {
        Toast.makeText(context, desc, Toast.LENGTH_SHORT).show();
    }
}
