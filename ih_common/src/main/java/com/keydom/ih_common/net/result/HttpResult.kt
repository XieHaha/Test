package com.keydom.ih_common.net.result

/**
 * Created by Chao  2018/5/8 on 13:39
 * description 网络请求统一模型，data是对象的情况
 */
class HttpResult<T>(statusCode: Int = 0, message: String = "", var result: T? = null) : BaseResult(statusCode, message) {
    override fun isEmpty(): Boolean {
        return result  == null
    }
}