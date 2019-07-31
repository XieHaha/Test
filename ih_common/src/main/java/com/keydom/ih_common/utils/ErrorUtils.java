/*
    ShengDao Android Client, CommonUtils
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.keydom.ih_common.utils;

/**
 * [公共工具类，与Android API相关的辅助类]
 **/
public class ErrorUtils {

    private static final int SERVICE_ERROR = 500;
    private static final int NOTFOUND = 404;
    private static final int SUCCESS = 200;

    public static String getErrorMsg(int code) {
        String errMsg;
        switch (code) {
            default:
                errMsg = "未知错误";
                break;
            case SERVICE_ERROR:
                errMsg = "服务器错误";
                break;
            case NOTFOUND:
                errMsg = "接口不存在";
                break;
        }
        return errMsg;
    }


}
