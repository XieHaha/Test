package com.keydom.ih_common.net.exception

import android.content.Context

/**
 * Created by Chao  2018/5/8 on 14:28
 * description 定义全局网络异常接口
 */
interface BaseException {
    fun handleException(context: Context?, e: ApiException)
}