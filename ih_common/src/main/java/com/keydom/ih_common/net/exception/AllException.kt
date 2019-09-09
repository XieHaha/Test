package com.keydom.ih_common.net.exception

import android.content.Context

/**
 * Created by Chao  2018/5/8 on 14:27
 * description 全局网络异常处理
 */
class AllException : BaseException {

    /**
     * 所有网络异常都会统一走这里，但是有前提条件：
     * 1.Observer订阅时，如果不重写requestError，默认返回false，所有错误将会走这里。
     * 2.如果重写requestError，在回掉里自己处理错误，并返回true这里回调将会被拦截。
     */
    override fun handleException(context: Context?, e: ApiException) {
        //TODO 此处用来处理一些特殊的全局异常(账号挤下线等)

    }
}