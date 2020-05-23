package com.keydom.ih_common.utils;

import android.text.TextUtils;

import com.keydom.ih_common.constant.Const;


/**
 * @date 20/4/26 14:17
 * @des
 */
public class BaseFileUtils {

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

    public static boolean needAddHead(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        return !url.startsWith("http");
    }
}
