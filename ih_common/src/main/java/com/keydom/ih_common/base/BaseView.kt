package com.keydom.ih_common.base

import android.content.Context
import io.reactivex.disposables.CompositeDisposable

interface BaseView {
    /**
     * 获取上下文对象
     */
    fun getContext(): Context

    /**
     *获取事件管理器
     */
    fun getDisposables(): CompositeDisposable
}