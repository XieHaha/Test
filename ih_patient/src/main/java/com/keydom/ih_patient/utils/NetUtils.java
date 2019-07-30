package com.keydom.ih_patient.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.keydom.Common;

/**
 * @Name：com.keydom.ih_patient.utils
 * @Description：网络工具
 * @Author：song
 * @Date：18/11/8 下午9:38
 * 修改人：xusong
 * 修改时间：18/11/8 下午9:38
 */
public class NetUtils {

    /**
     * 网络是否连接
     * @return
     */
    public static boolean isNetworkConnected() {
        if (Common.INSTANCE.getApplication() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager)Common.INSTANCE.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
