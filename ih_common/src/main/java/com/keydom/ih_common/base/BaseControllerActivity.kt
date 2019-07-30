package com.keydom.ih_common.base

import android.content.Context
import io.reactivex.disposables.CompositeDisposable
import java.lang.reflect.ParameterizedType

/**
 * Created by Chao  2018/5/8 on 15:50
 * description
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseControllerActivity<out C : BaseController<*>> : BaseActivity(), BaseView {

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

    /**
     * 初始化Controller
     */
    override fun initController() {
        controller.let {
            lifecycle.addObserver(it)
            it.attachView(this)
        }
    }


    override fun getContext(): Context = this

    override fun getDisposables(): CompositeDisposable = compositeDisposable


}