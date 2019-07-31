package com.keydom.ih_common.net.subsriber

import android.content.Context
import com.keydom.Common
import com.keydom.ih_common.net.exception.ApiException
import com.keydom.ih_common.net.exception.ExceptionEngine
import com.keydom.ih_common.net.result.BaseResult
import com.keydom.ih_common.widget.CommonProgressDialog
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Chao  2018/5/8 on 14:16
 * description 自定义源订阅者
 */
abstract class SourceSubscriber<T>(private val context: Context? = null
                                   , private val disposables: CompositeDisposable? = null
                                   , isShowing: Boolean = true
                                   , isCancel: Boolean = true) : Observer<T> {
    init {
        if (isShowing) {
            context?.let {
                CommonProgressDialog.showLoading(context, "请稍等", isCancel) {
                    disposables?.remove(dis)
                }
            }
        }
    }

    private lateinit var dis: Disposable
    override fun onSubscribe(d: Disposable) {
        disposables?.add(d)
        this.dis = d
    }

    override fun onError(e: Throwable) {
        disposables?.remove(dis)
        if (disposables?.size() == 0 || disposables == null) {
            CommonProgressDialog.loadingDismiss(context)
        }
        val apiException = ExceptionEngine.handleException(e)
        if (!requestError(apiException, apiException.code, apiException.msg)) {
            if (Common.getBaseException() != null) {
                Common.getBaseException()?.handleException(context, apiException)
            }
        }
    }

    /**
     * 将事件过滤处理后再将数据发送出去
     */
    override fun onNext(t: T) {
        if (t is BaseResult) {
//            t.message = ResultErrorManager.getMessage(t.message)
            if (t.statusCode == 200) {
                requestSuccess(t)
            } else {
                onError(ApiException(t.statusCode, t.message))
            }
        } else {
            requestSuccess(t)
        }
    }


    override fun onComplete() {
        disposables?.remove(dis)
        if (disposables?.size() == 0 || disposables == null) {
            CommonProgressDialog.loadingDismiss(context)
        }
    }

    /**
     * 网络请求成功得回调
     */
    open fun requestSuccess(data: T) {}

    /**
     * 返回值：true 子类处理，异常类不执行, false  子类不处理，由异常类统一处理
     */
    open fun requestError(exception: ApiException, code: Int, msg: String): Boolean = false
}