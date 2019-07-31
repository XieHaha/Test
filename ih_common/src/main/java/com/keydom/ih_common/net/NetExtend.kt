package com.keydom.ih_common.net

import com.keydom.ih_common.net.result.HttpResult
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by Chao  2018/5/9 on 10:54
 * description 网络扩展类
 */

fun <T : HttpResult<*>> Observable<T>.request(observer: Observer<T>) {
    ApiRequest.request(this, observer)
}

/*fun <T : HttpResult<R>, R : Mapper<M>, M> Observable<T>.requestDTO(observer: Observer<M>) {
    ApiRequest.requestDTO(this, observer)
}*/

/*@Synchronized
fun <T : HttpListResult<R>, R : Mapper<M>, M> Observable<T>.requestListDTO(observer: Observer<HttpResult<List<M>>>) {
    ApiRequest.requestListDTO(this, observer)
}*/
