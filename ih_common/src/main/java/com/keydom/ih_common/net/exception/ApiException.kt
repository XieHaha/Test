package com.keydom.ih_common.net.exception

/**
 * Created by Chao  2018/5/8 on 14:19
 * description 自定义网络异常
 */
class ApiException : RuntimeException {

    var code: Int = 0 //错误码
    var msg: String = ""//错误信息

    constructor(code: Int, msg: String?) {
        this.code = code
        if (msg != null) {
            this.msg = msg
        }
    }

    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }


}