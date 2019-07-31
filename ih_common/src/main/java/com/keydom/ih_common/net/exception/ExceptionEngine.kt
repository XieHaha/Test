package com.keydom.ih_common.net.exception

import android.net.ParseException
import android.util.MalformedJsonException
import com.alibaba.fastjson.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException

/**
 * Created by Chao  2018/5/8 on 14:17
 * description 异常转换类
 */
object ExceptionEngine {
    /**
     * 未知错误
     */
    private const val UN_KNOWN_ERROR = 1000
    /**
     * 未知错误
     */
    private val UN_KNOWN_ERROR_MESSAGE
        get() = "未知错误"

    /**
     * 网络错误
     */
    private val NETWORK_ERROR_MESSAGE
        get() = "网络错误"

    /**
     * 解析(服务器)数据错误
     */
    private const val ANALYTIC_SERVER_DATA_ERROR = 1001
    /**
     * 解析(服务器)数据错误
     */
    private val ANALYTIC_SERVER_DATA_ERROR_MESSAGE
        get() = "解析错误"

//    const val ANALYTIC_CLIENT_DATA_ERROR = 1002//解析(客户端)数据错误

    /**
     * 网络连接错误
     */
    private const val CONNECT_ERROR = 1010
    /**
     * 网络连接错误
     */
    private val CONNECT_ERROR_MESSAGE
        get() = "当前网络不可用"

    /**
     * 网络连接超时
     */
    private const val TIME_OUT_ERROR = 1020
    /**
     * 网络连接超时
     */
    private val TIME_OUT_ERROR_MESSAGE
        get() = "加载超时"
    /**
     * socket异常
     */
    private const val SOCKET_ERROR = 1030
    /**
     * socket异常
     */
    private val SOCKET_ERROR_MESSAGE
        get() = "服务器连接失败"

    fun handleException(e: Throwable): ApiException {
        val ex: ApiException
        if (e is HttpException) {             //HTTP错误
            ex = ApiException(e, e.code())
            ex.msg = (e.message())  //均视为网络错误
        } else if (e is ApiException) {    //服务器返回的错误
            ex = e
        } else if (e is org.json.JSONException
                || e is JSONException
                || e is ParseException || e is MalformedJsonException) {  //解析数据错误
            ex = ApiException(e, ANALYTIC_SERVER_DATA_ERROR)
            ex.msg = (ANALYTIC_SERVER_DATA_ERROR_MESSAGE)
        } else if (e is ConnectException) {//连接网络错误
            ex = ApiException(e, CONNECT_ERROR)
            ex.msg = (CONNECT_ERROR_MESSAGE)
        } else if (e is SocketTimeoutException) {//网络超时
            ex = ApiException(e, TIME_OUT_ERROR)
            ex.msg = (TIME_OUT_ERROR_MESSAGE)
        } else if (e is SocketException) {
            ex = ApiException(e, SOCKET_ERROR)
            ex.msg = (SOCKET_ERROR_MESSAGE)
        } else {  //未知错误
            ex = ApiException(e, UN_KNOWN_ERROR)
            ex.msg = e.message ?: UN_KNOWN_ERROR_MESSAGE
        }
        return ex
    }
}