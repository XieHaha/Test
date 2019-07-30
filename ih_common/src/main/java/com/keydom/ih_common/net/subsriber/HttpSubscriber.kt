package com.keydom.ih_common.net.subsriber

import android.content.Context
import com.keydom.ih_common.net.result.HttpResult
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Chao  2018/5/8 on 15:34
 * description 网络订阅,默认过滤HttpResult外层包装
 */

abstract class HttpSubscriber<T> @JvmOverloads constructor(context: Context? = null, disposables: CompositeDisposable? = null, isShowing: Boolean = true, isCancel: Boolean = true) : SourceSubscriber<HttpResult<T>>(context, disposables, isShowing, isCancel) {
    final override fun requestSuccess(data: HttpResult<T>) {
        requestComplete(data.result)
    }

    abstract fun requestComplete(data: T?)
}