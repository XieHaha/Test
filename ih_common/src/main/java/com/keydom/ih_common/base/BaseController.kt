package com.keydom.ih_common.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Chao  2018/5/8 on 15:49
 * description Presenter基类接口定义
 */
interface BaseController<V : BaseView> : LifecycleObserver {

    fun getView(): V?

    fun attachView(view: BaseView)

    fun getDisposable(): CompositeDisposable

    fun getContext(): Context

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()

}