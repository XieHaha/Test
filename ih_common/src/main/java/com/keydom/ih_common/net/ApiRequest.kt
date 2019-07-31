package com.keydom.ih_common.net

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object ApiRequest {
    /**
     * 源请求(不含DTO)
     */
    @Synchronized
    fun <T> request(observable: Observable<T>, observer: Observer<T>) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    /**
     * 返回Observable源，方便后续对Observable进一步链式处理(zip,map等操作)
     *
     * @param observable
     * @param <T>
     * @return 返回Observable源
     */
    @Synchronized
    fun <T> requestSource(observable: Observable<T>): Observable<T> {
        return observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
    }

    /**
     * 含有DTO的请求(map操作符不能返回null，内部会抛出异常，导致回调失效
     */
//    @Synchronized
//    fun <T : HttpResult<R>, R : Mapper<M>, M> requestDTO(observable: Observable<T>, observer: Observer<M>) {
//        observable
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .map(Function<T, M> { t ->
//                    if (t.`object` == null) {
//                        return@Function null
//                    } else if (t.code != 200 && !t.success) {
//                        ApiException(t.code, t.message)
//                    }
//                    t.`object`?.transformation()
//                })
//                .subscribe(observer)
//    }

    /**
     * ListDTO 构建  含请求码
     * ListResultDTO<JokeModelDTO>  返回 HttpResult<List<JokeModel>>
     */
//    @Synchronized
//    fun <T : HttpListResult<R>, R : Mapper<M>, M> requestListDTO(observable: Observable<T>, observer: Observer<HttpResult<List<M>>>) {
//        observable.observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .map(Function<T, HttpResult<List<M>>> { t ->
//                    if (t.`object` == null) {
//                        return@Function HttpResult(t.code, t.message, t.success, null)
//                    } else if (t.code != 200 && !t.success) {
//                        ApiException(t.code, t.message)
//                    }
//                    val list = ArrayList<M>()
//                    t.`object`?.map { list.add(it.transformation()) }
//                    HttpResult(t.code, t.message, t.success, list)
//                })
//                .subscribe(observer)
//    }
}