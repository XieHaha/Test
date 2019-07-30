package com.keydom.ih_patient.utils.pay;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 窗口工具
 */
public class WindowUtils {
    public static void hiddingInputMethod(View view, Context context){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
       /* boolean isOpen=imm.isActive();
        if(isOpen)*/

    }
}
