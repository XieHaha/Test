package com.keydom.ih_common.base

import android.content.Context
import com.keydom.Common
import io.reactivex.disposables.CompositeDisposable
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
abstract class BaseControllerFragment<out C : BaseController<*>> : BaseFragment(), BaseView {

    val controller: C by lazy {
        val type = javaClass.genericSuperclass
        val clz = if (type is ParameterizedType) {
            val args = type.actualTypeArguments
            args[0] as Class<*>
        } else {
            Any::class.java
        }
        clz.newInstance() as C
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun initController() {
        controller.let {
            lifecycle.addObserver(it)
            it.attachView(this)
        }
    }

    override fun getContext(): Context = activity ?: Common.getApplication()

    override fun getDisposables(): CompositeDisposable = compositeDisposable
}