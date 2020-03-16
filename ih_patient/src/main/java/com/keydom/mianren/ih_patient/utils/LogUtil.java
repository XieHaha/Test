package com.keydom.mianren.ih_patient.utils;

import android.util.Log;

/**
 * @Name：com.kentra.yxyz.utils
 * @Description：log工具
 * @Author：song
 * @Date：18/11/5 上午9:51
 * 修改人：xusong
 * 修改时间：18/11/5 上午9:51
 */
public class LogUtil {
    private boolean isDebug=true;
    private static final String TAG="yxyz";
    public static void log(String msg){
        Log.i(TAG,msg);
    }
}
