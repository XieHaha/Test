package com.keydom.ih_common.net.result

/**
 * Created by Chao  2018/5/8 on 13:39
 * description 网络请求统一模型，data是List得情况
 */
class HttpListResult<T>(code: Int = 0, message: String = "", success: Boolean, var `object`: List<T>? = null) : BaseResult(code, message) {
    override fun isEmpty(): Boolean {
        return `object` == null
    }
}