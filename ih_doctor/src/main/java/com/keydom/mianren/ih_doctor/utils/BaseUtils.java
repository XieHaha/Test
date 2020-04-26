package com.keydom.mianren.ih_doctor.utils;

import android.text.TextUtils;

import com.keydom.mianren.ih_doctor.constant.Const;

/**
 * @date 20/4/26 14:17
 * @des
 */
public class BaseUtils {

    public static String getHeaderUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (url.startsWith("http")) {
            return url;
        } else {
            return Const.IMAGE_HOST + url;
        }
    }
}
