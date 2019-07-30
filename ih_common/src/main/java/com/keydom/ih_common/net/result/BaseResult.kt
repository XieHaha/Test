package com.keydom.ih_common.net.result

/**
 * 网络请求返回实体基类
 */
abstract class BaseResult(var statusCode: Int = 0,  var message: String = "") {
    abstract fun isEmpty(): Boolean
}